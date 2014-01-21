import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class screen {

	// Method variables
	public core core;
	 public theFrame theFrame;
	 public theCanvas theCanvas;

	// Working variables.
	public String title = "Application"; // Application Frame title.
	private boolean isFullScreen = false;	// is Full-Screen?
	// For Graphics & Buffering.
	public  BufferStrategy bufferStrategy;
	public  GraphicsEnvironment graphicsEnv;
	public  GraphicsDevice graphicsDevice;
	public  GraphicsConfiguration graphicsConfig;
	public  BufferedImage buffer;
	public  Graphics graphics;
	public  Graphics2D graphics2d;
	public  AffineTransform affineTransf;
		public Color backgroundColor = Color.WHITE; // background Color variable: Used for clearing buffer.
			public Color backgroundColorBackup = backgroundColor; // backup of backgroundColor
		public Color frameColor = Color.BLACK; // background Color variable: Used for background color of theFrame.
	// Screen dimensions
		public final int MINWIDTH = 480;
		public final int MINHEIGHT = 480;
	public int windowWidth = 640;
	public int windowHeight = 480;
		public int windowWidthBackup = windowWidth; // Backup of windowWidth
		public int windowHeightBackup = windowHeight; // Backup of windowHeight
	public int offsetX = 1; // Moves top-Left corner of buffered (visible) screen correspondingly.
	public int offsetY = 1; // Moves top-Left corner of buffered (visible) screen correspondingly.
	public int offsetWidth = 1; // Offsets the width of the buffered (visible) screen correspondingly.
	public int offsetHeight = 25; // Offsets the width of the buffered (visible) screen correspondingly.
	// Drawing offsets. Used for changing the center reference so that a desired object can be drawn at a desired point on the screen.
	public boolean autoFocus = true; // If true, screen & gameLoop focuses on player(playerID)
	public int drawOffsetX = 0; // Offset of all objects drawn on screen.
	public int drawOffsetY = 0; // Offset of all objects drawn on screen.
	// Screen modes
	private int screenMode = 0; // screen mode.
	private final int DEFAULTMODE = 0; // Value of default screen mode.
	private final int CALIBRATINGMODE = 1; // Value of Calibrating screen mode.
		public int calibrationMode = 0; // calibration mode.


	// screen method.
	public screen(core core)
	{
		// Set core
		this.core = core;
	}

	// init screen.
	public void init()
	{
		// Building Canvas
		this.theCanvas = new theCanvas(this, core.controller);

		// Building Frame
		this.theFrame = new theFrame(this);
		// Setting up theFrame
		this.theFrame.setLocationRelativeTo(null); // Centers theFrame on screen.
		this.theFrame.setVisible(true); // Makes theFrame visible

		// Setting up BufferStrategy
		this.createBufferStrategy();


	}

	// -------------------------------------------------------- //
	// Create Buffer Strategy
	public void createBufferStrategy() {
			// Creating buffer strategy for double buffering.
			this.theCanvas.createBufferStrategy(2); // 2 makes Double buffer strategy.
			this.bufferStrategy = this.theCanvas.getBufferStrategy();

			// Get graphics configuration.
			this.graphicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			this.graphicsDevice = this.graphicsEnv.getDefaultScreenDevice();
			this.graphicsConfig = this.graphicsDevice.getDefaultConfiguration();
			// Create off-screen drawing surface
			this.buffer = this.graphicsConfig.createCompatibleImage(this.getDrawWidth(), this.getDrawHeight());
			// Objects needed for rendering...
			this.graphics = null;
			this.graphics2d = null;

	}
	public void refreshBackBuffer()
	{
		int theWidth = this.getDrawWidth();
        int theHeight =this.getDrawHeight();
        // Create new compatible off-screen drawing surface: buffer
		this.buffer = this.graphicsConfig.createCompatibleImage(this.windowWidth, this.windowHeight);
		// Clears back buffer by over writing with rectangle with background color.
		this.graphics2d = this.buffer.createGraphics();

        this.graphics2d.setColor(this.frameColor); // Sets Color to background Color
        this.graphics2d.fillRect(0,0,this.windowWidth, this.windowHeight); // draws a Rectangle with the Color backgroundColor, replacing anything underneath.

        this.graphics2d.setColor(this.backgroundColor); // Sets Color to background Color
       // System.out.println(theWidth);
       // System.out.println(theHeight);
        this.graphics2d.fillRect(offsetX, offsetY, theWidth , theHeight); // draws a Rectangle with the Color backgroundColor, replacing anything underneath.
	}


	// ------------------------------------------------ //
	// Set methods
	public void isFullScreen(boolean newIsFullScreen)
	{
		this.isFullScreen = newIsFullScreen;
	}
	public void backgroundColor(Color newColor)
	{
		Color theColor = (Color) newColor;
		this.backgroundColor = theColor;
	}
	public void title(String newTitle)
	{
		this.title = newTitle;
		// Update title on theFrame
		this.theFrame.setTitle(this.title);
	}
	public void backgroundColorBackup(Color newColor)
	{
		Color theColor = (Color) newColor;
		this.backgroundColorBackup = theColor;
	}


	// ------------------------------------------------ //
	// Get Methods
	public boolean isFullScreen()
	{
		return (this.isFullScreen);
	}
	public Color backgroundColor()
	{
		return ( (Color) this.backgroundColor);
	}
	public String title()
	{
		return (this.title);
	}
	public int getDrawWidth()
	{
		return (this.windowWidth-(this.offsetX + this.offsetWidth));
	}
	public  int getDrawHeight()
	{
		return (this.windowHeight-(this.offsetY + this.offsetHeight));
	}

	// -------------------------------------------------- //
	// update Method variables.
	public void updateMethod(core core)
	{
		this.core = core;
	}
	public void updateMethod(controller controller)
	{
		// this.controller = controller;
	}

	// ----------------------------------------------------------------------------------------------------------------- //
	// input handler
	public void keyPressed(KeyEvent keyEvent)
	{

		if (screenMode == DEFAULTMODE)
		{

		}
		else if (screenMode == CALIBRATINGMODE)
		{
			calibrateKeyPressed(keyEvent);
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

	// ------------------------------------------------------------------------------------------------------------------ //

	// toggleFullScreen
	public void toggleFullScreen()
	{
		this.isFullScreen = !this.isFullScreen; // Toggle isFullScreen.
		if (!isFullScreen) // If isFullScreen is currently not true (false), then it must have been true before toggled; or visa versa.
		{
		// Is currently Full-Screen.
			
			// Get window theFrame of Full-Screen
			Window theWindow = this.graphicsDevice.getFullScreenWindow();
			this.theFrame = (theFrame) ((JFrame) theWindow);
			
			// Restore screen from Full-Screen
			graphicsDevice.setFullScreenWindow(null);
				// Restore sizes from backup
				this.windowWidth = this.windowWidthBackup; 
				this.windowHeight = this.windowHeightBackup;
				
			
			// Settings for theFrame
			this.theFrame.setResizable(true);
			
		}
		else
		{
		// Is not currently Full-Screen.
			
			// Settings for theFrame
			/* Does not work theFrame. Error: The frame is displayable.*/ // this.theFrame.setUndecorated(true);
			/* NOT REQUIRED */ // this.theFrame.setResizable(false);
				// Change size
					// Backup Sizes
					this.windowWidthBackup = windowWidth;
					this.windowHeightBackup = windowHeight;
				// this.windowWidth = (windowWidth);
				// this.windowHeight = (windowWidth);
			
			// Set full-Screen window to theFrame
					System.out.println("setFullScreenWindow");
			this.graphicsDevice.setFullScreenWindow(this.theFrame);
			
			
		}
		
	}
	
	// UpdateWindowSize
	public void updateWindowSize()
	{
		// update width
		this.windowWidth = (theFrame.getSize().width);
			// System.out.println("windowWidth: " + windowWidth); // For debugging
		// update height
		this.windowHeight = (theFrame.getSize().height);
			// System.out.println("windowHeight: " + windowHeight); // For debugging

		// Correct size, if required
		if (this.windowWidth < MINWIDTH) // Correct Width
		{
			// Reset size to Minimum
			theFrame.setSize(MINWIDTH, windowHeight);
			// update width
			this.windowWidth = (theFrame.getSize().width);
		}
		if (this.windowHeight < MINHEIGHT) // Correct Height
		{
			// Reset size to Minimum
			theFrame.setSize(windowWidth, MINHEIGHT);
			// update height
			this.windowHeight = (theFrame.getSize().height);
		}

	}

	// ------------------------------------------------------ //
	// Calibrate the screen.
	public void calibrateScreen()
	{
		screenMode = CALIBRATINGMODE;
		core.controller.updateReceiver(this);

	}

	public void calibrateKeyPressed(KeyEvent keyEvent)
	{
		int keyCode = keyEvent.getKeyCode();

		if (calibrationMode == 0)
		{
			
			// Change background color
			this.backgroundColorBackup(this.backgroundColor); // Backup
			this.backgroundColor(Color.yellow);
			
			
			/*
			switch (keyCode)
			{
				case (KeyEvent.VK_ENTER):
				{
			*/		calibrationMode++;
			//		break;
			/*	}
				case (KeyEvent.VK_ESCAPE):
				{
					calibrationMode = 3;
						break;
				}
			}
			*/
		}
		if (calibrationMode == 1)
		{

			


			switch (keyCode)
			{
				case (KeyEvent.VK_ENTER):
				{
					calibrationMode++;
						break;
				}
				case (KeyEvent.VK_ESCAPE):
				{
					calibrationMode = 3;
						break;
				}
				case(KeyEvent.VK_UP):
				{
					offsetY--;
						break;
				}
				case(KeyEvent.VK_DOWN):
				{
					offsetY++;
						break;
				}
				case(KeyEvent.VK_LEFT):
				{
					offsetX--;
						break;
				}
				case(KeyEvent.VK_RIGHT):
				{
					offsetX++;
						break;
				}
			}


		}
		else if (calibrationMode == 2)
		{

			// Change background color
			this.backgroundColor = Color.yellow;

			switch (keyCode)
			{
				case (KeyEvent.VK_ENTER):
				{
					calibrationMode++;
					break;
				}
				case (KeyEvent.VK_ESCAPE):
				{
					calibrationMode = 3;
					break;
				}
				case(KeyEvent.VK_UP):
				{
					offsetHeight++;
						break;
				}
				case(KeyEvent.VK_DOWN):
				{
					offsetHeight--;
					break;
				}
				case(KeyEvent.VK_LEFT):
				{
					offsetWidth++;
						break;
				}
				case(KeyEvent.VK_RIGHT):
				{
					offsetWidth--;
						break;
				}
			}
		}

		if (calibrationMode == 3)
		{
			// Change background color
			this.backgroundColor(this.backgroundColorBackup);

			// Calibration completed.
			screenMode = DEFAULTMODE;

			// Reset calibrator
			calibrationMode = 0;

			System.out.println("update receiver: menu");
			// Set controller Receiver to menu.
			core.controller.updateReceiver(core.menu);
		}


	}



}
