import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;


public class projectile implements Serializable {

	// PlayerID
	public long playerID;

	// Details
	Color projColor = Color.GRAY;
	ArrayList<Point> tracer = new ArrayList<Point>();

	// Physics
	private ArrayList<acceleration> accelerations = new ArrayList<acceleration>();
	// double initX = 0;
	// double initY = 0;
	double x = 0;
	double y = 0;
	double totalV = 0;
	double slope = 0;
	double vx = 0;
	double vy = 0;
	double radius = 4;


	// Stats
	public long initialTime = 0; // in milliseconds
	public double lifespan = 2000; // in milliseconds // 1000 milliseconds = 1 second // 0 = forever
	public double health = 1; // If health == 0 then, projectile is dead and should be deleted. 
	public double damage = 2;
	public long lastTrace = 0; // in millisconds
	public boolean tracersEnabled = false;
	public double traceInterval = 2; // in milliseconds
	public int maxTracers = 10; 


	public projectile(long playerID)
	{
		this.playerID = playerID;
	}
	public projectile(projectile proj)
	{
		this.playerID = proj.playerID;
		this.x = proj.x;
		this.y = proj.y;
		this.totalV = proj.totalV;
		this.slope = proj.slope;
		this.vx = proj.vx;
		this.vy = proj.vy;
		this.radius = proj.radius;

	}

	public void playerID(long playerID)
	{
		this.playerID = playerID;
	}
	// ---------------------------------------------------------------------- //
	public synchronized void drawThis(screen screen)
	{
		// Draw Tracer
		if (tracersEnabled)
		{
			for (int t = 0; t < tracer.size(); t++)
			{
				// Set tracer Color
				Color traceColor = alphaColor(projColor,  (int) (255 * ((double) t/(double)tracer.size()) ));
				screen.graphics2d.setColor(traceColor);
				// Draw trace
				 screen.graphics2d.fillOval((int) tracer.get(t).x + screen.offsetX + screen.drawOffsetX, (int) tracer.get(t).y + screen.offsetY + screen.drawOffsetY, (int) radius, (int) radius);
			}
		}


		// Set projectille Color
		screen.graphics2d.setColor(projColor);
		screen.graphics2d.fillOval((int) this.x + screen.offsetX + screen.drawOffsetX, (int) this.y + screen.offsetY + screen.drawOffsetY, (int) radius, (int) radius);
	}

	public boolean isAlive()
	{
		
		if (health <= 0) return false; // Health <= 0, then it is dead; delete.
		if (lifespan == 0) return true; // It has lasted it's lifespan, not it is no more.
		if ((System.currentTimeMillis() - initialTime) <= lifespan)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// ----------------------------------------------------------------------- //
	public void fire(long playerID)
	{
		// Set initial (starting) time
		this.initialTime = System.currentTimeMillis(); // time of firing
		// Set up tracing
		lastTrace = System.currentTimeMillis();

		// Velocity
		double newVY = (totalV * (myMath.cos(slope)));
		double newVX = (totalV * (myMath.sin(slope)));
		this.newVelocities(newVX, newVY); 				// TODO: Finish			// NEXT TODO: Add means of transporting key and mouse controls thru socket. IDEA: Have the client always send current mousePoint and list of keyCodes (in numerical form) to server, which receives in that order!

		// System.out.println("FIRE!"); // For debugging

	}

	// ---------------------------- //
	// Set
	public void damage(double newDamage)
	{
		this.damage = newDamage;
	}
	public void newPoint(Point newPoint)
	{
		this.x = newPoint.x;
		this.y = newPoint.y;
	}
	public void newTotalVelocity(double totalVelocity)
	{
		this.totalV = totalVelocity;
	}
	public void newSlope(double newSlope)
	{
		this.slope = newSlope;
	}
	public void newVelocities(double newVX, double newVY)
	{
		this.vx = newVX;
		this.vy = newVY;
	}
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

	// ------------------------------ //
	// Get
	public double damage()
	{
		return (this.damage);
	}
	public double x()
	{
		return (this.x);
	}
	public double y()
	{
		return (this.y);
	}
	public double vx()
	{
		return (this.vx);
	}
	public double vy()
	{
		return (this.vy);
	}

	// ----------------------------------------------- //
	// Tracer
	public void addTracer()
	{
		// Create new Tracer Point
		Point newTracer = new Point();
		newTracer.x = (int) this.x;
		newTracer.y = (int) this.y;
		// Add Tracer Point
		addTracer(newTracer);
	}
	public void addTracer(Point newTracer)
	{
		
		// Adding Tracer
		if (tracer.size() >= this.maxTracers)
		{
			tracer.remove(0);
		}

		if ((System.currentTimeMillis() - lastTrace) >= traceInterval)
		{
			lastTrace = System.currentTimeMillis();
			tracer.add(newTracer); // If it has been a fair time since last tracer, add new tracer.
		}


	}

	// ------------------------------------------------------------------------- //
	// Physics

	  public void applyDrag(double drag)
	  {
	    this.vx = (drag * this.vx);
	    this.vy = (drag * this.vy);
	  }

	  public double calcDrag()
	  {
	  	// TODO Auto-generated method stub
	  	return 0;
	  }

	  public acceleration sumAccel()
	  {
	    double xAccel = 0, yAccel = 0;
	    for (int i = 0; i < this.accelerations.size(); i++) {
	      xAccel += this.accelerations.get(i).ax();
	      yAccel += this.accelerations.get(i).ay();
	    }
	    this.accelerations.clear();
	    return new acceleration(xAccel, yAccel);
	  }

	  public void updateVelocity(double timeFraction)
	  {
		// Get the sum of all accelerations acting on object.
		acceleration theAccel = this.sumAccel();
		// Apply the resulting change in velocity.
		double vx = this.vx + (theAccel.ax() * timeFraction);
		double vy = this.vy + (theAccel.ay() * timeFraction);
		this.updateVelocity(vx, vy);
		// Apply drag coefficient
		 this.applyDrag(1.0 - (timeFraction * this.calcDrag()));
	  }

	  public void addAccel(acceleration a)
	  {
	    this.accelerations.add(a);
	  }

	  public void updateVelocity(double vx, double vy)
	  {
		  // System.out.println("normalForceX: " + this.normalForceX);
		  // System.out.println("normalForceY: " + this.normalForceY);

		 //  System.out.println("vx:" + this.vx + "\t vy: " + this.vy);
	    this.vx = vx;
	    this.vy = vy;
		  // System.out.println("vx:" + this.vx + "\t vy: " + this.vy);
	  }

	  public void updatePos(double newX, double newY)
	  {
		  // Add Tracer
		 this.addTracer();

		 // Update to new position.
	    this.x = newX;
	    this.y = newY;

	  }

	// -- Alpha Color -- //
	public Color alphaColor(Color oldColor, int newAlpha)
	{
		// System.out.println("alpha:" + newAlpha);
		Color newColor = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), newAlpha);

		return newColor;
	}


}
