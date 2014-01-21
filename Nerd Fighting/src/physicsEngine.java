import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;


public class physicsEngine extends Thread {

	// Method variables
	public core core;
	// public world world;

	// Working variables
	public boolean goreEnabled = true;
	int restTime = 1;
	private long initTime = 0;
	private long timePassed = 0;
	private long curTime = 0;
	private long lastTime = 0;
	private double timeFraction = 0.0;
	private ArrayList<acceleration> constForces = new ArrayList<acceleration>();


	// Constructor
	public physicsEngine (core core, world theWorld)
	{
		// Set core
		this.core = core;

		// Set world
		// this.world = theWorld;

		// Set up physicsEngine Thread
		this.setName("physicsEngine Thread");
		this.setPriority(Thread.MAX_PRIORITY);
	}

	// Run method - when started.
	public void run()
	{
		// Initialize physicsEngine variables
		curTime = System.currentTimeMillis();
		initTime = System.currentTimeMillis();
		initializeConstForces();


		// Run physicsEngine
		while (core.isRunning)
		{

			// Check if physiceEngine should be running
			while (core.clientSocket.clientMode == core.clientSocket.DEFAULTMODE) // If client is running, it is getting info from a server, and if its getting info from a server. The server physicsEngine is running; NOT clients.
			{
					// System.out.println("physicsEngine: waiting"); // For debugging
				// if (!Core.getIfPhysicsEnabled()) {
				 lastTime = curTime;
					curTime = System.currentTimeMillis();
					// timePassed = (curTime - lastTime);
					timeFraction = (timePassed / 1000.0);
				// }


				try
				{
					// Give the OS some reseting time.
					Thread.sleep(restTime); // It's nice to give the computer a rest.
				} catch (InterruptedException iE) {
				}
			}
			
			// Check if ready
			core.world();

			try // In case of errors
			{

			// Process world.
			updateTime();
			updateGravity(); // Simulates Gravity changes, Ex: Rolling, or on a ship.
			applyConstForces();
			// sumForces();
			moveEnts();

			} catch (Exception e) {System.out.println(e); }

			/*
			// TEST //
			// Physics time!
			// Circles
			for (int c = 0; c < core.world().allHail.size(); c++)
			{
				circle b = core.world().allHail.get(c);

				    b.x += b.vx;
				    b.y += b.vy;
				   int dW = core.screen.getDrawWidth();
				   int dH = core.screen.getDrawHeight();
				    if (b.x < 0) {
				      b.x = 0;
				      b.vx = -b.vx;
				    }
				    if (b.x + b.radius >= dW) {
				      b.x = dW - b.radius;
				      b.vx = -b.vx;
				    }
				    if (b.y < 0) {
				      b.y = 0;
				      b.vy = -b.vy;
				    }
				    if (b.y + b.radius >= dH) {
				      b.y = dH - b.radius;
				      b.vy = -b.vy;
				    }
			}
			*/

			try
			{
				// Give the OS some reseting time.
				Thread.sleep(restTime); // It's nice to give the computer a rest.
			} catch (InterruptedException iE) {
			}

		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------- //
	public void toggleGore()
	{
		goreEnabled = !goreEnabled;
	}
	
	// ------------------------------------------------------- //
	private void updateTime()
	{
		// Update curTime and other required components involving time.
		lastTime = curTime;
		curTime = System.currentTimeMillis();
		timePassed = (curTime - lastTime);
		timeFraction = (timePassed / 1000.0);
	}

	private void updateGravity()
	{
		// Change values for gravityX and gravityY in constForces.
		constForces.set(0, new acceleration(core.world().gravityX, core.world().gravityY));
	}

	private void initializeConstForces()
	{
		constForces.add(new acceleration(0, core.world().totalGravity)); // Gravity: 1

		// constForces.add(new acceleration(core.forceX, core.forceY)); // Other External forces: 2
	}

	private synchronized void applyConstForces()
	{
		double xAccel = 0, yAccel = 0;
		// Find the total acceleration of all constant forces.
		for (int i = 0; i < constForces.size(); i++) {
			xAccel += constForces.get(i).ax();
			yAccel += constForces.get(i).ay();
		}

		// Apply the sum acceleration to each Player.
		for (int i = 0; i < core.world().allPlayers.size(); i++) {
			player p = core.world().allPlayers.get(i);
			p.addAccel(new acceleration(xAccel, yAccel));
		}

		// Apply the sum acceleration to each projectile.
		for (int i = 0; i < core.world().allProjectiles.size(); i++) {
			projectile p = core.world().allProjectiles.get(i);
			p.addAccel(new acceleration(xAccel, yAccel));
		}

	}

	/*
	private synchronized void sumForces()
	{

		for (int i = 0; i < core.world().allPlayers.size(); i++) {
			player p = core.world().allPlayers.get(i);
			// Get the sum of all accelerations acting on object.
			acceleration theAccel = p.sumAccel();
			// Apply the resulting change in velocity.
			double vx = p.vx() + (theAccel.ax() * timeFraction);
			double vy = p.vy() + (theAccel.ay() * timeFraction);
			p.updateVelocity(vx, vy);
			// Apply drag coefficient
			// p.applyDrag(1.0 - (timeFraction * p.calcDrag()));
		}

	}
	*/


	private synchronized void moveEnts()
	{

		int increments = 100;
		for (int i = 1; i <= increments; i++)
		{
			
			core.world().checkForDead(); // Update by Checking for dead projectiles and players, etc.
			
			// Apply the sum acceleration to each projectile.
			for (int p = 0; p < core.world().allProjectiles.size(); p++) {
				projectile proj = core.world().allProjectiles.get(p);

				// Update velocity
				proj.updateVelocity(timeFraction);

				// Get the initial x and y coords.
				double oldX = proj.x(), oldY = proj.y();
				// Calculate the new x and y coords.
				double newX = oldX + ((proj.vx() * (timeFraction/increments)));
				double newY = oldY + ((proj.vy() * (timeFraction/increments)));
				proj.updatePos(newX, newY);
				
				// -- Example -- Collision with ground -- //
				// int maxDepth = core.screen.getDrawHeight();
				int maxDepth = (core.world.groundY);
				if (proj.y() >= maxDepth)
				{
					double gBounce = 0.9; // Bounce coefficent of the ground. // -- Test -- //
					proj.vy((-1)*(proj.vy)*(gBounce));
					// proj.vx((proj.vx)*(0.9));
					proj.health -= 10;
				}
				
				checkPlayerCollision(proj);

			}

			for (int p = 0; p < core.world().allPlayers.size(); p++) {
				player player = core.world().allPlayers.get(p);
				player.updateAngles(timeFraction/increments);

				/*
				// Get the initial x and y coords.
				double oldX = player.getX(), oldY = player.getY();
				// Calculate the new x and y coords.
				// if (oldX  < 10 || oldY  < 10) System.out.println("oldX: " + oldX + "\n oldY: " + oldY);
				 *
				double newX = oldX + ((p.vx() * timeFraction)/increments);
				double newY = oldY + ((p.vy() * timeFraction)/increments);
				p.updatePos(newX, newY);
				*/

				/*
				int maxChange = 1;
				int increments = 1;

				if (player.vy() >= player.vx())
					{
					 increments = (int) (Math.abs(player.vy())/maxChange);
					}
				else
				{
					 increments = (int) (Math.abs(player.vx())/maxChange);
				}
				*/

				 // checkWallCollisions(player);


				// for (int t = 1; t <= increments; t++)
				// {

					player.normalForce(0,0);

					checkWallCollisions(player);

					player.updateVelocity(timeFraction);

					/*
					// Get the sum of all accelerations acting on object.
					acceleration theAccel = player.sumAccel();
					// Apply the resulting change in velocity.
					double vx = player.vx() + (theAccel.ax() * timeFraction);
					double vy = player.vy() + (theAccel.ay() * timeFraction);
					player.updateVelocity(vx, vy);
					// Apply drag coefficient
					//	p.applyDrag(timeFraction/increments);
						// p.applyDrag(1.0 - (timeFraction * p.calcDrag()));
					 */

					player.push_bCenterMass((player.vx() * timeFraction / increments), (player.vy() * timeFraction / increments), 0 , 0);

				// }


			}

			// checkCollisions();

		}
	}


	public synchronized void checkPlayerCollision(projectile proj)
	{
		// System.out.println("checkPlayerCollision");
		for (int p = 0; p < core.world().allPlayers.size(); p++) {			
			player player = core.world().allPlayers.get(p);
			if (player.getPlayerID() != proj.playerID) 
			{	
			
				Polygon playerColl = player.playerPolygon();
				Point projPoint = new Point((int) proj.x, (int) proj.y);
				// Check if projPoint is inside playerColl
				if (playerColl.contains(projPoint))
				{
					// System.out.println("HIT!");
					// Absorb bullet/ Delete bullet
					proj.health = 0; // Kill health of proj.
					
					// Do damage to player
					player.damageHealth(proj.damage);
						// player.health -= proj.damage;
					
					
					// Add to player's damage
					long playerID = proj.playerID;
					int playerIndex = core.world().playerIndex(playerID);
					if (playerIndex != -1)
					{
						player projPlayer = (core.world().allPlayers.get(playerIndex));
						projPlayer.damage += proj.damage;
					}
					// -- Test --//
						// circle blood = new circle(10,projPoint.x, projPoint.y);
						// blood.theColor(Color.RED);
						// core.world().addHail(blood); // Draw projPoint for showing hit point, by adding it to world//
					
					if (goreEnabled)
					{
						// Create projectile = Blood splatter
						projectile blood = new projectile(playerID);
						blood.newTotalVelocity(100);
						blood.slope = (proj.slope - 180);
						blood.projColor = Color.red;
						blood.damage = 0;
						blood.lifespan = 200;
						// Adjust proj = blood
						blood.newPoint(projPoint);
						// Fire proj = blood
						blood.fire(playerID);
						// Add projectile to world, thus appearing bleeding.
						core.world().addProjectile(blood);
					}
					
				}
			}
			else
			{
				// System.out.println("Same playerID");
				// continue;
			}
			
		}
		core.world().checkForDead(); // Update by Checking for dead projectiles and players, etc.
	}

	private synchronized void checkWallCollisions(player p)
	{

		// double screenWidth = core.screen.getDrawWidth();
		// double screenHeight = core.screen.getDrawHeight();
		 double screenWidth = core.screen.getDrawWidth();
		 double screenHeight = core.world.groundY;

		double maxY, maxX;

		// Head
		 maxY = (screenHeight) - p.hRadius(); // For screen border collision
		 maxX = (screenWidth) - p.hRadius(); // For screen border collision
		if ( (p.hy() + p.hRadius())  > maxY)
		{
			double height = (maxY - p.hy());
			p.push_head(0, -height, 1);
			// System.out.println("height: " + height);

		}

		// Body
        // bCenterMass
		maxY = (screenHeight)-0 ;
		 maxX = (screenWidth)-0 ;
		if (p.bNeckY() > maxY && p.bLowerY() > maxY)
		{
			double height = (maxY - p.bCenterY());
			// int direction = 1;
			// if (p.x() < p.bLowerX()) {direction = 1; } else if (p.x() > p.bLowerX()) {direction = -1; }
			p.push_bCenterMass(0, height, 0 , height);
			// System.out.println("height: " + height);

			p.normalForce(1,1);

		}
		else
		{
			// bTop // bTop is (x,y) point.
			maxY = (screenHeight) -0 ;
			 maxX = (screenWidth) -0 ;
			if (p.bNeckY() > maxY)
			{
				double height = (maxY - p.bNeckY());
				int direction = 1;
				if (p.x() < p.bLowerX()) {direction = 1; } else if (p.x() > p.bLowerX()) {direction = -1; }
				p.push_bTop(0, -height, direction);
				// System.out.println("height: " + height);

			}
			else
			// bLower
			maxY = (screenHeight)-0 ;
			 maxX = (screenWidth)-0 ;
			if (p.bLowerY() > maxY)
			{
				double height = (maxY - p.bLowerY());
				int direction = 1;
				if (p.bLowerX() > p.x()) {direction = 1; } else if (p.bLowerX() < p.x()) {direction = -1; }
				p.push_bLower(0, -height, direction);
				// System.out.println("height: " + height);
			}
		}


		// Arm 1 Joint 1
		maxY = (screenHeight) ;
		 maxX = (screenWidth) ;
		if (p.a1j1y() > maxY)
		{
			double height = (maxY - p.a1j1y());
				int direction = 1;
			if (p.a1j1x() > p.bTorsoX()) {direction = 1; } else if (p.a1j1x() < p.bTorsoX()) {direction = -1; }
			p.push_a1j1(0, -height, direction);
			// System.out.println("height: " + height);

		}

		// Hand 1
		 maxY = (screenHeight) - p.a1hr();
		 maxX = (screenWidth) - p.a1hr();
		if (p.a1hy() > maxY)
		{
			double height = (maxY - p.a1hy());
				int direction = 1;
			if (p.a1hx() < p.a1j1x()) {direction = 1; } else if (p.a1hx() > p.a1j1x()) {direction = -1; }
			p.push_a1h(0, -height, direction);
			// System.out.println("height: " + height);

		}
		// Arm 2 Joint 1
		maxY = (screenHeight) ;
		 maxX = (screenWidth) ;
		if (p.a2j1y() > maxY)
		{
			double height = (maxY - p.a2j1y());
				int direction = 1;
			if (p.a2j1x() > p.bTorsoX()) {direction = 1; } else if (p.a2j1x() < p.bTorsoX()) {direction = -1; }
			p.push_a2j1(0, -height, direction);
			// System.out.println("height: " + height);

		}

		// Hand 2
		 maxY = (screenHeight) - p.a2hr();
		 maxX = (screenWidth) - p.a2hr();
		if (p.a2hy() > maxY)
		{
			double height = (maxY - p.a2hy());
				int direction = 1;
			if (p.a2hx() < p.a2j1x()) {direction = 1; } else if (p.a2hx() > p.a2j1x()) {direction = -1; }
			p.push_a2h(0, -height, direction);
			// System.out.println("height: " + height);

		}
		// Leg 1 Joint 1
		 maxY = (screenHeight) ;
		 maxX = (screenWidth) ;
		if (p.l1j1y() > maxY)
		{
			double height = (maxY - p.l1j1y());
				int direction = 1;
			if (p.l1j1x() > p.bLowerX()) {direction = 1; } else if (p.l1j1x() < p.bLowerX()) {direction = -1; }
			p.push_l1j1(0, -height, direction);
			// System.out.println("height: " + height);

		}
		// Foot 1
		 maxY = (screenHeight) - p.l1fr();
		 maxX = (screenWidth) - p.l1fr();
		if (p.l1fy() > maxY)
		{
			double height = (maxY - p.l1fy());
				int direction = 1;
			if (p.l1fx() < p.l1j1x()) {direction = 1; } else if (p.l1fx() > p.l1j1x()) {direction = -1; }
			p.push_l1f(0, -height, direction);
			// System.out.println("height: " + height);

		}

		// Leg 2 Joint 2
		 maxY = (screenHeight) ;
		 maxX = (screenWidth) ;
		if (p.l2j1y() > maxY)
		{
			double height = (maxY - p.l2j1y());
				int direction = 1;
			if (p.l2j1x() > p.bLowerX()) {direction = 1; } else if (p.l2j1x() < p.bLowerX()) {direction = -1; }
			p.push_l2j1(0, -height, direction);
			// System.out.println("height: " + height);

		}
		// Foot 2
		 maxY = (screenHeight) - p.l2fr();
		 maxX = (screenWidth) - p.l2fr();
		if (p.l2fy() > maxY)
		{
			double height = (maxY - p.l2fy());
				int direction = 1;
			if (p.l2fx() < p.l2j1x()) {direction = 1; } else if (p.l2fx() > p.l1j1x()) {direction = -1; }
			p.push_l2f(0, -height, direction);
			// System.out.println("height: " + height);
		}

	}



}
