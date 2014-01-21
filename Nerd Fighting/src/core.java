import java.awt.Color;


public class core {

	// method variables
	public  screen screen;
	public  theFrame theFrame;
	public  gravityEngine gravityEngine;
	public  weatherEngine weatherEngine;
	public  physicsEngine physicsEngine;
	public  gameLoop gameLoop;
	public  menu menu;
	public  HUD HUD;
	public  controller controller;
	public  world world;
	public  serverSocket serverSocket;
	public  clientSocket clientSocket;
	public  subconscious subconscious;

	// Working variables
	public boolean isRunning = false;
	public boolean isInitialized = false;
	public boolean isSync = true;


	// Player variables
	public long localPlayerID = 0;

	public core()
	{
		// Start running.
		isRunning = true;

		// Initialize everything.
		init();

	}

	public void init()
	{

			System.out.println("Initialize..."); // For debugging

		// Creating World
		this.initWorld();
			System.out.println("world: Completed"); // For debugging

		// Creating menu
		this.menu = new menu(this);
			System.out.println("menu: Completed"); // For debugging

		// Creating HUD
		this.HUD = new HUD(this);
			System.out.println("HUD: Completed"); // For debugging


		// Create & start Threads:
			// socketServer
			 this.serverSocket = new serverSocket(this, this.world); // Create socketServer

			// clientSocket
			 this.clientSocket = new clientSocket(this, this.world); // Create clientSocket

			// gravityEngine
			 this.gravityEngine = new gravityEngine(this, this.world); // Create gravityEngine

			// weatherEngine
			 this.weatherEngine = new weatherEngine(this, this.world); // create weatherEngine

			// physicsEngine
			 this.physicsEngine = new physicsEngine(this, this.world); // create physicsEngine


		// Create controller
		this.controller = new controller(this);
			System.out.println("controller: Created"); // For debugging


		// init screen
		this.screen = new screen(this); // Create screen, with controller
		this.screen.isFullScreen(false); // false: is not full-screen.
		this.screen.init();
			System.out.println("screen: initilized"); // For debugging


		// Create subconscious for players - Read description in class for more info.
		this.subconscious = new subconscious(this);


		// Run gameLoop
		this.gameLoop = new gameLoop(this); // Create gameLoop

			serverSocket.start(); // Start socketServer
					System.out.println("serverSocket: Started"); // For debugging

			clientSocket.start(); // Start clientSocket
					System.out.println("clientSocket: Started"); // For debugging

			 gravityEngine.start(); // Start gravityEngine
					System.out.println("gravityEngine: Started"); // For debugging

			 weatherEngine.start(); // Start weatherEngine
					System.out.println("weatherEngine: Started"); // For debugging

			 physicsEngine.start(); // Start physicsEngine
					System.out.println("physicsEngine: Started"); // For debugging

			 subconscious.start(); // Start subconscious
					System.out.println("subconscious: Started"); // For debugging

			gameLoop.start(); // Start gameLoop
					System.out.println("gameLoop: Started"); // For debugging

		this.isInitialized = true;
			System.out.println("Init: Completed"); // For debugging


	}

	public void initWorld()
	{
		// Create world
		this.world = new world(this);
		this.world().init();
		// Initialize Player
		initPlayerID(); // Initialize users playerID
		initPlayer(this.localPlayerID, null, null, 100,100, 3);
	}

	public void initPlayer(long playerID, weapon weaponH1, weapon weaponH2, int x, int y, int scale)
	{

		// Special variables
		// int scale = (int) (5 * (Math.random()) + 5);
		//	int scale = 3;

		// Player(double x, double y, double hRadius, double bodyLength, double armLength, double handRadius, double footRadius, double legLength, double mass)
		player myPlayer = new player((double) x, (double) y, (double) 5 * scale, (double) 40 * scale, (double) 12 * scale, (double) 2 * scale, (double) 2 * scale, (double) 12 * scale, (double) 100 * scale); // Create player
		myPlayer.setPlayerID(playerID); // Set playerID
		myPlayer.setColor(Color.PINK); // Set Color of player

		// TEST //
		// Add weapon to player
		if (weaponH1 != null) myPlayer.h1Weapon(weaponH1);
		if (weaponH2 != null) myPlayer.h2Weapon(weaponH2);
		// myPlayer.h1Weapon(world.allWeapons.get(0)); // TEST
		// myPlayer.h2Weapon(core.world.allWeapons.get(0)); // TEST

		// Add users player to world.
		world().addPlayer(myPlayer);

	}

	public synchronized void initPlayerID()
	{
		this.localPlayerID = (createPlayerID());
	}

	public synchronized long createPlayerID()
	{
		long playerID = (long) (System.currentTimeMillis() * Math.random());
		return playerID;
	}

	// ------------------------------------------------------------------------------------------ //
	// Set methods
	public void isRunning(boolean newIsRunning)
	{
		this.isRunning = newIsRunning;
	}
	public void updateWorld(world newWorld)
	{
		this.world = newWorld;
	}

	// ----------------------------------------------------------------------------------------- //
	public synchronized world world()
	{

		while(!this.isSync)
		{
			try
			{
				 Thread.sleep(1);
			} catch (Exception e) {}
		}

		return ((world) this.world);

	}


}
