import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;


public class circle implements Serializable {

	double radius;
	double x, y, vx, vy;
	Color theColor = Color.BLACK;
	boolean isOutline = false;
	
	public circle(double radius, double x, double y)
	{
		this.radius = radius;
		this.x = x;
		this.y = y;
		
		// test
		// this.vx = (Math.random() * 10);
		// this.vy = (Math.random() * 10);
	}
	
	// ----------------------------------------------------------- //
	// Get
	public Color theColor()
	{
		return (this.theColor);
	}
	// ----------------------------------------------------------- //
	// Set
	public void theColor(Color newColor)
	{
		this.theColor = newColor;
	}
	
	// ----------------------------------------------------------- //
	public void drawThis(screen screen)
	{
		// Draw circle on screen
		screen.graphics2d.setColor(theColor);
		if (isOutline) screen.graphics2d.drawOval((int) x + screen.offsetX + screen.drawOffsetX, (int) y + screen.offsetY + screen.drawOffsetY, (int) radius, (int) radius);
		else if (!isOutline) screen.graphics2d.fillOval((int) x + screen.offsetX + screen.drawOffsetX, (int) y + screen.offsetY + screen.drawOffsetY, (int) radius, (int) radius);

	}

	// ------------------------------------------------------------ //
	// Set
	public void x(double newX)
	{
		this.x = newX;
	}
	public void y(double newY)
	{
		this.y = newY;
	}
	public void vx(double newVX)
	{
		this.vx = newVX;
	}
	public void vy(double newVY)
	{
		this.vy = newVY;
	}
	
	
	
}
