import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.util.ArrayList;



public class menu {

	// Method varialbes
	core core;


	// Working variables.
	public ArrayList<menuWindow> menus = new ArrayList<menuWindow>();
	boolean menuOpen = false;
	menuWindow mainMenu;


	public menu (core core)
	{
		// Set core
		this.core = core;
		
		// Initialize menu structure.
		this.init();

	}

	public void init()
	{
		// Initialize menu structure.
		 mainMenu = new menuWindow(this,"Main"); // Creates mainMenu
		 
		 // Separator / Blank
		 menuItem blankItem = new menuItem(this, ""); // Blank item for separator; if needed later.
		 
		// About
		 menuItem aboutItem = new menuItem(this, "About"); // Create a menuItem
		 	menuWindow aboutMenu = new menuWindow(this, "About"); // Create aboutMenu
		 	aboutMenu.setMenuWidth(150); // setting new menuWidth
		 	// Author
		 	menuItem author = new menuItem(this, "Author: Glavin Wiechert");
		 	aboutMenu.addMenuItem(author);
		 	// Application
		 	menuItem appName = new menuItem(this, "Application: Nerd Fighting!");
		 	aboutMenu.addMenuItem(appName);
		 	// Version
		 	menuItem appVersion = new menuItem(this, "Version: 1.0b");
		 	aboutMenu.addMenuItem(appVersion);
		 	// IP
		 	InetAddress thisIP = null;
		 	try {
		 	     thisIP = InetAddress.getLocalHost();
		 	     }
		 	    catch(Exception e) {
		 	     e.printStackTrace();
		 	 }		 	
		 	menuItem userIP = new menuItem(this, "You're IP is: " + thisIP.getHostAddress());
		 	aboutMenu.addMenuItem(userIP);
		 aboutItem.setAction(aboutMenu); // setAction to open aboutMenu.
		 mainMenu.addMenuItem(aboutItem); // add aboutItem with aboutMenu to mainMenu.
		// Play
		 menuItem playItem = new menuItem(this, "Play"); // Create a menuItem
		 	menuWindow playMenu = new menuWindow(this, "Play"); // Create menuWindow playMenu
		 	// Create Game 
		 	menuItem createGame = new menuItem(this, "Create Game"); // Create menuItem createGame
		 	createGame.setAction(10); // setAction to specialAction: 10 = Create Game.
		 	playMenu.addMenuItem(createGame); // Add createGame to playMenu.
		 	// Join Game
		 	menuItem joinGame = new menuItem(this, "Join Game"); // Create menuItem joinGame
		 	joinGame.setAction(11); // setAction to specialAction: 11 = Join Game.
		 	playMenu.addMenuItem(joinGame); // Add joinGame to playMenu.
		 	// Add your IP menu (Again) for server access, etc.
		 	playMenu.setMenuWidth(150); // Adjusting width to fit IP
		 	playMenu.addMenuItem(userIP);
		 playItem.setAction(playMenu); // setAction to open playMenu.
		 mainMenu.addMenuItem(playItem); // Add playItem with playMenu to mainMenu.

		// Screen
		menuWindow screenMenu = new menuWindow(this,"Screen");	
			// Toggle Full-Screen Item
			menuItem toggleFullScreen = new menuItem(this, "Toggle Full-Screen"); // Create togglefullScreen menuItem
			toggleFullScreen.setAction(1); // setAction to specialAction: 1 = toggle FullScreen.
			screenMenu.addMenuItem(toggleFullScreen); // add toggleFullScreen to screenMenu.
			// Calibrator Item
		 	menuItem calibrator = new menuItem(this, "Calibrator"); // Create a menuItem
		 	calibrator.setAction(2); // setAction to screen.calibrator // 2 = Triggers calibration of screen.
		 	screenMenu.addMenuItem(calibrator); // Add menuItem Calibrator to screenMenu.
		 	// Graphics Menu
		 	menuWindow graphicsMenu = new menuWindow(this,"Graphics"); // Create graphics menuWindow
		 	graphicsMenu.setMenuWidth(150); // setting new menuWidth
		 		// toggleGore Item
		 		menuItem toggleGore = new menuItem(this, "Toggle Gore"); // Menu item to toggle gore
		 		toggleGore.setAction(3); // setAction to toggle gore // 3 = Triggers toggle gore.
		 		graphicsMenu.addMenuItem(toggleGore); // Add toggleGore to graphicsMenu. 
		 		// toggleBottleneck Item
		 		menuItem toggleBottleneck = new menuItem(this, "Toggle FPS-Bottleneck"); // Menu item to toggle gore
		 		toggleBottleneck.setAction(4); // setAction to toggle bottleneck // 4 = Triggers toggle bottleneck.
		 		graphicsMenu.addMenuItem(toggleBottleneck); // Add toggleGore to graphicsMenu. 
		 	menuItem graphicsItem = new menuItem(this, "Graphics"); // Graphics menuItem
		 	graphicsItem.setAction(graphicsMenu); // Set action to open graphicsMenu
		 	screenMenu.addMenuItem(graphicsItem); // Add graphicsItem with graphicsMenu to screenMenu.	
		 menuItem screenItem = new menuItem(this, "Screen"); // Create screenItem menuItem 
		 screenItem.setAction(screenMenu); // setAction to open screenMenu.
		 mainMenu.addMenuItem(screenItem); // Add screenItem with screenMenu to mainMenu

		 // Blank
		 mainMenu.addMenuItem(blankItem); // Add a separator.
		 
		 // Exit
		 menuItem exitItem = new menuItem(this,"Exit");
		 exitItem.setAction(-1); // setAction to special Action: -1 = Exit application.
		 mainMenu.addMenuItem(exitItem); // add exitItem to mainMenu.

	}

	// ------------------------------------ //
	public void updateMethod(screen theScreen)
	{
		// this.screen = theScreen;
	}

	// ------------------------------------ //
	public void drawThis(screen screen)
	{
		// Draw menu on screen.
		if (menuOpen)
		{

			menuWindow currentMenu = (getLastMenuWindow());
			currentMenu.drawThis(screen);

		}
		else
		{
			// Menu not open.
			// Do not draw anything.
		}

	}

	// ------------------------------------- //
	// Set/Toggle methods
	public void toggleMenu()
	{
		getLastMenuWindow(); // If there is no menus then this will create one: mainMenu.
		this.menuOpen = !menuOpen; // Toggles menuOpen.
		
		if (!menuOpen)
		{
			core.controller.updateReceiver(core.controller.SUBCONSCIOUS);
		}
		
	}

	// ------------------------------------ //
	// Get methods
	public menuWindow getLastMenuWindow()
	{
		int countOfMenus = (menus.size()); // Get size of list of Menus
		if (countOfMenus == 0) // Check if there is any menus in the list of menus.
		{ // No menus - Null
			 addMenu(this.mainMenu); // Add Main Menu to list of Menus - Making the list contain Main Menu, and no longer be Empty/Null.
			 countOfMenus = (menus.size()); // Get size of list of Menus
		}
		return ((menuWindow) this.menus.get( countOfMenus - 1 ));  // Return last menu. // In java, Index 0 is item 1, thus countOfMenus-1 gives us the last item in the list in Java ArrayList.
	}

	// -------------------------------------- //
	// Misc methods
	public void upMenuPath()
	{
		int countOfMenus = (menus.size()); // Getting total count of menus
		if (countOfMenus > 1) // Check if there is any menus.
		{ // There is at least 2 menu
			this.menus.remove(countOfMenus - 1); // Remove last menu // Last menu is the menu that is drawn: thus by removing the last menu it changes the current menu to the menu prior.
		}
		else if (countOfMenus == 1)
		{ // There is only 1 menu
			this.menus.remove(countOfMenus - 1); // Remove last menu
			// There should be no menus left.
			toggleMenu(); // turn off menu

		}
		else if (countOfMenus == 0)
		{ // There are no menus.
			toggleMenu(); // turn on menu
		}
	}
	public void addMenu(menuWindow newMenu)
	{
		this.menus.add(newMenu); // Add menu // The Last menu is drawn: thus this menu will be drawn next, for it is the last menu now.
	}

	// --------------------------------------------------------------------------------------------------------------------------------------- //

	// Menu input handlers
	public void keyPressed(KeyEvent keyEvent)
	{
		int keyCode = keyEvent.getKeyCode();

		switch (keyCode)
		{
			case (KeyEvent.VK_ESCAPE):
			{
				upMenuPath();
				break;
			}
			case (KeyEvent.VK_UP):
			{
				menuWindow currMenu = getLastMenuWindow();
				currMenu.moveUpSelection();
				break;
			}
			case (KeyEvent.VK_DOWN):
			{
				menuWindow currMenu = getLastMenuWindow();
				currMenu.moveDownSelection();
				break;
			}
			case (KeyEvent.VK_ENTER):
			{
				menuWindow currMenu = getLastMenuWindow();
				currMenu.initAction();
				break;
			}

		}


	}



	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

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

	}

	public void actionPerformed(ActionEvent actionEvent) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}


}


// --------------------------------------------------------------------------------------------------------------------------------- //

class menuItem
{

	// Menu
	menu menu;

	// Settings
	 String menuItemName = "";
	 int menuItemAction = 0; // Type of Action for this menuItem
	 	final int NULLACTION = menuItemAction = 0;
	 	final int WINDOWACTION = 1; // Action loads actionMenu
	 		menuWindow actionMenu;
	 	final int VALUECHANGER = 2; // Action loads actionValueChanger
	 		valueChanger actionValueChanger;
	 	final int SPECIALACTION = 3; // Special Action - Ex: screen.Calibrator
	 		int specialActionNum = 0;
	 			// Screen
	 			final int TOGGLEFULLSCREEN = 1; // Toggle Full-Screen
	 			final int CALIBRATOR = 2; // Calibrates screen.
	 			final int TOGGLEGORE = 3; // Toggle Gore
	 			final int TOGGLEBOTTLENECK = 4; // Toggle Gore

	 			// Play
	 			final int CREATEGAME = 10; // Create Game
	 			final int JOINGAME = 11; // Join Game
	 			// Exit
	 			final int EXITNUM = -1; // Exit

	// Working variables
	 		/* OLD */ // boolean isSelected = false;
	 Color menuItemTextColor = Color.white;
	 int menuItemFontSize = 11;
	 Font menuItemFont = new Font("Arial", Font.ITALIC, menuItemFontSize);
	 Color menuItemColor = Color.blue;
	 Color menuItemHightlightColor = Color.green;


	// ------------------------------------------------- //

	public menuItem(menu menu, String menuItemName)
	{
		// Creating access to menu.
		this.menu = menu;

		// Setting Menu Item Name
		this.menuItemName = menuItemName;

	}

	public void initAction ()
	{

			// System.out.println(menuItemName); // For Debugging
		// Menu has been selected and Entered
		if (menuItemAction == NULLACTION)
		{
			// No set action.

		}
		else if (menuItemAction == WINDOWACTION)
		{
			// Opens new menuWindow
			menu.addMenu(actionMenu);

		}
		else if (menuItemAction == VALUECHANGER)
		{
			//

		}
		else if (menuItemAction == SPECIALACTION)
		{
		// Screen
		 if (specialActionNum == CALIBRATOR)
			{ // Calibrate screen
				menu.core.screen.calibrateScreen();
			}
			else 
			if (specialActionNum == TOGGLEGORE)
			{ // Toggle Gore
				menu.core.physicsEngine.toggleGore();
			}
			else 
			if (specialActionNum == TOGGLEBOTTLENECK)
			{ // Toggle Bottleneck
					menu.core.gameLoop.toggleBottleneck();
			}
			else
			if (specialActionNum == TOGGLEFULLSCREEN)
			{ // Toggle Full-Screen
				menu.core.screen.toggleFullScreen();
			}
		// Play
			if (specialActionNum == CREATEGAME)
			{ // Create Game
				menu.core.serverSocket.init();
			}
			else
			if (specialActionNum == JOINGAME)
			{ // Join Game
				menu.core.clientSocket.init();
			}
		// Exit
			else if (specialActionNum == EXITNUM)
			{ // Exit
				menu.core.isRunning(false);
			}

		}

	}

	// ----------------------------------------------- //
	// Set action
	public void setAction(menuWindow menuWindow)
	{
		// Set action type to WINDOWACTION
		this.menuItemAction = this.WINDOWACTION;

		// Set actionMenu to menuWindow
		this.actionMenu = menuWindow;
	}
	public void setAction(valueChanger valueChanger)
	{
		// Set action type to WINDOWACTION
		this.menuItemAction = this.VALUECHANGER;

		// Set actionMenu to menuWindow
		this.actionValueChanger = valueChanger;
	}
	public void setAction(int theSpecialActionNum)
	{
		// set action type to SPECIALACTION
		this.menuItemAction = this.SPECIALACTION;
		// Set specialActionNum
		this.specialActionNum = theSpecialActionNum;
	}
	// ---------------------------------------------- //
	// Draw
	public void drawThis(screen screen, boolean isSelected, int x, int y, int width, int height)
	{
		// Draw Selection highlight, if selected.
		if (isSelected)
		{
			// Draw highlight.
			boolean raised = false;
			// Set Color
			screen.graphics2d.setColor(menuItemHightlightColor);
			// Draw 3d-Rectangle
			screen.graphics2d.fill3DRect(x, y, width, height, raised);
		}

		// Draw menuItemName
		screen.graphics2d.setFont(menuItemFont); // Set Font
        screen.graphics2d.setColor(menuItemTextColor); // Set Color
        /*
        Draws the text given by the specified string, using this graphics context's current font and color. The baseline of the leftmost character is at position (x, y) in this graphics context's coordinate system.
		Parameters:
		str - the string to be drawn.
		x - the x coordinate.
		y - the y coordinate.
        */
        String marker = "  ";
        if (isSelected) marker = ">";
        updateMenuItemName();
        screen.graphics2d.drawString(marker + menuItemName, (int) x, (int) (y + (1 * height) - 1) );

	}
	// update menuItemName
	public void updateMenuItemName()
	{
		String extraName = "";
		if (specialActionNum == CREATEGAME)
		{
			if (menu.core.serverSocket.serverMode != menu.core.serverSocket.OFF)
			{
				extraName = ("Port: " + (String) Integer.toString(menu.core.serverSocket.serverPort()));
			}
			else
			{
				extraName = "Off";
			}
			menuItemName = "Create Game - " + extraName;
		}
		else 
		if (specialActionNum == JOINGAME)
		{
			if (menu.core.clientSocket.clientMode != menu.core.clientSocket.OFF)
			{
				extraName = ("Port: " + (String) Integer.toString(menu.core.clientSocket.serverPort));
			}
			else
			{
				extraName = "Off";
			}
			menuItemName = "Join Game - " + extraName;
		}
		else 
		if (specialActionNum == TOGGLEBOTTLENECK)
		{
			if (menu.core.gameLoop.bottleneck)
			{
				extraName = "On";
			}
			else
			{
				extraName = "Off";
			}
			menuItemName = "Toggle Bottleneck - " + extraName;
		}
		else 
			if (specialActionNum == TOGGLEGORE)
			{
				if (menu.core.physicsEngine.goreEnabled)
				{
					extraName = "On";
				}
				else
				{
					extraName = "Off";
				}
				menuItemName = "Toggle Gore - " + extraName;
			}
		
		
	}
}

// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

class valueChanger
{

	// Menu
	menu menu;

	// Settings
	String valueChangerName = "";

	// Working variables

	public valueChanger(menu menu, String valueChangerName)
	{
		// Creating access to menu
		this.menu = menu;

		// Setting valueChanger name
		this.valueChangerName = valueChangerName;
	}




}


// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

class menuWindow
{
	// Menu
	menu menu;

	// Settings
	 ArrayList<menuItem> menuItems = new ArrayList<menuItem>();
	 String menuWindowName = "";

	// Working variables
	 int menuSelected = 0; // Index number of selected menu.
	 int menuBorder = 1; // Border size in pixels.
	 Color menuColor = Color.blue; // Color of this menuWindow.
	 Color menuNameColor = Color.white; // Color of menuWindowName when drawn.
	 Font menuFont = new Font("Times New Roman", Font.BOLD, 12);
	 int menuWidth = 120;
	 int menuItemsHeight = 12;


	// ----------------------------------------------------------- //

	public menuWindow(menu menu, String menuWindowName)
	{
		// Creating access to menu.
		this.menu = menu;

		// Setting menuWindow Name
		this.menuWindowName = menuWindowName;
	}

	public void drawThis(screen screen)
	{

		// Draw menu box.
		// Get Center of drawing surface.
		int drawWidth = screen.getDrawWidth(); // Calculates the total drawing surface.
		int drawHeight = screen.getDrawHeight(); // Calculates the total drawing surface.
		int drawCenterX = drawWidth/2; // Calculates the center of the total drawing surface.
		int drawCenterY = drawHeight/2; // Calculates the center of the total drawing surface.
		// Get menu box dimensions
		int boxWidth = menuWidth; // boxWidth = menuWidth
		int boxHeight = (menuItemsHeight * (menuItems.size())); // Calculate boxHeight based on number of menuItems

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
		screen.graphics2d.setColor(menuColor);
		// Draw 3d-Rectangle
		screen.graphics2d.fill3DRect(x - menuBorder, y - menuBorder - menuItemsHeight, boxWidth + menuBorder, boxHeight + menuBorder + menuItemsHeight, raised);
		// Set Color
		screen.graphics2d.setColor(menuNameColor); // Set Color
		screen.graphics2d.setFont(menuFont); // Set Font
        // Draw menuWindow Name
		screen.graphics2d.drawString(menuWindowName, (int) (x + menuBorder), (int) (y - menuBorder) );

		// Draw menuItems
		for (int i = 0; i < (menuItems.size()); i++)
		{
			menuItem currItem = (menuItems.get(i)); // Get menuItem currItem.
			boolean isSelected = false; // Create isSelected variable.
			if (i == menuSelected) {isSelected = true;} else {isSelected = false;} // Check if this menuItem is selected.
			currItem.drawThis(screen, isSelected, (int) x + menuBorder, (int) (y + (i * menuItemsHeight) + menuBorder), menuWidth - (2 * menuBorder), menuItemsHeight - (2 * menuBorder)); // Draw this menuItem.
		}


	}
	
	// --------------------------------------------------- //
	// Set
	public void setMenuWidth(int newWidth)
	{
		this.menuWidth = newWidth;
	}

	// -------------------------------------------------- //
	public void addMenuItem(menuItem newMenuItem)
	{
		this.menuItems.add(newMenuItem);
	}

	// --------------------------------------------------- //
	// Movements
	public void moveUpSelection() // Move up selection of menuItems.
	{
			// System.out.println("1:" + this.menuSelected); // For debugging
		// Decrease selected number by 1.
		this.menuSelected = (this.menuSelected - 1);
		// Correct if selection number out of bounds.
			// System.out.println("2: " + this.menuSelected);  // For debugging
		if (this.menuSelected < 0)
		{
			this.menuSelected = (this.menuItems.size() - 1);
		}
			// System.out.println("3: " + this.menuSelected);  // For debugging

	}
	public void moveDownSelection() // Move down selection of menuItems.
	{
		// Increase selected number by 1.
		this.menuSelected += 1;
		// Correct if selection number out of bounds.
		if (this.menuSelected >= (this.menuItems.size()))
		{
			this.menuSelected = (0);
		}
	}
	// Action
	public void initAction ()
	{
		menuItem currItem = menuItems.get(menuSelected); // Get selected menuItem.
		currItem.initAction(); // currMenu action

	}



}
