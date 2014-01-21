import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.io.*;
import java.net.*;
import java.util.*;

public class serverSocket extends Thread {

	// Method variables
	public core core;
	// public world world;
	// public controller controller;
	
	// Servers
	ServerSocket worldServer;
	
	
	// Working variables
	Color serverColor = Color.RED;
	Color serverTextColor = Color.BLACK;
	Font serverFont = new Font("Times New Roman", Font.BOLD, 12);
	String serverMessage = "serverSocket";
	String displayTitle = "Server";
	boolean serverDisplay = false;
	int restTime = 1;
	int serverPort = 1;
		String tempPort = "";
	
	// Server Mode
	int serverMode = -1; // Starting Server Mode
		final int OFF = -1; // server Off
		final int DEFAULTMODE = 0; // Default Mode // Running
		final int INITMODE = 1; // Init Server Mode
			int init_Mode = 0; // Init Mode - start/default
				final int INITPORT = 1; // Initialize port number for server.
				final int DISPLAYSERVER = 2; // Display information for clients to access. Ex: IP address and Port num.
				final int EXITINIT = -2; // Exit init
		final int LOCALMODE = 2;

	
	// Constructor
	public serverSocket (core core, world theWorld)
	{
		// set core
		this.core = core;
		
		// Set world
		// this.world = theWorld;
		
		// Set up Thread
		this.setName("serverSocket Thread");
		this.setPriority(Thread.MAX_PRIORITY);
		
	}
	
	public void init()
	{
		// System.out.println("Init: serverSocket"); // For debugging
		
		// Set this to Receiver
		core.controller.updateReceiver(this);
		
		// Init Server mode
		serverMode = INITMODE; // Set serverMode to INITMODE
		init_Mode = INITPORT; // Set to initialize port.
		
		// Display Message
		tempPort = (Integer.toString(serverPort));
		serverMessage = "Port: " + tempPort;
		serverDisplay = true;
	}
	
	public void run()
	{
		while (core.isRunning)
		{
			
			while (serverMode != DEFAULTMODE)
			{
				// Server off or Server is Initializing.
				try
				{
					// Give the OS some reseting time.
					Thread.sleep(restTime); // It's nice to give the computer a rest.
				} catch (InterruptedException iE) { 
				}
				
			}
			
			// Server running
		       try {
		    	   // System.out.println("Waiting for connections."); // For debugging
		        Socket client = worldServer.accept();
		        	// System.out.println("Accepted a connection from: "+ client.getInetAddress()); // For debugging
		        Connect c = new Connect(client, core);
		       } catch(Exception e) {}
	
			
			/*  // No delay, full speed, no delay server.
			try
			{
				// Give the OS some reseting time.
				Thread.sleep(restTime); // It's nice to give the computer a rest.
			} catch (InterruptedException iE) { 
			}
			*/
			
		}
		
	}
	
	// ------------------------------------------------------------------ //
	// Update Method
	public void updateMethod(controller controller)
	{
		// this.controller = controller;
	}
	
	// ----------------------------------------------------------- //
	// drawThis
	public void drawThis(screen screen)
	{

		if (serverDisplay)
			{

			// Draw server display box.
			// Get Center of drawing surface.
			int drawWidth = screen.getDrawWidth(); // Calculates the total drawing surface.
			int drawHeight = screen.getDrawHeight(); // Calculates the total drawing surface.
			int drawCenterX = drawWidth/2; // Calculates the center of the total drawing surface.
			int drawCenterY = drawHeight/2; // Calculates the center of the total drawing surface.
			// Display settings
			int displayBorder = 1;
			int displayItemsHeight = 12;
			
			// Get menu box dimensions
			int boxWidth = 150; // boxWidth = menuWidth
			int boxHeight = (displayItemsHeight * 1); // Calculate boxHeight based on number of menuItems

			/* // Parameters
			x - the x coordinate of the rectangle to be drawn.
			y - the y coordinate of the rectangle to be drawn.
			width - the width of the rectangle to be drawn.
			height - the height of the rectangle to be drawn.
			raised - a boolean that determines whether the rectangle appears to be raised above the surface or sunk into the surface.
			*/
			int x = (drawCenterX - (boxWidth/2) + screen.offsetX);
			int y = (drawCenterY - (boxHeight/2) + screen.offsetY);
			boolean raised = true;
			// Set Color
			screen.graphics2d.setColor(serverColor);
			// Draw 3d-Rectangle
			screen.graphics2d.fill3DRect(x - displayBorder, y - displayBorder - displayItemsHeight, boxWidth + displayBorder, boxHeight + displayBorder + displayItemsHeight, raised);
			// Set Color
			screen.graphics2d.setColor(serverTextColor); // Set Color
			screen.graphics2d.setFont(serverFont); // Set Font
	        // Draw menuWindow Name
			screen.graphics2d.drawString(displayTitle, (int) (x + displayBorder), (int) (y - displayBorder) );
			// Draw Message
			screen.graphics2d.drawString(serverMessage, (int) (x + displayBorder), (int) (y - displayBorder + displayItemsHeight) );

		}

	}
	
	// ------------------------------------------------------------ //
	// Get methods
	public String getLocalIP()
	{
		// IP
	 	InetAddress thisIP = null;
	 	try {
	 	     thisIP = InetAddress.getLocalHost();
	 	     }
	 	    catch(Exception e) {
	 	     e.printStackTrace();
	 	 }		 	
	 	return (thisIP.getHostAddress());
	}
	public int serverPort()
	{
		return (serverPort);
	}

	
	// ------------------------------------------------------------- //
	// Input handler
	public void keyPressed(KeyEvent keyEvent) {
		

// System.out.println("clientSocket: keyPressed: " + keyEvent.getKeyChar() + "\n" + clientMode);
		
		// keyPressed
		int keyCode = keyEvent.getKeyCode();		
		
		if (serverMode == INITMODE)
		{
			serverDisplay = true;
			// System.out.println(init_Mode);
			
			if (init_Mode == INITPORT)
			{
				// Check IP address for length
				if (tempPort.length() > 5) // Port numbers aren't longer than 5 digits. 
				{
					// Port to long. Erase tempPort and start over.
					tempPort = "";
					serverMessage = "Error: Too long. Max: 5 Digits long.";
				}
				try
				{
					// Check IP address value size
					if (Integer.parseInt(tempPort) > 49151) // Port numbers 0-1023 are Well-Known; Port numbers 1024-49151 are Registered; Above that are private or ephemeral. 
					{
						// Port to large. Erase tempPort and start over.
						tempPort = "";
						serverMessage = "Error: Too large. Max: 49151";
				}
				} catch (Exception e) { }
				
				if (keyCode == KeyEvent.VK_ESCAPE)
				{
					/* OLD */ // init_Mode = EXITINIT; 
					serverDisplay = false;
					serverMode = OFF; // Reset clientMode
					init_Mode = 0; // Reset init_Mode
					// Reset Receiver
					core.controller.updateReceiver(core.menu);
					
				}
				else if (keyCode == KeyEvent.VK_0 ||keyCode == KeyEvent.VK_1 ||keyCode == KeyEvent.VK_2 ||keyCode == KeyEvent.VK_3 ||keyCode == KeyEvent.VK_4 ||keyCode == KeyEvent.VK_5 ||keyCode == KeyEvent.VK_6 ||keyCode == KeyEvent.VK_7 ||keyCode == KeyEvent.VK_8 ||keyCode == KeyEvent.VK_9)
				{
					// Add to tempPort
					tempPort += (keyEvent.getKeyChar());
					
					// Display working serverIP
					serverMessage = ("Port: " + tempPort);
				}
				else if (keyCode == keyEvent.VK_BACK_SPACE)
				{
					// Backspace
					try 
					{
					tempPort = tempPort.substring(0,tempPort.length()-1);
					} catch (Exception exception) {}
					

					// Display working serverIP
					serverMessage = ("Port: " + tempPort);
					
				}
				else if (keyCode == KeyEvent.VK_ENTER)
				{
					// set serverIP to tempIP
					serverPort = Integer.parseInt(tempPort);
					
					// Last setup complete - Init done.
					try
					{
						worldServer = new ServerSocket(serverPort); // Create ServerSocket
					    // System.out.println("Server listening on port " + serverPort + "."); // for debugging

					} catch (Exception e) { }
					
					// Display info
					/* OLD - Now menuItem with IP address in menuWindow playMenu. */ //init_Mode = DISPLAYSERVER;
					/* // Get Display ready to display info.
					serverDisplay = true;
					serverMessage = ("IP: " + getLocalIP() + ", Port: " + serverPort);
					*/
					
					// Continue/Exit Init
					/* OLD */ // init_Mode = EXITINIT; 
					serverDisplay = false;
					serverMode = DEFAULTMODE; // Reset clientMode
					init_Mode = 0; // Reset init_Mode
					// Reset Receiver
					core.controller.updateReceiver(core.menu);
					
					
					// Make sure clientSocket is off - No having a server running AND the client..
					core.clientSocket.clientMode = core.clientSocket.OFF; // Turns clientSocket to Off mode.
					
					// -- Update userName -- //
					int index = core.subconscious.inputManagerIndex(core.subconscious.playerID); // Get index // Uses playerID to match and get index
					if (index == -1) // If index = -1, then it does not already exist
					{
							// Does not exist // Do nothing
					}
					else // Exists
					{
						core.subconscious.inputManagers.get(index).userName("Host"); // Update userName to Host
					}
					
				}
				
			}
			 // OLD - Play menu contains user IP address and Port
			/*
			else if (init_Mode == DISPLAYSERVER)
			{
				// Display info
				serverDisplay = true;
				serverMessage = ("IP: " + getLocalIP() + ", Port: " + serverPort);
				
				// Cancel when pressed,
				if (keyCode == KeyEvent.VK_ESCAPE)
				{
					init_Mode = EXITINIT; // Exit Init
				}
				else if (keyCode == KeyEvent.VK_ENTER)
				{
					// Exit Init
					init_Mode = EXITINIT;
				}
				
			}
			*/
			
			/* OLD */ // Now Exit Init is implemented into the above methods, where required.
			// Exit Init
			/*
			if (init_Mode == EXITINIT)
			{
				serverDisplay = false;
				serverMode = DEFAULTMODE; // Reset clientMode

					
				init_Mode = 0; // Reset init_Mode
				
				// Reset Receiver
				controller.updateReceiver(controller.menu);
			}			
			*/
			
		}
		else 
		{
			// serverDisplay = false;
		}
		
	}
	
	
	
}



// ------------------------------------------------------------------------------------------------------------------------------------- //
// Connection to client
class Connect extends Thread {
	   private Socket client = null;
	   private ObjectInputStream ois = null;
	   private ObjectOutputStream oos = null;
	   
	   // Core
	   private core core;
	
	   public Connect() {}
	
	   public Connect(Socket clientSocket, core theCore) {
	     client = clientSocket;
	     core = theCore;
	     try {
	      ois = new ObjectInputStream(client.getInputStream());
	      oos = new ObjectOutputStream(client.getOutputStream());
	     } catch(Exception e1) {
	         try {
	            client.close();
	         }catch(Exception e) 
	         {
	           System.out.println("serverSocket: Connect: Error1: " + e + "\t" + e.getMessage());
	         }
	         return;
	     }
	     this.start();
	   }
	
	  
	   public void run() {
	      try {
	    	 inputManager newInputManager = (inputManager) ois.readObject();
	    	 core.subconscious.addInputManager( newInputManager ); 
	    
	    	  core.isSync = false;
	    	  oos.writeObject(core.world);
	          oos.flush();
	         
	         // close streams and connections
	         ois.close();
	         oos.close();
	         core.isSync = true; 
	         client.close(); 	         
	      
	      } catch(Exception e)
	      {
	           System.out.println("serverSocket: Connect: Error2: " + e + " \t" +  e.getMessage());
	      }       
	   }
}
