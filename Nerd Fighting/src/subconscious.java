import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;


public class subconscious extends Thread{

	// core
	core core;
	myMath myMath = new myMath();

	// Working variables
	int restTime = 1;
	private long initTime = 0;
	private long timePassed = 0;
	private long curTime = 0;
	private long lastTime = 0;
	private double timeFraction = 0.0;

	// PlayerID
	public long playerID;

	// inputManager
	ArrayList<inputManager> inputManagers = new ArrayList<inputManager>();

	public subconscious(core core)
	{
		// core
		this.core = core;

		// Set up physicsEngine Thread
		this.setName("subconscious Thread");
		this.setPriority(Thread.MAX_PRIORITY);

		// Set playerID
		this.playerID = core.localPlayerID;

		// Create local InputManager
		inputManager localInputManager = new inputManager(this.playerID);
		addInputManager(localInputManager);

	}

	public void run()
	{
		// Run subconscious
		while (core.isRunning)
		{
			// Check if subconscious should be running
			while (core.clientSocket.clientMode == core.clientSocket.DEFAULTMODE) // If client is running, it is getting info from a server, and if its getting info from a server. The server subconscious is running; NOT clients.
			{
				// System.out.println("subconscious: waiting"); // For debugging

				try
				{
					// Give the OS some reseting time.
					Thread.sleep(restTime); // It's nice to give the computer a rest.
				} catch (InterruptedException iE) {
				}
			}


			// System.out.println("inputManagers: " + inputManagers);
			// System.out.println("Players: " + core.world.allPlayers);

			// Update time - for Real time gaming
			updateTime();
			
			for (int i = 0; i < inputManagers.size(); i++)
			{
				// Check if ready
				core.world();
				// Update player with inputs
						// System.out.print("UPDATE PLAYER: "); // Debugging
				updatePlayer(inputManagers.get(i));
						// System.out.println("....DONE"); // Debugging

			}

			try
			{
				// Give the OS some reseting time.
				Thread.sleep(restTime); // It's nice to give the computer a rest.
			} catch (InterruptedException iE) {
			}

		}

	}
	
	private void updateTime()
	{
		// Update curTime and other required components involving time.
		lastTime = curTime;
		curTime = System.currentTimeMillis();
		timePassed = (curTime - lastTime);
		timeFraction = (timePassed / 1000.0);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------- //
	public synchronized  void updatePlayer(inputManager playerInput)
	{
		long playerID = playerInput.playerID;

		// Get playerIndex with playerID
		int playerIndex = (core.world().playerIndex(playerID));


		if (playerIndex == -1) // -1 = Does not exist // -1 != Does exist
		{
		// Create player
			// Settings
			int x = 100;
			int y = 100;
			int scale = 3;
			// Initialize Player
			core.initPlayer(playerID, null, null, x, y, scale);

		}

		// Get playerIndex with playerID, again
		playerIndex = (core.world().playerIndex(playerID));


		if (playerIndex != -1) // -1 = Does not exist // -1 != Does exist
		{
			// Update player lastAccessed
			core.world().allPlayers.get(playerIndex).lastAccessed = System.currentTimeMillis();
			core.world().allPlayers.get(playerIndex).userName = (playerInput.userName);

			// ----- Do player actions, etc ------ //
			// Keyboard
			for (int k = 0; k < playerInput.keysDown.size(); k++ )
			{

				int keyCode = playerInput.keysDown.get(k);

				if (keyCode == KeyEvent.VK_F) // Flip
				{
					playerFlip(playerID);
				}
				else if (keyCode == KeyEvent.VK_SPACE) // Jump
				{
					playerJump(playerID);
				}
				else if (keyCode == KeyEvent.VK_ENTER) // Respawn
				{
					core.world().allPlayers.get(playerIndex).respawn();
				}
				else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
				{
					playerWalk(playerID,+1);
				}
				else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
				{
					playerWalk(playerID,-1);
				}
				else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
				{
					// playerStand(playerID);
				}
				else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
				{
					// playerCrouch(playerID);
				}
				else if (keyCode == KeyEvent.VK_X)
				{
					// -- TEST -- //
					 /* Creates new weapon */ 
					 /* add weapon */
					  int index = core.world().playerIndex(playerID);
					  if (index != -1)
					  {
						// System.out.println("Add: h1Weapon");
					 	 if (core.world().allPlayers.get(index).h1Weapon == null) 
					 		 {
					 		// Create Weapon1
								weapon weap1 = new weapon(playerID);
								// Create projectile
								projectile proj1 = new projectile(playerID);
								proj1.newTotalVelocity(5000);
								// Add proj to weapon
								weap1.setProj1(proj1);
								weap1.setProj2(proj1);
								// Add weapon to player
								core.world().allPlayers.get(index).h1Weapon(weap1);
								/*
								// Add weapon to allWeapons
								// allWeapons.add(weap1);
								*/
					 		 	// core.world().allPlayers.get(index).h1Weapon(core.world.allWeapons.get(core.world.allWeapons.size() - 1));
					 		 }

					  }
					 // /* Creates new weapon */ core.world.init();
					 /* add weapon */
					 // index = core.world.playerIndex(playerID);
					  if (index != -1)
					  {
						// System.out.println("Add: h2Weapon");
					 	
						// Create Weapon1
							weapon weap2 = new weapon(playerID);
							// Create projectile
							projectile proj2 = new projectile(playerID);
							proj2.newTotalVelocity(5000);
							// Add proj to weapon
							weap2.setProj1(proj2);
							weap2.setProj2(proj2);
							// Add weapon to player
							core.world().allPlayers.get(index).h2Weapon(weap2);

					  }

				}

			}

			// Mouse
			playerAim(playerID, playerInput.mousePoint);
			if (playerInput.mouseLeftDown) playerShoot(playerID);

		}
		else
		{
			// System.out.println("No player!"); // For debugging
		}


	}

	public synchronized void addInputManager(inputManager newInputM)
	{
	//	System.out.println("addInputManager: playerID: " + newInputM.playerID);
		int index = inputManagerIndex(newInputM.playerID); // Get index // Uses playerID to match and get index
		if (index == -1) // If index = -1, then it does not already exist
		{
		//	System.out.println("Add: inputManager");
			this.inputManagers.add(newInputM); // Add newInputM
		}
		else // Exists
		{
		//	System.out.println("Set: inputManager");
			this.inputManagers.set(index, newInputM); // Update inputManager(index) to newInputM
		}
	}

	public synchronized void removeInputManager(long playerID)
	{
		int index = inputManagerIndex(playerID); // Get index // Uses playerID to match and get index
		if (index == -1) // If index = -1, then it does not already exist
		{
				// Does not exist // Do nothing
		}
		else // Exists
		{
			this.inputManagers.remove(index); // Remove deleteInputM
		}
	}

	public synchronized void removeInputManager(inputManager deleteInputM)
	{
		int index = inputManagerIndex(deleteInputM.playerID); // Get index // Uses playerID to match and get index
		if (index == -1) // If index = -1, then it does not already exist
		{
				// Does not exist // Do nothing
		}
		else // Exists
		{
			this.inputManagers.remove(index); // Remove deleteInputM
		}
	}

	public synchronized int inputManagerIndex(long playerID)
	{
		int index = -1; // Index -1 = Does not exist.

		for (int i = 0; i < this.inputManagers.size(); i++)
		{
			if (((long) this.inputManagers.get(i).playerID) == ((long) playerID))
			{
				// Is the desired index
				index = i;
				break;
			}
		}

		return index;

	}


	// --------------------------------------------------------------------------------------------------------------------------------------- //

	// Menu input handlers
	public void keyPressed(KeyEvent keyEvent)
	{
		int keyCode = keyEvent.getKeyCode();

		// System.out.println(keyCode);


		if ( keyCode == KeyEvent.VK_ESCAPE)
		{
			core.controller.updateReceiver(core.controller.MENUNUM);
			core.menu.toggleMenu();
			// System.out.println(core.menu.menuOpen);
		}
		else
		{
		// Add keyCode to keysDown of inputManager(playerID)
			// Get index
			int index = inputManagerIndex(this.playerID);
			// Add to inputManager(index)
			inputManagers.get(index).addKey(keyCode);
		}

		/*
		if (keyCode == KeyEvent.VK_BACK_SPACE)
		{
			// Backspace
			try
			{
				core.world().test = core.world().test.substring(0,core.world().test.length()-1);
			} catch (Exception exception) {}
			}
		else if (keyCode == KeyEvent.VK_ENTER)
		{
			core.world = new world(core);
		//	core.initWorld();
		}
		else if (keyCode == KeyEvent.VK_F)
		{
			playerFlip(playerID);
		}
		else if (keyCode == KeyEvent.VK_SPACE)
		{
			playerJump(playerID);
		}
		else if (keyCode == KeyEvent.VK_RIGHT)
		{
			playerWalk(playerID,+1);
		}
		else if (keyCode == KeyEvent.VK_LEFT)
		{
			playerWalk(playerID,-1);
		}
		else
		{
			core.world().test += ((keyEvent.getKeyChar()) + "");
		}
		*/


	}


	public void keyReleased(KeyEvent keyEvent) {

		// Get keyCode
		int keyCode = keyEvent.getKeyCode();

		// Remove keyCode from keysDown of inputManager(playerID)
			// Get index
			int index = inputManagerIndex(this.playerID);
			// Remove from inputManager(index)
			inputManagers.get(index).removeKey(keyCode);


	}

	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		//	System.out.println(mouseEvent);

		// Get index
		int index = inputManagerIndex(this.playerID);
		// Update inputManager(index)
		 inputManagers.get(index).mouseLeftDown();
		
		/*
		// Updating weapon and projectile angle
		Point aimPoint = new Point();
		aimPoint.x = (mouseEvent.getX());
		aimPoint.y = (mouseEvent.getY());
		playerAim(this.playerID, aimPoint);
		// Shooting
		playerShoot(this.playerID);
		 */

		/*
		int x = mouseEvent.getX();
		int y = mouseEvent.getY();

		// Special variables
		// int scale = (int) (5 * (Math.random()) + 5);
		int scale = 3;

		// Player(double x, double y, double hRadius, double bodyLength, double armLength, double handRadius, double footRadius, double legLength, double mass)
		player myPlayer = new player((double) x, (double) y, (double) 5 * scale, (double) 40 * scale, (double) 12 * scale, (double) 2 * scale, (double) 2 * scale, (double) 12 * scale, (double) 100 * scale);
		this.playerID = (long) (System.currentTimeMillis() * Math.random());
		myPlayer.setPlayerID(this.playerID);
		myPlayer.setColor(Color.PINK);

		// TEST //
		// Add weapon to player
		myPlayer.h1Weapon(core.world().allWeapons.get(0)); // TEST
		// myPlayer.h2Weapon(core.world().allWeapons.get(0)); // TEST

		try
		{
			core.world().allPlayers.set(0,myPlayer);
		} catch (Exception exception)
		{
			core.world().addPlayer(myPlayer);
		}

		*/

	}

	public void mouseEntered(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent keyEvent) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
	//	System.out.println(mouseEvent);
	//	playerShoot(this.playerID);
		// mouseDown = true, for inputManager(playerID)
			// Get index
			int index = inputManagerIndex(this.playerID);

			inputManagers.get(index).mouseLeftDown();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

		Point aimPoint = new Point();
		aimPoint.x = (mouseEvent.getX());
		aimPoint.y = (mouseEvent.getY());
		// Get index
			int index = inputManagerIndex(this.playerID);
			// Update inputManager(index)
			inputManagers.get(index).mousePoint(aimPoint, core.screen.drawOffsetX, core.screen.drawOffsetY);
			// inputManagers.get(index).mouseLeftUp();

			
		//	playerAim(this.playerID, aimPoint);
	}

	public void mouseDragged(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub


		// Updating weapon and projectile angle
	Point aimPoint = new Point();
		aimPoint.x = (mouseEvent.getX());
		aimPoint.y = (mouseEvent.getY());
		// Get index
			int index = inputManagerIndex(this.playerID);
			// Remove from inputManager(index)
			inputManagers.get(index).mousePoint(aimPoint, core.screen.drawOffsetX, core.screen.drawOffsetY);
			inputManagers.get(index).mouseLeftDown();

		/*
	 	playerAim(this.playerID, aimPoint);
		// Shooting
		playerShoot(this.playerID);
		*/


		/*
		// TEST
		circle newHail = new circle(Math.random() * 10, mouseEvent.getX(), mouseEvent.getY());
		core.world().addHail(newHail);
		*/

	}

	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		Point aimPoint = new Point();
		aimPoint.x = (mouseEvent.getX());
		aimPoint.y = (mouseEvent.getY());
		// Get index
			int index = inputManagerIndex(this.playerID);
			// Remove from inputManager(index)
			inputManagers.get(index).mousePoint(aimPoint, core.screen.drawOffsetX, core.screen.drawOffsetY);
			inputManagers.get(index).mouseLeftUp();
	}


	// ----------------------------------------------------------------------------------------------------------------------//
	// Player movements
	public void playerAim(long playerID, Point aimPoint)
	{
		/*
		try
		{
			// core.screen.graphics2d.setColor(Color.GREEN);
		 // core.screen.graphics2d.fillOval(aimPoint.x, aimPoint.y, 25, 25);
		} catch (Exception e) {}
		*/
		
		// aimPoint.x -= 1 * (core.screen.drawOffsetX);
		// aimPoint.y -= 1 * (core.screen.drawOffsetY);
		// System.out.println("drawOffsetX: " + core.screen.drawOffsetX);
		// System.out.println("drawOffsetY: " + core.screen.drawOffsetY);

		 // aimPoint.x -= 0;
		// aimPoint.y -= 0;
		
		/*
		try
		{
			core.screen.graphics2d.setColor(Color.RED);
		  core.screen.graphics2d.fillOval(aimPoint.x + core.screen.drawOffsetX, aimPoint.y + core.screen.drawOffsetY, 25, 25);
		} catch (Exception e) {}
		*/
		
		// System.out.println("playerAim");
		int playerIndex = (core.world().playerIndex(playerID));
		if (playerIndex != -1)
		{
			// System.out.println("playeAim");
			core.world().allPlayers.get(playerIndex).aimWeapons(aimPoint);
		}
	}
	public void playerShoot(long playerID)
	{
		int playerIndex = (core.world().playerIndex(playerID));
		if (playerIndex != -1)
		{
			// System.out.println("SHOOT!");
			core.world().allPlayers.get(playerIndex).fire(core.world);					// You left off here last night // TODO: You need to go from here, to player, to weapon, to projectile, and create the required methods // NEXT TODO: implement weapon creation into player and then Test player with weapon. Good luck!
		}
	}
	public void playerWalk(long playerID, int direction)
	{
		int playerIndex = (core.world().playerIndex(playerID));
		if (playerIndex != -1)
		{
			if (core.world().allPlayers.get(playerIndex).normalForceY !=0 )  // Check if on ground/jumping surface
			{
				core.world().allPlayers.get(playerIndex).push_bCenterMass(0,0,direction * 1,0);
			}
		}
	}
	public void playerJump(long playerID)
	{

		int playerIndex = (core.world().playerIndex(playerID));
		if (playerIndex != -1 && core.world().allPlayers.get(playerIndex).normalForceY !=0) // Check if on ground/jumping surface
		{
			// if (core.world().allPlayers.get(playerIndex).normalForceY !=0 )  // Check if on ground/jumping surface
			// {
				// core.world().allPlayers.get(playerIndex).push_bCenterMass(0, -10, 0, (((-1)*(core.world().allPlayers.get(playerIndex).vy())) - 100));
			 	core.world().allPlayers.get(playerIndex).push_bCenterMass(0, -10, 0, (-750));

			// }
		}
	}
	public void playerPunch(long playerID)
	{
		int playerIndex = (core.world().playerIndex(playerID));
		if (playerIndex != -1)
		{

		}
	}
	public void playerFlip(long playerID)
	{
		int playerIndex = (core.world().playerIndex(playerID));
		if (playerIndex != -1)
		{
			int direction = (myMath.getNumSign(core.world().allPlayers.get(playerIndex).vx()));
			if (direction == 0) direction = +1;
				// core.world().allPlayers.get(playerIndex).bodyAngleV += ((-1) * direction * 3.6);
			if (core.world().allPlayers.get(playerIndex).bodyAngle % 45 != 0) 
			{
				core.world().allPlayers.get(playerIndex).bodyAngle += (core.world().allPlayers.get(playerIndex).bodyAngle % 45);
			}
			else 
			{
				 core.world().allPlayers.get(playerIndex).bodyAngle += ((-1) * direction * 45);
			}
				// core.world().allPlayers.get(playerIndex).y += 100;
				// core.world().allPlayers.get(playerIndex).x += 10;
		}


	}
}




class inputManager implements Serializable
{
	// PlayerID
	long playerID = 0; // Default playerID = 0;
	String userName = "";

	// Input variables
	ArrayList<Integer> keysDown = new ArrayList<Integer>();
	Point mousePoint = new Point(0,0);
	boolean mouseLeftDown = false;
	boolean mouseRightDown = false;


	public inputManager(long playerID)
	{
		this.playerID = playerID;
	}
	
	public void userName(String uName)
	{
		this.userName = uName;
	}

	// Inputs
	public synchronized void removeKey(int keyCode)
	{
		int keyIndex = keyIndex(keyCode); // Get index
		if (keyIndex != -1) // If keyIndex = -1, then it does not already exist
		{
			keysDown.remove(keyIndex);
		}
	}
	public synchronized void addKey(int keyCode)
	{
		int keyIndex = keyIndex(keyCode); // Get index
		if (keyIndex == -1) // If keyIndex = -1, then it does not already exist
		{
			keysDown.add(keyCode);
		}

		// System.out.println("keysDown: " + keysDown);
	}
	public synchronized void mousePoint(Point mousePoint, int drawOffsetX, int drawOffsetY)
	{
		mousePoint.x -= 1 * (drawOffsetX);
		mousePoint.y -= 1 * (drawOffsetY);
		this.mousePoint = mousePoint;
	}

	public void mouseLeftDown()
	{
		this.mouseLeftDown = (true);
	}

	public void mouseRightDown()
	{
		this.mouseRightDown = (true);
	}

	public void mouseLeftUp()
	{
		this.mouseLeftDown = (false);
	}

	public void mouseRightUp()
	{
		this.mouseRightDown = (false);
	}


	// Misc
	public int keyIndex(int keyCode)
	{
		int keyIndex = -1; // -1 = Does not exist.
		for (int k = 0; k < keysDown.size(); k++)
		{
			if (keysDown.get(k) == keyCode)
			{
				// Is the desired keyCode
				keyIndex = k;
			}
		}

		return keyIndex;

	}



}
