
public class weatherEngine extends Thread {
	// Method variables
	public core core;
	// public world world;
	
	// Working variables.
	int restTime = 100;
	
	
	// Constructor
	public weatherEngine(core core, world theWorld)
	{
		// Set core
		this.core = core;
		
		// Set world
		// this.world = theWorld;
		
		// Set up Thread
		this.setName("weatherEngine Thread");
		this.setPriority(Thread.MIN_PRIORITY);
	}
	
	public void run()
	{
		while (core.isRunning)
		{
			
			try
			{
				// Give the OS some reseting time.
				Thread.sleep(restTime); // It's nice to give the computer a rest.
			} catch (InterruptedException iE) { 
			}
			
		}
	}
	
	// ---------------------------------------------------------------------------------- //
	// Draw
	public void drawThis()
	{
		
	}
	
	
}
