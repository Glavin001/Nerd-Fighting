import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class controller {

	// Method variables
	core core;

	// Working variables
	int escapeKey = KeyEvent.VK_ESCAPE;
	int theReceiverNum = 0; // Current receiver.
		final int NULLRECEIVER = 0; // Receiver = null
		final int MENUNUM = 1; // Receiver = menu
		final int SCREENNUM = 2; // Receiver = screen
		final int SERVER = 3; // Receiver = serverSocket
		final int CLIENT = 4; // Receiver = clientSocket
		final int SUBCONSCIOUS = 5; // Receiver = subconscious

	int lastReceiverNum = 0; // LastReceiver: so that it can be re-initiated once current receiver does not require the controller.

	// Constructor
	public controller(core core)
	{
		// Set core
		this.core = core;

		// Set receiver
		this.theReceiverNum = SUBCONSCIOUS;
	}




	// Updating theReciever
	/*
	 public void updateToLastReceiver() // Update current receiver to last receiver.

	{
		// Note: Should add saveToLastReceiver() around here.
		// updateReceiver(lastReceiver);
	}
	*/
	public void updateReceiver(int newReceiverNum)
	{
		// Sets theReceiverNum to newReceiverNum
		this.theReceiverNum = newReceiverNum;
	}
	public void updateReceiver(menu menu)
	{
		// Menu
		// this.menu = menu;
		this.theReceiverNum = MENUNUM;
	}
	public void updateReceiver(screen screen)
	{
		// Screen
		// this.screen = screen;
		this.theReceiverNum = SCREENNUM;
	}
	public void updateReceiver(clientSocket clientSocket)
	{
		// this.clientSocket = clientSocket;
		this.theReceiverNum = CLIENT;
	}
	public void updateReceiver(serverSocket serverSocket)
	{
		// this.serverSocket = serverSocket;
		this.theReceiverNum = SERVER;
	}

	/*
	// Saving current Receiver to Last Receiver
	public void saveToLastReceiver() // Update current receiver to last receiver.
	{
		updateLastReceiver(theReceiver);
	}
	public void updateLastReceiver(menu menu)
	{
		menu lastReceiver = menu;
	}
	public void updateLastReceiver(socketServer socketServer)
	{
		socketServer lastReceiver = socketServer;
	}
	public void updateLastReceiver(screen screen)
	{
		screen lastReceiver = screen;
	}
	*/

	// Received input.
	public void keyPressed(KeyEvent keyEvent)
	{
		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.keyPressed(keyEvent); // Send event to menu.
			}
			else if (theReceiverNum == SCREENNUM) // Check if receiver is screen.
			{
				// the Receiver is screen.
				screen theReceiver = this.core.screen;
				theReceiver.keyPressed(keyEvent); // Send event to screen.
			}
			else if (theReceiverNum == SERVER) // Check if receiver is serverSocket.
			{
				// the Receiver is serverSocket.
				serverSocket theReceiver = this.core.serverSocket;
				theReceiver.keyPressed(keyEvent); // Send event to serverSocket.
			}
			else if (theReceiverNum == CLIENT) // Check if receiver is clientSocket.
			{
				// the Receiver is clientSocket.
				clientSocket theReceiver = this.core.clientSocket;
				theReceiver.keyPressed(keyEvent); // Send event to clientSocket.
			}
			else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
			{
				// the Receiver is subconscious.
				subconscious theReceiver = this.core.subconscious;
				theReceiver.keyPressed(keyEvent); // Send event to subconscious.
			}


		}

	}


	public void keyReleased(KeyEvent keyEvent)
	{
		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.keyReleased(keyEvent); // Send event to menu.
			}
			else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
			{
				// the Receiver is subconscious.
				subconscious theReceiver = this.core.subconscious;
				theReceiver.keyReleased(keyEvent); // Send event to subconscious.
			}
		}
	}


	public void mouseClicked(MouseEvent mouseEvent)
	{

		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.mouseClicked(mouseEvent); // Send event to menu.
			}
			else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
			{
					// the Receiver is subconscious.
					subconscious theReceiver = this.core.subconscious;
					theReceiver.mouseClicked(mouseEvent); // Send event to subconscious.
			}
		}

	}




	public void mouseEntered(MouseEvent mouseEvent)
	{

		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.mouseEntered(mouseEvent); // Send event to menu.
			}
		}

	}




	public void mouseExited(MouseEvent mouseEvent)
	{

		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.mouseExited(mouseEvent); // Send event to menu.
			}
		}
	}




	public void keyTyped(KeyEvent keyEvent)
	{
		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.keyTyped(keyEvent); // Send event to menu.
			}
		}
	}




	public void mousePressed(MouseEvent mouseEvent)
	{

		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.mousePressed(mouseEvent); // Send event to menu.
			}
		}
		else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
		{
				// the Receiver is subconscious.
				subconscious theReceiver = this.core.subconscious;
				theReceiver.mousePressed(mouseEvent); // Send event to subconscious.
		}
	}




	public void mouseReleased(MouseEvent mouseEvent)
	{
		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.mouseReleased(mouseEvent); // Send event to menu.
			}
				else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
			{
				// the Receiver is subconscious.
				subconscious theReceiver = this.core.subconscious;
				theReceiver.mouseReleased(mouseEvent); // Send event to subconscious.
			}
		}
	}




	public void mouseDragged(MouseEvent mouseEvent)
	{
		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.mouseDragged(mouseEvent); // Send event to menu.
			}
			else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
			{
				// the Receiver is subconscious.
				subconscious theReceiver = this.core.subconscious;
				theReceiver.mouseDragged(mouseEvent); // Send event to subconscious.
			}


		}
	}




	public void mouseMoved(MouseEvent mouseEvent)
	{
		try {
			if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
			{
				if (theReceiverNum == MENUNUM)  // Check if receiver is menu
				{
					// the Receiver is menu.
					menu theReceiver = this.core.menu; // Set theReceiver to menu.
					theReceiver.mouseMoved(mouseEvent); // Send event to menu.
				}
				else if (theReceiverNum == SUBCONSCIOUS) // Check if receiver is subconscious.
				{
					// the Receiver is subconscious.
					subconscious theReceiver = this.core.subconscious;
					theReceiver.mouseMoved(mouseEvent); // Send event to subconscious.
				}
			}
		} catch (Exception e) {}
	}




	public void actionPerformed(ActionEvent actionEvent)
	{
		if (theReceiverNum != NULLRECEIVER) // Check if there is a receiver.
		{
			if (theReceiverNum == MENUNUM)  // Check if receiver is menu
			{
				// the Receiver is menu.
				menu theReceiver = this.core.menu; // Set theReceiver to menu.
				theReceiver.actionPerformed(actionEvent); // Send event to menu.
			}
		}
	}





}
