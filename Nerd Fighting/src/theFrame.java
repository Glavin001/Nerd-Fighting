import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;


public class theFrame extends JFrame {
		
	// Method variables
	screen screen;
	
	// Constructor builds GUI.
	public theFrame (screen screen)
	{
		// Build content pane.
		Container contentPane = this.getContentPane();
		contentPane.setBackground(screen.frameColor);
		
		// JFrame settings.
		this.setTitle(screen.title());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIgnoreRepaint(true);
		
		// Add canvas to the conentPane
		contentPane.add(screen.theCanvas);
		
		// Pack
		this.pack();
		
	}
	
	
	
	
	
	
}
