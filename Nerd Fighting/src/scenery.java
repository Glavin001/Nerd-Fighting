import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;


public class scenery implements Serializable 
{

	// Working variables
	
	
	public scenery ()
	{
		
	}
	
	
	 public synchronized void drawThis(screen screen)
	  {

		  // Set Color
	    	screen.graphics2d.setColor(Color.black);

	    	// offsets
	    	int offsetX = screen.offsetX;
	    	int offsetY = screen.offsetY;


	  }
	
}
