import java.util.ArrayList;


public class gameLoop extends Thread {

	// Method variables
	 core core;


	// World
	// world world; // World
	double restTime = 1.0;

	// Working variables.
	public final int MAXLAYERS = 1;
	// FPS
	public float idealFPS = (float) 30.0;
	public boolean bottleneck = true;
	public float fps = 0; // Frames-Per-Second
	public ArrayList<Float> frameList = new ArrayList<Float>();
	public double fpsSum = 0;
		// public int frames = 0;		// total Frames count
		// public long totalTime = 0;	// Total Time
    public long curTime = System.currentTimeMillis(); // the Current Time in Milliseconds
    public long lastTime = (curTime); // the Last "Current" Time


	// Constructor
	public gameLoop (core core)
	{
		// Set core
		this.core = core;

		// Set up Thread
		this.setName("gameLoop Thread");
		this.setPriority(Thread.MAX_PRIORITY);

	}

	// -------------------------- //
	// Update Methods variables.
	public void updateMethod(core newCore)
	{
		this.core = newCore;
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
	// Update world
	public void updateWorld(world newWorld)
	{
		// this.world = newWorld;
	}

	// --------------------------- //
	// Get methods
	public float fps()
	{
		return (this.fps);
	}
	// Set methods
	public void toggleBottleneck()
	{
		bottleneck = !bottleneck;

		if (bottleneck) // bottleneck is true, and is enabled - Thus, it was disabled before.
		{
			// Reset FPS variables for new FPS calculation for bottleneck to be more accurate and faster.
			frameList = new ArrayList<Float>();


			// Reset working variables - Ex: restTime
			restTime = 1;
			fpsSum = 0;
		    curTime = System.currentTimeMillis(); // the Current Time in Milliseconds
		    lastTime = curTime;

			// Update Frame rate - Initialize new frameList and continue from fresh.
			updateFPS();


		}

	}


	// -------------------------- //
	// Calculate frame rate
	public float updateFPS()
	{
		// Update currTime
	    curTime = System.currentTimeMillis(); // the Current Time in Milliseconds
		// Calculate new frame
	    float timeDiff = (curTime - lastTime);
	     		// System.out.println("timeDiff: " + timeDiff); // For debugging
	     if (timeDiff == 0) timeDiff = 1;
	    float newFPS = (float) (1000/timeDiff); // 1000 Miliseconds = 1 Second
	    lastTime = curTime;
	     	// System.out.println("newFPS: " + newFPS); // For debugging

	     if (frameList.size() > 1000) // 1000 Miliseconds = 1 Second
	     {
	    	 	// System.out.println("Delete(0): " + frameList.get(0)); // For debugging
	    	 fpsSum -= frameList.get(0); // Subtract outdated fps.
	    	 frameList.remove(0); // Delete outdated fps.
	     }
	     frameList.add((float) newFPS); // Add newFPS.
	     fpsSum += newFPS; // AddnewFPS,
	    	// System.out.println("fpsSum: " + fpsSum); // For debugging

	    this.fps = (float) ((fpsSum / frameList.size()));
	    	// System.out.println("fps: " + this.fps); // For debugging


	    // Bottleneck gameLoop to idealFPS.
	    if (bottleneck)
	    {
		    // Adjust restTime to idealFPS
	        if (restTime == 0) restTime = 1;
	        if (fps != 0)
	        {
	        	 // System.out.println(restTime + "\n" + fps + "/" + idealFPS + "\n");    // For debugging
	        	restTime = (double) ((double) restTime * ((double) fps/ (double) idealFPS));
	        	if (restTime > 100) // If restTime is over 1000 then fix it. - Cause: At the beginning of FPS-Calculation, the frame-rate is off and maybe cause then restTime to be miscalculated.
	        	{
	            	 // System.out.println(restTime + "\n" + fps + "/" + idealFPS);   // For debugging
	            	 restTime = 100;
	        	}
	        }

	    }
	    else
	    {
	    	// Reset restTime to 1 millisecond. (Min delay, without being 0)
	    	restTime = 1;
	    }

		return (fps);
	}
	
	public void updatePlayerFocus(long playerID)
	{
		// View Point: offsets
        // core.screen.drawOffsetX = 0;
       //  core.screen.drawOffsetY = 0;
      if (core.screen.autoFocus)
      {
        	try
	        {
        		// Get playerIndex with playerID, again
        		int index = (core.world().playerIndex(playerID));        		
	        	 
        		core.screen.drawOffsetX = (int) (core.screen.getDrawWidth()/2 -  core.world().allPlayers.get(index).x());
	        	 core.screen.drawOffsetY = (int) (core.screen.getDrawHeight()/2 -  core.world().allPlayers.get(index).y());
	        	 
	        } catch (Exception e)
	        {
	        	System.out.println("Exception: " + e);
	         core.screen.drawOffsetX = 0;
	         core.screen.drawOffsetY = 0;
	        }
      }

	}

	// run method - when started.
	public void run()
	{

		// Set up some variables for FPS calculation, etc.
	     fps = 0;		// Frames-Per-Second
	    // frames = 0;		// total Frames count
	    // totalTime = 0;	// Total Time
	     curTime = System.currentTimeMillis(); // the Current Time in Milliseconds
	     lastTime = curTime; // the Last "Current" Time

		while (core.isRunning)
		{

			try
			{

				updateFPS();

				/*// OLD - New, more accurate FPS calculator implemented above.
				// Calculations for Frames-Per-Second.
		        lastTime = curTime;
		        curTime = System.currentTimeMillis();
		        totalTime += curTime - lastTime;	// Calculating time changed from lastTime and adding to totalTime.
		        if (totalTime >= 1000)
		        {	// if totalTime is greater than 1000 (milliseconds, 1000 milliseconds = 1 second) then..
		        		//-OLD-// // totalTime -= 1000;	// Decrease totalTime by 1000 (milliseconds, 100 milliseconds = 1 second)
		        	totalTime = 0;
		        	fps = frames; 		// Setting Frames-Per-Second to the count of Frames (of this second)
		        	frames = 0;			// Resetting frames to 0

		        	 // Adjust restTime to idealFPS
			        if (restTime == 0) restTime = 1;
			        if (fps != 0)
			        {
			        	// System.out.println(restTime + "\n" + fps + "/" + idealFPS);
			        	restTime = (double) ((double) restTime * ((double) fps/ (double) idealFPS));
			        }

		        }
		        ++frames; // Adding to Frames count
				*/

				// Update window size
		        core.screen.updateWindowSize();

				// Refresh back buffer
		        core.screen.refreshBackBuffer();

				// ------------------- //


				try // For any possible errors that would crash application.
				{

					// Update world
					core.world().checkForDead();
					
					// update focus
					this.updatePlayerFocus(core.subconscious.playerID);

				// ----------------- //
					
					// draw background	- Layer 0
					core.world().drawBackground(core.screen);
					
					// Draw Layers.
					for (int layerNum = 0; layerNum <= MAXLAYERS; layerNum++)
					{

					// draw weather
						core.world().drawThis(core.screen);
					// draw Scenery

					// draw Projectiles
					for (int p = 0; p < core.world().allProjectiles.size(); p++)
					{
						core.world().allProjectiles.get(p).drawThis(core.screen);
					}
					// draw Players
					}
				// --------------- //

				// draw HUD - if one.
					core.HUD.drawThis(core.screen,this);

				// draw Menu - if anything.
					core.menu.drawThis(core.screen);

				// draw socketClient - For client console/errors, etc - If any
					core.clientSocket.drawThis(core.screen);

				// draw socketServer - For server console/errors, etc - If any
					core.serverSocket.drawThis(core.screen);

				} catch (Exception e) { System.out.println(e);}
				// ----------------------------------------------------------- //

				// Blit image and Flip
					core.screen.graphics = core.screen.bufferStrategy.getDrawGraphics();
					core.screen.graphics.drawImage(core.screen.buffer, 0, 0, null);
				if (!core.screen.bufferStrategy.contentsLost()) core.screen.bufferStrategy.show();

				// -------------------------------------------------------- //

				// Give the OS some reseting time.
				Thread.sleep((int) restTime); // It's nice to give the computer a rest.

			} catch (InterruptedException iE) {

			} finally // After drawing...
			{
				// Release resources
				 if (core.screen.graphics != null) core.screen.graphics.dispose(); // if there IS something (not nothing) in screen.graphics then dispose of it.
			     if (core.screen.graphics2d != null) core.screen.graphics2d.dispose();  // if there IS something (not nothing) in screen.graphics2d then dispose of it.
			}

		}

			// System.out.println("Exit"); // For debugging
		System.exit(0);

	}


}
