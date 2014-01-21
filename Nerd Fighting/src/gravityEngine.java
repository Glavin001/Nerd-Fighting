
public class gravityEngine extends Thread {

	// Method variables
	public core core;
	// public world world;
	
	// Working variables
	int restTime = 100;

	public gravityEngine (core core, world theWorld)
	{
		// Set core
		this.core = core;
		
		// Set up world.
		// this.world = theWorld;
		
		// Set up Thread
		this.setName("gravityEngine Thread");
		this.setPriority(Thread.MIN_PRIORITY);
		
	}
	
	public void run()
	{
		while (core.isRunning)
		{
			
			// Check if subconscious should be running
			while (core.clientSocket.clientMode == core.clientSocket.DEFAULTMODE) // If client is running, it is getting info from a server, and if its getting info from a server. The server subconscious is running; NOT clients.
			{
				
				// System.out.println("gravityEngine: waiting"); // For debugging

				try
				{
					// Give the OS some reseting time.
					Thread.sleep(restTime); // It's nice to give the computer a rest.
				} catch (InterruptedException iE) {
				}
			}
			
			// Check if ready
			core.world();
			
			core.world().gravityY = core.world().totalGravity;
			core.world().gravityX = 0;
			
			try
			{
				// Give the OS some reseting time.
				Thread.sleep(restTime); // It's nice to give the computer a rest.
			} catch (InterruptedException iE) { 
			}
		}
	}
	
}
