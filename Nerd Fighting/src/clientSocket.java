import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;


public class clientSocket extends Thread {

	// Method variables
	public core core;
	// public world world;
	// public controller controller;
		
	// Working variables
	Color displayColor = Color.RED;
	Color displayTextColor = Color.BLACK;
	Font displayFont = new Font("Times New Roman", Font.BOLD, 12);
	String displayMessage = "";
	String displayTitle = "Client";
	boolean clientDisplay = false;
	int restTime = 25;
	String userName = "";
		String tempName = "";
	String serverIP = "";
		String tempIP = "";
	int serverPort = 1;
		String tempPort = "";
	int maxLength = 16;
	// Server Mode
	int clientMode = -1; // Starting Server Mode
		final int OFF = -1; // server Off
		final int DEFAULTMODE = 0; // Default Mode
		final int INITMODE = 1; // Init Server Mode
			int init_Mode = 0; // Init Mode - start/default
				final int INITNAME = 1; // Init Mode - User Name
				final int INITIP = 2; // Init Mode - Server IP to connect to
				final int INITPORT = 3; // Init Mode - Server Port to connect to
				final int EXITINIT = -2; // Exit Init Mode
		final int LOCALMODE = 2;

	
	// Constructor
	public clientSocket (core core, world theWorld)
	{
		// set core
		this.core = core;
		
		// Set world
		// this.world = theWorld;
		
		// Set up Thread
		this.setName("clientSocket Thread");
		this.setPriority(Thread.MAX_PRIORITY);
		
	}
	
	public void init()
	{
		// System.out.println("Init: clientSocket"); // for debugging
		
		// Init Server mode
		clientMode = INITMODE; // Set serverMode to INITMODE
		init_Mode = INITNAME; // Start off Initializing
		// Display Message
		tempName = userName;
		displayMessage = "UserName: " + tempName;
		clientDisplay = true;
		
		// Set this to Receiver
		core.controller.updateReceiver(this);
		
		
	}
	
	public void run()
	{
		while (core.isRunning)
		{
			
			try
			{
				// Update restTime
				restTime = (int) (core.gameLoop.restTime);
			} catch (Exception e) {}
			
			while (clientMode != DEFAULTMODE)
			{
				// Server off or Server is Initializing.
				 // System.out.println("OFF/INIT - clientMode: " + clientMode); // For debugging
				
				try
				{
					// Give the OS some reseting time.
					Thread.sleep(restTime); // It's nice to give the computer a rest.
				} catch (InterruptedException iE) { }
			}
				// System.out.println("client ON"); // For debugging
			
			// Create/Reset working variables
			   ObjectOutputStream oos = null;
			      ObjectInputStream ois = null;
			      Socket socket = null;
			      
			      try {
			        // open a socket connection
			        socket = new Socket(serverIP, serverPort);
			        // open I/O streams for objects
			        oos = new ObjectOutputStream(socket.getOutputStream());
			        ois = new ObjectInputStream(socket.getInputStream());
			        
			        
			        // Create localInputManager
			        int index = (core.subconscious.inputManagerIndex(core.subconscious.playerID));
			        inputManager localInputManager = new inputManager(core.subconscious.playerID);
			        if (index != -1)
			        {
			        	 localInputManager = (core.subconscious.inputManagers.get(index));	
			        }
			        else 
			        {
			        	// Create missing local InputManager
			    		// localInputManager = new inputManager(core.subconscious.playerID);
			    		// core.subconscious.addInputManager(localInputManager);
			    	}
			    	 
			        oos.writeObject(localInputManager); // Send inputManager
			        oos.flush();
			        

			        // Get copy of updated World from the server
			          world updatedWorld = (world) (ois.readObject());
		        		// System.out.println("clientSocket: updateWorld: " + updatedWorld); // For debugging
			          core.updateWorld(updatedWorld);
			        		/* OLD - From initial writing and testing of clientServer connect component. */ // System.out.print("The date is: " + world + ".\n"); // Prints date read from server.
			        
			        // Close Streams
			        oos.close();
			        ois.close();
			        
			        /* OLD */ // Don't display an error - There is non!
			        	// clientDisplay = false;
			      } catch(Exception e) {
			    	   String errorMessage = e.getMessage();
			         System.out.println("clientSocket: Error1: " + e + "\t" + errorMessage); // For Debugging
			    	  
			    	  // Error - server must be down, etc.
			    	 // clientMode = OFF; // Turn clientSocket off for now.
			    	  
			    	 /* OLD */ // Display error
			    	  	// clientDisplay = true;
			    	  	// displayMessage = errorMessage;
			      }
			
			
			try
			{
				// Give the OS some reseting time.
				Thread.sleep(restTime); // It's nice to give the computer a rest.
			} catch (InterruptedException iE) { 
				  String errorMessage = iE.getMessage();
		         System.out.println("clientSocket: Error2: " + iE + "\t" + errorMessage); // For Debugging
			}
			
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

		if (clientDisplay)
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
			int boxWidth = 120; // boxWidth = menuWidth
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
			screen.graphics2d.setColor(displayColor);
			// Draw 3d-Rectangle
			screen.graphics2d.fill3DRect(x - displayBorder, y - displayBorder - displayItemsHeight, boxWidth + displayBorder, boxHeight + displayBorder + displayItemsHeight, raised);
			// Set Color
			screen.graphics2d.setColor(displayTextColor); // Set Color
			screen.graphics2d.setFont(displayFont); // Set Font
	        // Draw menuWindow Name
			screen.graphics2d.drawString(displayTitle, (int) (x + displayBorder), (int) (y - displayBorder) );
			// Draw message
			screen.graphics2d.drawString(displayMessage, (int) (x + displayBorder), (int) (y - displayBorder + displayItemsHeight) );

		}

	}
	
	// ------------------------------------------------------------ //
	// Get methods
	
	
	// ------------------------------------------------------------- //
	// Misc methods
	

	
	// ------------------------------------------------------------- //
	// Input handler
	public void keyPressed(KeyEvent keyEvent) {
		// System.out.println("clientSocket: keyPressed: " + keyEvent.getKeyChar() + "\n" + clientMode);
		
		// keyPressed
		int keyCode = keyEvent.getKeyCode();		
		
		if (clientMode == INITMODE)
		{
			clientDisplay = true;
			// System.out.println(init_Mode);
			
			if (init_Mode == INITNAME)
			{
				// Check Name
				if (tempName.length() > maxLength)
				{
					// Name too long: erase and start over.
					tempName = ""; 
					displayMessage = "Error: Too long.";
				}
				
				if (keyCode == KeyEvent.VK_ESCAPE)
				{
					/* OLD */ // init_Mode = EXITINIT; // Exit Init
					
					clientDisplay = false;
					clientMode = OFF; // Reset clientMode
	
					init_Mode = 0; // Reset init_Mode
					
					// Reset Receiver
					core.controller.updateReceiver(core.menu);
					
				} else if (keyCode == keyEvent.VK_ENTER)
				{
					// Set userName
					userName = tempName; // new userName
					tempName = ""; // Reset tempName
					
					// -- Update userName -- // 
					int index = core.subconscious.inputManagerIndex(core.subconscious.playerID); // Get index // Uses playerID to match and get index
					if (index == -1) // If index = -1, then it does not already exist
					{
							// Does not exist // Do nothing
					}
					else // Exists
					{
						core.subconscious.inputManagers.get(index).userName(this.userName); // Update userName
					}
					
				// Prep //
					// Init IP
					tempIP = serverIP;
					displayMessage = ("IP: " + tempIP);
					// init_Mode
					init_Mode = INITIP;
					
				} else if (keyCode == KeyEvent.VK_BACK_SPACE)
				{
					// Backspace
					try 
					{
						tempName = tempName.substring(0,tempName.length()-1);
					} catch (Exception exception) {}
					
					// Display working userName (tempName)
					displayMessage = ("UserName: " + tempName);
					
				} else if (!(keyCode == KeyEvent.VK_SHIFT) || !(keyCode == KeyEvent.VK_SPACE || !(keyCode == KeyEvent.VK_TAB)))
				{
					// Add to tempName
					tempName += (keyEvent.getKeyChar());
					
					// Display working userName (tempName)
					displayMessage = ("UserName: " + tempName);
				}
				
				
				
			}
			else if (init_Mode == INITIP)
			{
				// Check IP address
				if (tempIP.length() > maxLength)
				{
					// Address to long. Erase tempIP and start over.
					tempIP = "";
					displayMessage = "Error: Too long.";
				}
				
				if (keyCode == KeyEvent.VK_ESCAPE)
				{
					/* OLD */ // init_Mode = EXITINIT; // Exit Init
					
					clientDisplay = false;
					clientMode = OFF; // Reset clientMode

					init_Mode = 0; // Reset init_Mode
					
					// Reset Receiver
					core.controller.updateReceiver(core.menu);
					
				}
				else if (keyCode == KeyEvent.VK_PERIOD || keyCode == KeyEvent.VK_0 ||keyCode == KeyEvent.VK_1 ||keyCode == KeyEvent.VK_2 ||keyCode == KeyEvent.VK_3 ||keyCode == KeyEvent.VK_4 ||keyCode == KeyEvent.VK_5 ||keyCode == KeyEvent.VK_6 ||keyCode == KeyEvent.VK_7 ||keyCode == KeyEvent.VK_8 ||keyCode == KeyEvent.VK_9)
				{
					// Add character
					tempIP += (keyEvent.getKeyChar());
				
					// Display working serverIP
					displayMessage = ("IP: " + tempIP);
					
				}
				else if (keyCode == keyEvent.VK_BACK_SPACE)
				{
					
					// Delete last character (backspace)
					try 
					{
						tempIP = tempIP.substring(0,tempIP.length()-1);
					} catch (Exception exception) {}
					
					// Display working serverIP
					displayMessage = ("IP: " + tempIP);
					
				}
				else if (keyCode == KeyEvent.VK_ENTER)
				{
					// set serverIP to tempIP
					serverIP = tempIP;
		
					// Prep //
						// init Port
						tempPort = (Integer.toString(serverPort));
						displayMessage = ("Port: " + tempPort);
						// INITPort
						init_Mode = INITPORT;
				}
				
			}
			else if (init_Mode == INITPORT)
			{
				// Check IP address
				if (tempPort.length() > 5) // Port numbers aren't longer than 5 digits.
				{
					// Port to long. Erase tempPort and start over.
					tempPort = "";
					displayMessage = "Error: Too long.";
				}
				
				if (keyCode == KeyEvent.VK_ESCAPE)
				{
					/* OLD */ // init_Mode = EXITINIT; // Exit Init
					
					clientDisplay = false;
					clientMode = OFF; // Reset clientMode

					init_Mode = 0; // Reset init_Mode
					
					// Reset Receiver
					core.controller.updateReceiver(core.menu);
					
				}
				else if (keyCode == KeyEvent.VK_0 ||keyCode == KeyEvent.VK_1 ||keyCode == KeyEvent.VK_2 ||keyCode == KeyEvent.VK_3 ||keyCode == KeyEvent.VK_4 ||keyCode == KeyEvent.VK_5 ||keyCode == KeyEvent.VK_6 ||keyCode == KeyEvent.VK_7 ||keyCode == KeyEvent.VK_8 ||keyCode == KeyEvent.VK_9)
				{
					tempPort += (keyEvent.getKeyChar());
				}
				else if (keyCode == keyEvent.VK_BACK_SPACE)
				{
					
					try 
					{
						tempPort = tempPort.substring(0,tempPort.length()-1);
					} catch (Exception exception) {}
					
				}
				else if (keyCode == KeyEvent.VK_ENTER)
				{
					// set serverIP to tempIP
					serverPort = Integer.parseInt(tempPort);
					
					// Last setup complete - Init done.
					// this.run();
					
					// Exit INIT
					/* OLD */ // init_Mode = EXITINIT;
					clientDisplay = false;
					clientMode = DEFAULTMODE; // Start running clientMode
					init_Mode = 0; // Reset init_Mode
					// Reset Receiver
					core.controller.updateReceiver(core.menu);
					
					// Make sure serverSocket is off - No having a server running AND the client..
					core.serverSocket.serverMode = core.serverSocket.OFF; // Turns serverSocket to Off mode.
					
				}

				// Display working serverIP
				displayMessage = ("Port: " + tempPort);
			}
			
			/* OLD */ // Exiting Init is now implements into all of the above methods that require it.
			// Exit Init
			/*
			if (init_Mode == EXITINIT)
			{
				clientDisplay = false;
				clientMode = DEFAULTMODE; // Reset clientMode

					
				init_Mode = 0; // Reset init_Mode
				
				// Reset Receiver
				controller.updateReceiver(controller.menu);
			}
			*/			

			
		}
		else 
		{
			// clientDisplay = false;
		}
		
	}
	
	
}
