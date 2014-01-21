import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;


public class world implements Serializable {

	/**
	 *
	 */
	// private static final long serialVersionUID = 1L;

	// All Objects
	public ArrayList<scenery> allScenery = new ArrayList<scenery>(); // Scenery
	public ArrayList<projectile> allProjectiles = new ArrayList<projectile>(); // Projectiles
	public ArrayList<weapon> allWeapons = new ArrayList<weapon>(); // Weapons
	public ArrayList<player> allPlayers = new ArrayList<player>(); // Players
	public ArrayList<line> allRain = new ArrayList<line>(); // Rain
	public ArrayList<circle> allHail = new ArrayList<circle>(); // Rain


	/* OLD */ // Methods variables
	// weatherEngine weatherEngine;
	// serverSocket serverSocket;
	// clientSocket clientSocket;
	// core core;
	// menu menu;
	// screen screen;
	// HUD HUD;

	// Working variables
	public int groundY = 1000;
	public double totalGravity = 1500;
	public double gravityY = 0;
	public double gravityX = 0;


	// -------------------------------------------------------------- //

	public world(world oldWorld)
	{
		// Copy from oldWorld
		this.allScenery = oldWorld.allScenery;
		this.allProjectiles = oldWorld.allProjectiles;
		this.allWeapons = oldWorld.allWeapons;
		this.allPlayers = oldWorld.allPlayers;
		this.allRain = oldWorld.allRain;
		this.allHail = oldWorld.allHail;

	}
	
	public world(core core)
	{
		// Set up core
		// this.core = core;

	//	this.init();

	}
	
	public synchronized void copyWorld(world oldWorld)
	{
		// Copy from oldWorld
		this.allScenery = oldWorld.allScenery;
		this.allProjectiles = oldWorld.allProjectiles;
		this.allWeapons = oldWorld.allWeapons;
		this.allPlayers = oldWorld.allPlayers;
		this.allRain = oldWorld.allRain;
		this.allHail = oldWorld.allHail;

	}

	public synchronized void init()
	{
		// Create scenery

		// Spawn weapons
			// Create Weapon1
			weapon weap1 = new weapon(0);
			// Create projectile
			projectile proj1 = new projectile(0);
			proj1.newTotalVelocity(15000);
			// Add proj to weapon
			weap1.setProj1(proj1);
			weap1.setProj2(proj1);
			// Add weapon to allWeapons
			allWeapons.add(weap1);
	}

	// Check if objects are still "alive" (should exist)
	public synchronized void checkForDead()
	{
		ArrayList<Integer> deadList = new ArrayList<Integer>();

		// Check projectiles
		for (int p = 0; p < allProjectiles.size(); p++)
		{
			if (!(allProjectiles.get(p).isAlive()))
			{
				deadList.add(p);
			}
		}
			// System.out.println(deadList); // For debugging
		// Kill projectiles
		for (int d = (deadList.size() - 1); d >= 0; d--)
		{
			removeProjectile(deadList.get(d));
		}
		
		// Reset deadList
		deadList = new ArrayList<Integer>();
		
		// Check projectiles
		for (int p = 0; p < allPlayers.size(); p++)
		{
			if (!(allPlayers.get(p).isAlive()))
			{
				deadList.add(p);
			}
		}
			// System.out.println(deadList); // For debugging
		// Kill projectiles
		for (int d = (deadList.size() - 1); d >= 0; d--)
		{
			// System.out.println("Player Dead!: " + d);
				// -- TEST -- //
				player player = allPlayers.get(d); // Getting player
			//	player.legStrength = 0; // Makes it so player cannot stand under any force.
			//	if (player.normalForceY !=0) player.vy -= 1; // Pushing player up, so that they will fall, and as above states, will not be able to stand; thus appearing to fall and die.
				// player.y -= 1;
			// removeProjectile(deadList.get(d));
		}


	}

	// ------------------------------------------------------------- //

	public synchronized void drawThis(screen screen)
	{
		// Draw 
		screen.graphics2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		screen.graphics2d.setColor(Color.BLACK);
		// Draw hail
		for (int h = 0; h < allHail.size(); h++)
		{
			allHail.get(h).drawThis(screen);
		}
		// Draw players
		for (int p = 0; p < allPlayers.size(); p++)
		{
			allPlayers.get(p).drawThis(screen);
		}
		/*
		// Draw projectiles
		for (int p = 0; p < allProjectiles.size(); p++)
		{
			System.out.println("p:" + p);
			allProjectiles.get(p).drawThis(screen);
		}
		*/

	}
	
	public synchronized void drawBackground(screen screen)
	{
		// Draw 
		screen.graphics2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		screen.graphics2d.setColor(Color.BLACK);
		
		// Draw ground line
		int x1 = screen.offsetX;
		int y1 = this.groundY + screen.drawOffsetY;
		// int y1 = screen.getDrawHeight() + screen.drawOffsetY + screen.offsetY;

		int x2 = screen.getDrawWidth() + screen.offsetX;
		int y2 = this.groundY + screen.drawOffsetY;
		// int y2 = screen.getDrawHeight() + screen.drawOffsetY + screen.offsetY;

		screen.graphics2d.drawLine(x1, y1, x2, y2);
		
		// Draw scenery
		for (int h = 0; h < allScenery.size(); h++)
		{
			allScenery.get(h).drawThis(screen);
		}
		
	}
	
	

	// ------------------------------------------------------------- //

	// Update Methods variables.
	/*// OLD //
	public void updateMethod(serverSocket newServerSocket)
	{
		// this.serverSocket = newServerSocket;
	}
	public void updateMthod(clientSocket newClientSocket)
	{
		// this.clientSocket = newClientSocket;
	}
	public void updateMethod(core newCore)
	{
		// this.core = newCore;
	}
	public void updateMethod(menu newMenu)
	{
		// this.menu = newMenu;
	}
	public void updateMethod(screen newScreen)
	{
		// this.screen = newScreen;
	}
	public void updateMethod(HUD newHUD)
	{
		// this.HUD = newHUD;
	}
	*/

	// ------------------------------------------------------------------------ //
	// Add objects
	public synchronized void addHail(circle hail)
	{
		this.allHail.add(hail);
	}
	public synchronized void addPlayer(player newPlayer)
	{
		if (playerIndex(newPlayer.getPlayerID()) == -1)
		{
			this.allPlayers.add(newPlayer);
		}
	}
	public synchronized void removePlayer(int index)
	{
		this.allPlayers.remove(index);
	}
	public synchronized void addProjectile(projectile newProjectile)
	{
		this.allProjectiles.add(newProjectile);
	}
	public synchronized void removeProjectile(int index)
	{
		this.allProjectiles.remove(index);
	}

	// ------------------------------------------------------------------------ //
	public synchronized int playerIndex(long playerID) // Get index of player with playerID
	{
		int playerIndex = -1; // If it is still -1, then playerID could not be found, and does not exist.

		for (int p = 0; p < allPlayers.size(); p++)
		{
			if (allPlayers.get(p).getPlayerID() == playerID)
			{
				playerIndex = p;
				break;
			}
		}

		return playerIndex;
	}

}
