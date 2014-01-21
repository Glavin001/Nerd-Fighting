import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class theCanvas extends Canvas implements ActionListener, MouseMotionListener, MouseListener, KeyListener 
{

	// Method variables.
	screen screen;
	core core;
	// controller controller;
	
	public theCanvas (screen screen, controller controller)
	{
		
		// Set method variables
		this.screen = screen; // screen
		// this.controller = controller; // controller
		this.core = screen.core;
		
		// Add Listeners
		addMouseListener(this);
		addMouseListener(this);
    	addMouseMotionListener(this);
    	addKeyListener(this);
    	
    	this.setFocusTraversalKeysEnabled(false); // Enabling Traversal Keys.
    	this.setIgnoreRepaint(true); // Ignoring repaint.
    	this.setSize(screen.windowWidth, screen.windowHeight); // Setting canvas size.
	}
	
	
	// Update Method variables
	public void updateMethod(screen newScreen)  	{  		this.screen = newScreen; 	}
	// public void updateMethod(controller newController) 	{  		this.controller = newController; 	}
	
	// Implemented Methods - For input.
	public void keyPressed(KeyEvent keyEvent) {
		core.controller.keyPressed(keyEvent);
	}
	public void keyReleased(KeyEvent keyEvent) {
		core.controller.keyReleased(keyEvent);
	}
	public void keyTyped(KeyEvent keyEvent) {
		core.controller.keyTyped(keyEvent);
	}
	public void mouseClicked(MouseEvent mouseEvent) {
		core.controller.mouseClicked(mouseEvent);
	}
	public void mouseEntered(MouseEvent mouseEvent) {
		core.controller.mouseEntered(mouseEvent);
	}
	public void mouseExited(MouseEvent mouseEvent) {
		core.controller.mouseExited(mouseEvent);
	}
	public void mousePressed(MouseEvent mouseEvent) {
		core.controller.mousePressed(mouseEvent);
	}
	public void mouseReleased(MouseEvent mouseEvent) {
		core.controller.mouseReleased(mouseEvent);
	}
	public void mouseDragged(MouseEvent mouseEvent) {
		core.controller.mouseDragged(mouseEvent);
	}
	public void mouseMoved(MouseEvent mouseEvent) {
		try { core.controller.mouseMoved(mouseEvent); } catch (Exception e ) {}
	}
	public void actionPerformed(ActionEvent actionEvent) {
		core.controller.actionPerformed(actionEvent);
	}
	
}
