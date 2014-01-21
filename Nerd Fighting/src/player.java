import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;


public class player implements Serializable {

	// User statistics
	public long lastAccessed = System.currentTimeMillis(); // Last time player was accessed
	public long lifespan = 100; // lifespan = max difference between System.currentTimeMillis() and lastAccessed // Will delete player if not accessed within *lifespan* amount of milliseconds.


	// myMath
	myMath myMath = new myMath();

	private int layerNum = 3;

	private long playerID;
	public String userName = "";
	private long timeOfDeath = 0; // Time of death, for seeing how long since player has died.
	private int autoRespawnDelay = 1500; // time in milliseconds until player will respawn, after death.
	private long lastHit = 0; // Time of last hit
	private int healthRegenDelay = 2000; // in milliseconds, time delay until health regenerates
	private double healthRegenRate = (1 + (0.001)); // rate of regenerating health // (Health points)^(per milliseconds)

	// Player Stats
	public double maxHealth = 1000;
		public double health = maxHealth;
	public double maxShield = 0;
		public double shield = maxShield;
	public double damage = 0;

	// Weapon
	public weapon h1Weapon = null;
	public weapon h2Weapon = null;

	// Defaults    // default Angles; will attempt to return to normal when not moving, etc.
	private static double headAngleDefault = 0;
	private static double bodyAngleDefault = 0;
	private static double armAngleDefault = 45;
	private static double armJointAngleDefault = 22;
	private static double legAngleDefault = 45;
	private static double legJointAngleDefault = 22;
	private  double angleAbsorb = 1000.0; // If angle has a velocity than it will decrease incrementally by angleAbsorb rate.
	private  double angleMaxRate = 36 ; // If the angle is change more than angleMaxRate then some angular velocity is applied on other components of the Body.
	private  double angleMaxBend = 36 ; // Maximum angle that can occur without breakage.
	private  double angleStrength = 360; // If the angle has force being applied greater than angleStrength then the angle (joint) breaks and can exceed angleMaxBend.
	/* TEST */ public  double legStrength = 3000; // TEST


	// Head
	public double hAngle, hAngleV, hRadius;
  // Body
  public double x, y, vx, vy, bodyLength, bodyAngle, bodyAngleV, mass;
  double normalForceX = 0;   double normalForceY = 0;

  // Arm1
  public double a1Angle, a1AngleV, a1J1Angle, a1J1AngleV, a1L;
  // Hand 1
  public double a1hr;

  // Arm 2
  public double a2Angle, a2AngleV, a2J1Angle, a2J1AngleV, a2L;
  // Hand 2
  public double a2hr;

  // Leg 1
  public double l1Angle, l1AngleV, l1J1Angle, l1J1AngleV, l1L;
  //Foot 1
  public double l1fr;

  // Leg 2
  public double l2Angle, l2AngleV, l2J1Angle, l2J1AngleV, l2L;
  //Foot 2
  public double l2fr;

  private Color theColor;
  private ArrayList<acceleration> accelerations = new ArrayList<acceleration>();

  public player(double hAngle, double hAngleV, double hRadius,
		  double x, double y, double vx, double vy, double bodyLength, double bodyAngle, double bodyAngleV, double mass,
		  double a1Angle, double a1AngleV, double a1J1Angle, double  a1J1AngleV, double a1L, double a1hr,
		  double a2Angle, double a2AngleV, double a2J1Angle, double  a2J1AngleV, double a2L, double a2hr,
		  double l1Angle, double l1AngleV, double l1J1Angle, double l1J1AngleV, double l1L, double l1fr,
		  double l2Angle, double l2AngleV, double l2J1Angle, double l2J1AngleV, double l2L, double l2fr)
  {
	  // Head
	  this.hAngle = hAngle;
	  this.hAngleV = hAngleV;
	  this.hRadius = hRadius;
	  // Body
	  this.x = x;
	  this.y = y;
	  this.vx = vx;
	  this.vy = vy;
	  this.bodyLength = bodyLength;
	  this.bodyAngle = bodyAngle;
	  this.bodyAngleV = bodyAngleV;
	  this.mass = mass;
	  // Arm 1
	  this.a1Angle = a1Angle;
	  this.a1AngleV = a1AngleV;
	  this.a1J1Angle = a1J1Angle;
	  this.a1L = a1L;
	  this.a1hr = a1hr;
	  // Arm 2
	  this.a2Angle = a2Angle;
	  this.a2AngleV = a2AngleV;
	  this.a2J1Angle = a2J1Angle;
	  this.a2L = a2L;
	  this.a2hr = a2hr;
	  // Leg 1
	  this.l1Angle = l1Angle;
	  this.l1AngleV = l1AngleV;
	  this.l1J1Angle = l1J1Angle;
	  this.l1J1AngleV = l1J1AngleV;
	  this.l1L = l1L;
	  this.l1fr = l1fr;
	// Leg 2
	  this.l2Angle = l2Angle;
	  this.l2AngleV = l2AngleV;
	  this.l2J1Angle = l2J1Angle;
	  this.l2J1AngleV = l2J1AngleV;
	  this.l2L = l2L;
	  this.l2fr = l2fr;

	  // Tests
	  // this.bodyAngle = (Math.random() * 720) - 360;
	  // this.bodyAngleV = (Math.random() * 2) - 1;
	  // this.hAngleV = (Math.random() * 2) - 1;
	  // this.a1AngleV = (Math.random() * 2) - 1;
	  // this.a2AngleV = (Math.random() * 2) - 1;
	  // this.l1AngleV = (Math.random() * 2) - 1;
	  // this.l2AngleV = (Math.random() * 2) - 1;
	  // this.a1J1AngleV = (Math.random() * 2) - 1;
	  // this.a2J1AngleV = (Math.random() * 2) - 1;
	  // this.l1J1AngleV = (Math.random() * 2) - 1;
	  // this.l2J1AngleV = (Math.random() * 2) - 1;
	  // this.vx = (Math.random() * 100) - 50;
	  // this.vy = (Math.random() * 100) - 50;
  }

  public player(double x, double y, double hRadius, double bodyLength, double armLength, double handRadius, double footRadius, double legLength, double mass) // Default standing Player.
  {
	  /*
	  double hAngle = 0;
	  double a1Angle = 0;
	  double a1J1Angle = 0;
	  double a2Angle = 0;
	  double a2J1Angle = 0;
	  double l1Angle = 0;
	  double l1J1Angle = 0;
	  double l2Angle = 0;
	  double l2J1Angle = 0;
	  */

	  this( headAngleDefault,  0,  hRadius,
			   x,  y,  0,  0,  bodyLength, bodyAngleDefault,  0,  mass,
			   armAngleDefault,  0,  armJointAngleDefault,   0,  armLength,  handRadius,
			   -armAngleDefault,  0,  -armJointAngleDefault,   0,  armLength,  handRadius,
			   legAngleDefault,  0,  legJointAngleDefault,  0,  legLength,  footRadius,
			   -legAngleDefault,  0,  -legJointAngleDefault,  0,  legLength,  footRadius);
  }
  
  public void respawn(double x, double y, double hRadius, double bodyLength, double armLength, double handRadius, double footRadius, double legLength, double mass) // Default standing Player.
  {
	  /*
	  double hAngle = 0;
	  double a1Angle = 0;
	  double a1J1Angle = 0;
	  double a2Angle = 0;
	  double a2J1Angle = 0;
	  double l1Angle = 0;
	  double l1J1Angle = 0;
	  double l2Angle = 0;
	  double l2J1Angle = 0;
	  */

	  respawn( headAngleDefault,  0,  hRadius,
			   x,  y,  0,  0,  bodyLength, bodyAngleDefault,  0,  mass,
			   armAngleDefault,  0,  armJointAngleDefault,   0,  armLength,  handRadius,
			   -armAngleDefault,  0,  -armJointAngleDefault,   0,  armLength,  handRadius,
			   legAngleDefault,  0,  legJointAngleDefault,  0,  legLength,  footRadius,
			   -legAngleDefault,  0,  -legJointAngleDefault,  0,  legLength,  footRadius);
  }
  
  public void respawn(double hAngle, double hAngleV, double hRadius,
		  double x, double y, double vx, double vy, double bodyLength, double bodyAngle, double bodyAngleV, double mass,
		  double a1Angle, double a1AngleV, double a1J1Angle, double  a1J1AngleV, double a1L, double a1hr,
		  double a2Angle, double a2AngleV, double a2J1Angle, double  a2J1AngleV, double a2L, double a2hr,
		  double l1Angle, double l1AngleV, double l1J1Angle, double l1J1AngleV, double l1L, double l1fr,
		  double l2Angle, double l2AngleV, double l2J1Angle, double l2J1AngleV, double l2L, double l2fr)
  {
	  // Head
	  this.hAngle = hAngle;
	  this.hAngleV = hAngleV;
	  this.hRadius = hRadius;
	  // Body
	  this.x = x;
	  this.y = y;
	  this.vx = vx;
	  this.vy = vy;
	  this.bodyLength = bodyLength;
	  this.bodyAngle = bodyAngle;
	  this.bodyAngleV = bodyAngleV;
	  this.mass = mass;
	  // Arm 1
	  this.a1Angle = a1Angle;
	  this.a1AngleV = a1AngleV;
	  this.a1J1Angle = a1J1Angle;
	  this.a1L = a1L;
	  this.a1hr = a1hr;
	  // Arm 2
	  this.a2Angle = a2Angle;
	  this.a2AngleV = a2AngleV;
	  this.a2J1Angle = a2J1Angle;
	  this.a2L = a2L;
	  this.a2hr = a2hr;
	  // Leg 1
	  this.l1Angle = l1Angle;
	  this.l1AngleV = l1AngleV;
	  this.l1J1Angle = l1J1Angle;
	  this.l1J1AngleV = l1J1AngleV;
	  this.l1L = l1L;
	  this.l1fr = l1fr;
	// Leg 2
	  this.l2Angle = l2Angle;
	  this.l2AngleV = l2AngleV;
	  this.l2J1Angle = l2J1Angle;
	  this.l2J1AngleV = l2J1AngleV;
	  this.l2L = l2L;
	  this.l2fr = l2fr;

	  // Tests
	  // this.bodyAngle = (Math.random() * 720) - 360;
	  // this.bodyAngleV = (Math.random() * 2) - 1;
	  // this.hAngleV = (Math.random() * 2) - 1;
	  // this.a1AngleV = (Math.random() * 2) - 1;
	  // this.a2AngleV = (Math.random() * 2) - 1;
	  // this.l1AngleV = (Math.random() * 2) - 1;
	  // this.l2AngleV = (Math.random() * 2) - 1;
	  // this.a1J1AngleV = (Math.random() * 2) - 1;
	  // this.a2J1AngleV = (Math.random() * 2) - 1;
	  // this.l1J1AngleV = (Math.random() * 2) - 1;
	  // this.l2J1AngleV = (Math.random() * 2) - 1;
	  // this.vx = (Math.random() * 100) - 50;
	  // this.vy = (Math.random() * 100) - 50;
  }

  public void respawn()
  {
	// Initialize Player
		// Create player
		// Settings
		int x = 100;
		int y = 100;
		int scale = 3;
		// Player(double x, double y, double hRadius, double bodyLength, double armLength, double handRadius, double footRadius, double legLength, double mass)
		this.respawn((double) x, (double) y, (double) 5 * scale, (double) 40 * scale, (double) 12 * scale, (double) 2 * scale, (double) 2 * scale, (double) 12 * scale, (double) 100 * scale); // Create player
		// Resetting player stats
		damage = 0;
		health = maxHealth;
		legStrength = 3000;
  }
  
  public boolean isAlive()
	{
	  
	 
		if (health <= 0) 
		{
			//---------- Respawn player ---------//
			if (timeOfDeath == 0) 
			{
				timeOfDeath = System.currentTimeMillis(); // Set timeOfDeath, seeing as it has not been set already
			}
			if (System.currentTimeMillis() - timeOfDeath >= autoRespawnDelay) // If time since death is >= autoRespawnDelay, then repawn()
			{
					timeOfDeath = 0; // reset timeOfDeath
					respawn(); // Repsawn player.
			}
				return false; // Health <= 0, then it is dead; delete.
		}
		else
		{
			/*
			if (this.health < this.maxHealth)
				{
				// Is not dead; still alive
				if (lastHit != 0)
				{
					long deltaLastHit = (System.currentTimeMillis() - lastHit); // Millisecond difference
					deltaLastHit -= this.healthRegenDelay; // Subtract healthRegenDelay
					if (deltaLastHit > 0)
					{
						int increaseHealth = (int) (Math.pow(this.healthRegenRate, deltaLastHit)); // Increased health
						this.health += increaseHealth; // Increase the health by increased health amount
					}
				}
			}
			if (this.health > this.maxHealth) this.health = this.maxHealth; // Set to maxHealth.
			*/

		}		
		return true;

	}

  public synchronized void drawThis(screen screen)
  {

	  // Set Color
    	screen.graphics2d.setColor(getColor());

    	// offsets
    	int offsetX = screen.offsetX + screen.drawOffsetX;
    	int offsetY = screen.offsetY + screen.drawOffsetY;

    	
    	// Draw userName
    	// screen.graphics2d.setFont();
    	screen.graphics2d.drawString((String) this.userName, (int) (bNeckX() + offsetX), (int) (bNeckY() - this.bodyLength() + offsetY));

    	// init point list
    	int[] xList;
    	int[] yList;

    	 // draw Body
												    	 // if (p.x() < 1 || p.y() < 1 || p.bLowerX() < 1 || p.bLowerY() < 1)
												    		 // System.out.println(x() + "\n" +  y() + "\n" + bLowerX() + "\n" + bLowerY() + "\n" + "\n");
												    		// Core.consoleMsg = (x() + "\n" +  y() + "\n" + bLowerX() + "\n" + bLowerY() + "\n" + "\n");
												    	// Screen.graphics2d.drawLine( (int) x(), (int)  y(),  (int) bLowerX(), (int)  bLowerY());
    	    //	screen.graphics2d.setColor(Color.BLUE); // Testing
    	// xList = new int[] {(int) ((int) bNeckX() + offsetX),(int) ((int) x() + offsetX), (int) ((int) p.bLowerX() + offsetX)};
        // yList = new int[] {(int) ((int) bNeckY() + offsetY),(int) ((int) y() + offsetY), (int) ((int) p.bLowerY() + offsetY)};
    	   xList = new int[] {(int) ((int) bNeckX() + offsetX),(int) ((int) bLowerX() + offsetX)};
    	   yList = new int[] {(int) ((int) bNeckY() + offsetY),(int) ((int) bLowerY() + offsetY)};
     	 screen.graphics2d.drawPolyline(xList,yList, 2);


     	 	// screen.graphics2d.setColor(Color.GREEN); // Testing

    	// draw Head
    	 screen.graphics2d.fill(new Ellipse2D.Double(hx() + offsetX, hy() +offsetY, hRadius() * 2, hRadius() * 2));	// For future, make head display image as head, and collision as eclipse/circle.


    	   //	 screen.graphics2d.setColor(Color.GRAY); // Testing

    	// draw Arm 1
      xList = new int[] {(int) ((int) bTorsoX()+offsetX),(int) ((int) a1j1x()+offsetX),(int) ((int) a1hx()+offsetX)};
      yList = new int[] {(int) ((int) bTorsoY()+offsetY),(int) ((int) a1j1y()+offsetY),(int) ((int) a1hy()+offsetY)};
    	screen.graphics2d.drawPolyline(xList,yList, 3);

	   //	 screen.graphics2d.setColor(Color.BLACK); // Testing

    	// draw Hand 1
    	screen.graphics2d.fill(new Ellipse2D.Double(a1hx() - a1hr() + offsetX, a1hy() - a1hr() + offsetY,  a1hr() * 2, a1hr() * 2));


	   //	 screen.graphics2d.setColor(Color.GRAY); // Testing


    	// draw Arm 2
    	xList = new int[] {(int) ((int) bTorsoX()+offsetX),(int) ((int) a2j1x()+offsetX),(int) ((int) a2hx()+offsetX)};
      yList = new int[] {(int) ((int) bTorsoY()+offsetY),(int) ((int) a2j1y()+offsetY),(int) ((int) a2hy()+offsetY)};
    	screen.graphics2d.drawPolyline(xList,yList, 3);


	   //	 screen.graphics2d.setColor(Color.BLACK); // Testing


    	// draw Hand 2
    	screen.graphics2d.fill(new Ellipse2D.Double(a2hx() - a2hr()+offsetX, a2hy() - a2hr()+offsetY, a2hr() * 2, a2hr() * 2));


	   	// screen.graphics2d.setColor(Color.GRAY); // Testing


    	// draw Leg 1
    	xList = new int[] {(int) ((int) bLowerX()+offsetX),(int) ((int) l1j1x()+offsetX),(int) ((int) l1fx()+offsetX)};
      yList = new int[] {(int) ((int) bLowerY()+offsetY),(int) ((int) l1j1y()+offsetY),(int) ((int) l1fy()+offsetY)};
    	screen.graphics2d.drawPolyline(xList,yList, 3);


	   //	 screen.graphics2d.setColor(Color.BLACK); // Testing


    	// draw Foot 1
    	screen.graphics2d.fill(new Ellipse2D.Double(l1fx() - l1fr()+offsetX, l1fy() - l1fr()+offsetY, l1fr() * 2, l1fr() * 2));


	   //	 screen.graphics2d.setColor(Color.GRAY); // Testing


    	// draw Leg 2
    	xList = new int[] {(int) ((int) bLowerX()+offsetX),(int) ((int) l2j1x()+offsetX),(int) ((int) l2fx()+offsetX)};
      yList = new int[] {(int) ((int) bLowerY()+offsetY),(int) ((int) l2j1y()+offsetY),(int) ((int) l2fy()+offsetY)};
    	screen.graphics2d.drawPolyline(xList,yList, 3);


	   	// screen.graphics2d.setColor(Color.BLACK); // Testing


    	// draw Foot 2
    	screen.graphics2d.fill(new Ellipse2D.Double(l2fx() - l2fr()+offsetX, l2fy() - l2fr()+offsetY, l2fr() * 2, l2fr() * 2));


    	// Draw Weapon(s)
    	if (h1Weapon != null)
    	{
    		// Update weapon position
    		Point weaponPt = new Point();
    		weaponPt.x = ((int) a1hx());
    		weaponPt.y = ((int) a1hy());
    		h1Weapon.weaponPosition(weaponPt);
    		// Draw weapon
    		h1Weapon.drawThis(screen);
    	}
    	if (h2Weapon != null)
    	{
    		// Update weapon position
    		Point weaponPt = new Point();
    		weaponPt.x = ((int) a2hx());
    		weaponPt.y = ((int) a2hy());
    		h2Weapon.weaponPosition(weaponPt);
    		// Draw weapon
    		h2Weapon.drawThis(screen);
    	}

  }
  
  public synchronized Polygon playerPolygon()
  {
	  Polygon playerPolygon = new Polygon();
	  
	  playerPolygon.addPoint((int) bNeckX(), (int)bNeckY()); // Neck
	 // playerPolygon.addPoint((int) bTorsoX(), (int)bTorsoY()); // Torso
	  playerPolygon.addPoint((int) a1hx(), (int)a1hy()); // Hand 1
	  playerPolygon.addPoint((int) bLowerX()+1, (int)bLowerY()); // Lower body		// Offset by 1 so that the next point of Lower Body is not equal and thus ends the polygon.
	  playerPolygon.addPoint((int) l1fx(), (int)l1fy()); // Leg 1
	  playerPolygon.addPoint((int) l2fx(), (int)l2fy()); // Leg 1
	  playerPolygon.addPoint((int) bLowerX()-1, (int)bLowerY()); // Lower body
	  playerPolygon.addPoint((int) a2hx(), (int)a2hy()); // Hand 2
		 // playerPolygon.addPoint((int) bTorsoX(), (int)bTorsoY()); // Torso
	  playerPolygon.addPoint((int) bNeckX(), (int)bNeckY()); // Neck

	  
	  
	  
    	 // Body
	//  playerPolygon.addPoint((int) bNeckX(), (int)bNeckY());
	 // playerPolygon.addPoint((int) bLowerX(), (int)bLowerY());

    	// Head
    	// screen.graphics2d.fill(new Ellipse2D.Double(hx(), hy(), hRadius() * 2, hRadius() * 2));	// For future, make head display image as head, and collision as eclipse/circle.
    	  // playerPolygon.addPoint((int) hx(), (int)hy());


    	// Arm 1
	 // playerPolygon.addPoint((int) bTorsoX(), (int)bTorsoY());
	 // playerPolygon.addPoint((int) a1j1x(), (int)a1j1y());
	 // playerPolygon.addPoint((int) a1hx(), (int)a1hy());




    	// Hand 1
    //	screen.graphics2d.fill(new Ellipse2D.Double(a1hx() - a1hr() + offsetX, a1hy() - a1hr() + offsetY,  a1hr() * 2, a1hr() * 2));



    	// draw Arm 2
    	// xList = new int[] {(int) ((int) bTorsoX()+offsetX),(int) ((int) a2j1x()+offsetX),(int) ((int) a2hx()+offsetX)};
     // yList = new int[] {(int) ((int) bTorsoY()+offsetY),(int) ((int) a2j1y()+offsetY),(int) ((int) a2hy()+offsetY)};




    	// draw Hand 2
    	// screen.graphics2d.fill(new Ellipse2D.Double(a2hx() - a2hr()+offsetX, a2hy() - a2hr()+offsetY, a2hr() * 2, a2hr() * 2));




    	// draw Leg 1
    	// xList = new int[] {(int) ((int) bLowerX()+offsetX),(int) ((int) l1j1x()+offsetX),(int) ((int) l1fx()+offsetX)};
     // yList = new int[] {(int) ((int) bLowerY()+offsetY),(int) ((int) l1j1y()+offsetY),(int) ((int) l1fy()+offsetY)};




    	// draw Foot 1
    	// screen.graphics2d.fill(new Ellipse2D.Double(l1fx() - l1fr()+offsetX, l1fy() - l1fr()+offsetY, l1fr() * 2, l1fr() * 2));




    	// draw Leg 2
    	// xList = new int[] {(int) ((int) bLowerX()+offsetX),(int) ((int) l2j1x()+offsetX),(int) ((int) l2fx()+offsetX)};
      // yList = new int[] {(int) ((int) bLowerY()+offsetY),(int) ((int) l2j1y()+offsetY),(int) ((int) l2fy()+offsetY)};




    	// draw Foot 2
    	// screen.graphics2d.fill(new Ellipse2D.Double(l2fx() - l2fr()+offsetX, l2fy() - l2fr()+offsetY, l2fr() * 2, l2fr() * 2));

    	/*
    	if (h1Weapon != null)
    	{
    		// Update weapon position
    		Point weaponPt = new Point();
    		weaponPt.x = ((int) a1hx());
    		weaponPt.y = ((int) a1hy());
    		h1Weapon.weaponPosition(weaponPt);
    	}
    	if (h2Weapon != null)
    	{
    		// Update weapon position
    		Point weaponPt = new Point();
    		weaponPt.x = ((int) a2hx());
    		weaponPt.y = ((int) a2hy());
    		h2Weapon.weaponPosition(weaponPt);
    	}
 		*/

	   return (playerPolygon);
	   
  }

  public int getLayerNum()
  {
  	return (this.layerNum);
  }

  // ------------------------------------------------------------ //
  // Returning info on Player

  // Calculative/Derived
  public double hx()
  {
	   // return ( (hRadius() * (myMath.cos(90 - hAngle() ))) + x());
	   // return (hRadius() * (myMath.sin(hAngle())) + x() - (1 * hRadius()));
	  // return (hRadius() * (myMath.sin(hAngle())) + bNeckX() - (1 * hRadius()));
	//  return (bNeckX());

	   return (bNeckX() - (hRadius() * (myMath.sin(hAngle()))));

  }
  public double hy()
  {
	   // return ( (hRadius() * (myMath.cos(hAngle() ))) + y());
	   // return (hRadius() * (myMath.cos(hAngle())) + y() - (1 * hRadius()));
	   // return (hRadius() * (myMath.cos(hAngle())) + bNeckY() - (1 * hRadius()));
	//  return (bNeckY());
	    return (bNeckY() - (hRadius() * (myMath.cos(hAngle())) ) );

  }
  public double bNeckX()
  {
	  return ((-1 * (bodyLength/2) * myMath.sin(bodyAngle()) ) + x());
  }
  public double bNeckY()
  {
	  return (( -1 * (bodyLength/2) * myMath.cos(bodyAngle()) ) + y());
  }
  public double bTorsoX()
  {
	  // return ((bodyLength/3) * myMath.sin(bodyAngle()) + x());
	  return (( -1 * (bodyLength/3) * myMath.sin(bodyAngle()) ) + x());
  }
  public double bTorsoY()
  {
	  // return ((bodyLength/3) * myMath.cos(bodyAngle()) + y());
	  return (( -1 * (bodyLength/3) * myMath.cos(bodyAngle()) ) + y());

  }
  public double bCenterX()
  {
	  // return (bodyLength/2 * myMath.sin(bodyAngle()) + x());
	  return (x());
  }
  public double bCenterY()
  {
	  // return (bodyLength/2 * myMath.cos(bodyAngle()) + y());
	  return (y());

  }
  public double bLowerX()
  {
	  // return (bodyLength * myMath.sin(bodyAngle()) + x());
	  return (bodyLength/2 * myMath.sin(bodyAngle()) + x());

  }
  public double bLowerY()
  {
	  // return (bodyLength * myMath.cos(bodyAngle()) + y());
	  return (bodyLength/2 * myMath.cos(bodyAngle()) + y());
  }
  // Arm 1
  public double a1j1x()
  {
	  return (a1L * myMath.sin(a1Angle()) + bTorsoX());
  }
  public double a1hx()
  {
	  return (a1L * myMath.sin(a1J1Angle()) + a1j1x());
  }
  public double a1j1y()
  {
	  return (a1L * myMath.cos(a1Angle()) + bTorsoY());
  }
  public double a1hy()
  {
	  return (a1L * myMath.cos(a1J1Angle()) + a1j1y());
  }
  // Arm 2
  public double a2j1x()
  {
	  return (a2L * myMath.sin(a2Angle()) + bTorsoX());
  }
  public double a2hx()
  {
	  return (a2L * myMath.sin(a2J1Angle()) + a2j1x());
  }
  public double a2j1y()
  {
	  return (a2L * myMath.cos(a2Angle()) + bTorsoY());
  }
  public double a2hy()
  {
	  return (a2L * myMath.cos(a2J1Angle()) + a2j1y());
  }
  // Leg 1
  public double l1j1x()
  {
	  return (l1L * myMath.sin(l1Angle()) + bLowerX());
  }
  public double l1fx()
  {
	  return (l1L * myMath.sin(l1J1Angle()) + l1j1x());
  }
  public double l1j1y()
  {
	  return (l1L * myMath.cos(l1Angle()) + bLowerY());
  }
  public double l1fy()
  {
	  return (l1L * myMath.cos(l1J1Angle()) + l1j1y());
  }
  // Leg 2
  public double l2j1x()
  {
	  return (l2L * myMath.sin(l2Angle()) + bLowerX());
  }
  public double l2fx()
  {
	  return (l2L * myMath.sin(l2J1Angle()) + l2j1x());
  }
  public double l2j1y()
  {
	  return (l2L * myMath.cos(l2Angle()) + bLowerY());
  }
  public double l2fy()
  {
	  return (l2L * myMath.cos(l2J1Angle()) + l2j1y());
  }

  // Calculative Angles
  public double a1Angle () // Arm 1
  {
	  return (bodyAngle() + a1Angle);
  }
  public double a1J1Angle () // Arm 1 Joint 1
  {
	  return (a1Angle() - a1J1Angle);
  }
  public double l1Angle () // Leg 1
  {
	return (bodyAngle() + l1Angle);
  }
  public double l1J1Angle () // Leg 1 Joint 1
  {
	  return (l1Angle() - l1J1Angle);
  }
  public double a2Angle () // Arm 2
  {
	  return (bodyAngle() + a2Angle);
  }
  public double a2J1Angle () // Arm 2 Joint 1
  {
	  return (a2Angle() - a2J1Angle);
  }
  public double l2Angle () // Leg 2
  {
	return (bodyAngle() + l2Angle);
  }
  public double l2J1Angle () // Leg 2 Joint 1
  {
	  return (l2Angle() - l2J1Angle);
  }



  // Simple
  public double x() {     return this.x; }
  public double y()   {     return this.y; }
  public double getX() {     return (double) (   x()); }
  public double getY()   {     return (double) (   y()); }
  public double getCenterMassX() { return (double) (bCenterX()); }
  public double getCenterMassY() { return (double) (bCenterY()); }
  public double hRadius() { return this.hRadius;}
  public double hAngle() { return this.hAngle; }
  public double bodyAngle() {return this.bodyAngle;}
  public double mass()   { 	  return this.mass;   }
  public double vx()   {     return this.vx;   }
  public double vy()   {     return this.vy;   }
  public double a1L() { return this.a1L; }
  public double a2L() { return this.a2L; }
  public double a1hr() { return this.a1hr; }
  public double a2hr() { return this.a2hr; }
  public double l1fr() { return this.l1fr; }
  public double l2fr() { return this.l2fr; }
  public double l1L() { return this.l1L; }
  public double l2L() { return this.l2L; }
  public double bodyLength() { return this.bodyLength; }


  // --------------------------------------------------------------- //

  // Updates
  public synchronized void updateAngles(double timeFraction)
  {
	  // Updating angles.
	   hAngle += hAngleV * timeFraction;
	  bodyAngle += bodyAngleV  * timeFraction;
	  a1Angle += a1AngleV  * timeFraction;
	  a1J1Angle += a1J1AngleV  * timeFraction;
	  a2Angle += a2AngleV  * timeFraction;
	  a2J1Angle += a2J1AngleV  * timeFraction;
	  l1Angle += l1AngleV  * timeFraction;
	  l1J1Angle += l1J1AngleV  * timeFraction;
	  l2Angle += l2AngleV  * timeFraction;
	  l2J1Angle += l2J1AngleV  * timeFraction;


	  // Updating Angle Velocities
	  						// if ((Math.abs((bodyAngleV / (Math.abs(bodyAngleV)) * angleAbsorb) * timeFraction)) > (Math.abs(bodyAngleV)) || Double.isNaN(bodyAngleV)) {bodyAngleV = 0;}  else {bodyAngleV -= ( (bodyAngleV / (Math.abs(bodyAngleV)) * angleAbsorb) * timeFraction);}
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(bodyAngleV))) {bodyAngleV = 0;} else { bodyAngleV += (-1 * myMath.getNumSign(bodyAngleV)) * (angleAbsorb * timeFraction); }
	  		// System.out.println("bodyAngleV: " + bodyAngleV);		// For debugging
	  		// System.out.println("body Sign: " + myMath.getNumSign(bodyAngleV));   // For debugging
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(hAngleV))) {hAngleV = 0;} else { hAngleV += (-1 * myMath.getNumSign(hAngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(a1AngleV))) {a1AngleV = 0;} else { a1AngleV += (-1 * myMath.getNumSign(a1AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(a1J1AngleV))) {a1J1AngleV = 0;} else { a1J1AngleV += (-1 * myMath.getNumSign(a1J1AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(a2AngleV))) {a2AngleV = 0;} else { a2AngleV += (-1 * myMath.getNumSign(a2AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(a2J1AngleV))) {a2J1AngleV = 0;} else { a2J1AngleV += (-1 * myMath.getNumSign(a2J1AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(l1AngleV))) {l1AngleV = 0;} else { l1AngleV += (-1 * myMath.getNumSign(l1AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(l1J1AngleV))) {l1J1AngleV = 0;} else { l1J1AngleV += (-1 * myMath.getNumSign(l1J1AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(l2AngleV))) {l2AngleV = 0;} else { l2AngleV += (-1 * myMath.getNumSign(l2AngleV)) * (angleAbsorb * timeFraction); }
	  if ((Math.abs(angleAbsorb * timeFraction)) >= (Math.abs(l2J1AngleV))) {l2J1AngleV = 0;} else { l2J1AngleV += (-1 * myMath.getNumSign(l2J1AngleV)) * (angleAbsorb * timeFraction); }

  }

  public void setPlayerID (long playerID)
  {
	  this.playerID = playerID;
  }

  public long getPlayerID()
  {
	  return this.playerID;
  }

 public void damageHealth(double damage)
 {
	 this.lastHit = System.currentTimeMillis(); // Updating last time hit
	 
	 this.health -= damage;
	 if (this.health <= 0 && this.legStrength != 0)
	 {
		 this.legStrength = 0; // Makes it so player cannot stand under any force.
		if (this.normalForceY !=0) this.vy -= 10; // Pushing player up, so that they will fall, and as above states, will not be able to stand; thus appearing to fall and die.
	 }
 }

  public static Color randomColor()
  {
	  //Creating Random color
	int R = (int) (Math.random( )*256);
	int G = (int)(Math.random( )*256);
	int B= (int)(Math.random( )*256);
	Color randomColor = new Color(R, G, B);
	return randomColor;
}

  public Vector2D velVector()
  {
    return new Vector2D(this.vx(), this.vy());
  }

  public void normalForce(double fX, double fY)
  {
	  this.normalForceX = fX;
	  this.normalForceY = fY;

	  // System.out.println("normalForceX: " + this.normalForceX);
	  // System.out.println("normalForceY: " + this.normalForceY);
  }

  public void applyDrag(double dragX, double dragY)
  {
    this.vx = (dragX * this.vx);
    this.vy = (dragY * this.vy);
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
	double vx = this.vx() + (theAccel.ax() * timeFraction);
	double vy = this.vy() + (theAccel.ay() * timeFraction);
	this.updateVelocity(vx, vy);
	// Apply drag coefficient
	 this.applyDrag(1.0 - (timeFraction * this.calcDragX()), 1.0 - (timeFraction * this.calcDragY()) );
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
    this.vx = vx - (vx * this.normalForceX);
    this.vy = vy - (vy * this.normalForceY);
	 // System.out.println("vx:" + this.vx + "\t vy: " + this.vy);
  }

  public void updatePos(double newX, double newY)
  {
    this.x = newX;
    this.y = newY;

    if (h1Weapon != null)
  	{
  		Point newPosition = new Point();
  		newPosition.x = ((int) a1hx());
  		newPosition.y = ((int) a1hy());
  		h1Weapon.weaponPosition(newPosition);
  	}
	  if (h2Weapon != null)
  	{
  		Point newPosition = new Point();
  		newPosition.x = ((int) a2hx());
  		newPosition.y = ((int) a2hy());
  		h2Weapon.weaponPosition(newPosition);
  	}

  }

  public Color getColor()
  {
	return this.theColor;
  }

  public void setColor(Color newColor)
  {
	  this.theColor = newColor;
  }

  public double getHeadRadius() {
	  return this.hRadius;
  }


  public void x(double newX)
  {
    this.x = newX;
  }

  public void y(double newY)
  {
    this.y = newY;
  }

  public double calcDragY() {
  	// TODO Auto-generated method stub
  	return 0.0;
  }
   public double calcDragX() {
  	double drag = 0;
  	if (this.normalForceY != 0) drag = 0.02;
  	return drag;
  }

  // --------------------------------------------------------------------- //
  // Push points
  	// Note: "Temp" is a reminder to redesign the script to accommodate all of the other factors, not only that specific point.
  public void push_head(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("l1L: " + l1L());

		double beta = (height / (hRadius()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);

		// Temp
		  this.hAngle += (myMath.getNumSign(y) * theta);
	   this.hAngleV += (myMath.getNumSign(y) * theta) / 1000;

	}
  public void push_bCenterMass(double x, double y, double vx, double vy)
  {
	  // this.push_bTop(-x, -y, direction); // for older version
	  this.x += x;
	  this.y += y;
	  this.vx += vx;
	  	// System.out.println(this.vy()); // For debugging
	  this.vy += vy;
	  	// System.out.println(this.vy()); // For debugging


  }
  public void push_bTop(double x, double y, int direction) {



	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("l1L: " + l1L());

		double beta = (height / (bodyLength()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);

		double theta1 = this.bodyAngle;

		// New angles
		  this.bodyAngle -= (myMath.getNumSign(y) * direction * theta);
	   this.bodyAngleV -= (myMath.getNumSign(y) * direction * theta) / 1000;

	   // Move Body // Acts like body pivot around friction of point with ground.
	   /*
	   // double changeAngle = 1 * (myMath.getNumSign(y) * direction * theta);
	   double theta2 = this.bodyAngle;
		 double moveX = 0;  moveX = bodyLength() * (myMath.sin(theta2) - myMath.sin(theta1));
		  double moveY = 0; // moveY = bodyLength() * (myMath.cos(theta2) - myMath.cos(theta1));

		   	 // this.push_bCenterMass(moveX/2, moveY/2);
		   	 this.x += moveX/2;
		  	 this.y += moveY/2;
		 */


	// Slow Velocity
	 //  this.vx -= (x/2);
	  // this.vy -= (y/2);


		// this.vy = -1;

	    // this.x -=  (x);
	   // this.y -=  (y);

	}
  public void push_bLower(double x, double y, int direction) {

		  double height = (Math.abs(y));
			// System.out.println("height: " + height);
			// System.out.println("l1L: " + l1L());

			double beta = (height / (bodyLength()));
			// System.out.println("beta: " + beta);

			double theta = myMath.cos(beta);
			// System.out.println("theta: " + theta);

			double theta1 = this.bodyAngle;

			// Temp
			  this.bodyAngle += (myMath.getNumSign(y) * direction * theta);
		   this.bodyAngleV += (myMath.getNumSign(y) * direction * theta) / 1000;

		   // Move Body // Acts like body pivot around friction of point with ground.
		   /*


		   // double changeAngle = 1 * (myMath.getNumSign(y) * direction * theta);
		   double theta2 = this.bodyAngle;
			 double moveX = 0;  moveX = bodyLength() * (myMath.sin(theta2) - myMath.sin(theta1));
			  double moveY = 0; // moveY = bodyLength() * (myMath.cos(theta2) - myMath.cos(theta1));

		   	//   this.push_bCenterMass(-moveX/2, -moveY/2);
		  	 this.x += -moveX/2;
		  	 this.y += -moveY/2;
		  	*/


		// Slow Velocity
		 //   this.vx -= (x);
		 //   this.vy -= (y);


			// this.vy = -1;

		  //   this.x -=  (x);
		  //  this.y -=  (y);

		}
  public void push_l1j1(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("l1L: " + l1L());

		double beta = (height / (l1L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);

		// Decrease Velocity
			// updateVelocity(this.vx - x/1000, this.vy - y/1000);
			// updateVelocity(-this.vx,-this.vy);



		// Temp
		this.l1Angle += (myMath.getNumSign(y) * direction *  theta);
	   this.l1AngleV += (myMath.getNumSign(y) * direction * theta) / 1000;

	   this.bodyAngleV += (myMath.getNumSign(y) * direction * theta) / 1000;


	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_l2j1(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("l2L: " + l2L());

		double beta = (height / (l2L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);

		// Decrease Velocity
			// updateVelocity(this.vx - x/1000, this.vy - y/1000);
			// updateVelocity(-this.vx,-this.vy);

	  // Temp
		  this.l2Angle += (myMath.getNumSign(y) * direction * theta);
	   this.l2AngleV += (myMath.getNumSign(y) * direction *  theta) / 1000;

	   this.bodyAngleV += (myMath.getNumSign(y) * direction * theta) / 1000;


	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_l1f(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("l1L: " + l1L());

		double beta = (height / (l1L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);

		// Decrease Velocity
		// updateVelocity(this.vx - x/1000, this.vy - y/1000);
		// updateVelocity(-0,-1);

		if (Math.abs(theta) <= angleStrength && Math.abs(this.vy) <= legStrength)
		{
			  this.normalForce(this.normalForceX, (this.normalForceY + 0.5));
			 // this.normalForce(this.normalForceX, 1);


		}
		else {

			// this.normalForce(this.normalForceX, (this.normalForceY + 0.5));


		 	if (l1J1Angle <= 0)
			   {

			   		beta = theta - angleMaxRate;
					theta = 0;
					height = (l1L() * myMath.acos(beta));
					push_l1j1(x,height, -direction);

			   } else if (theta > angleMaxRate)
				{
					beta = theta - angleMaxRate;
					theta = angleMaxRate;
					height = (l1L() * myMath.acos(beta));
					push_l1j1(x,height, -direction);
			}
			// Temp
			  this.l1J1Angle += (myMath.getNumSign(y) * direction *  theta);
		   this.l1J1AngleV += (myMath.getNumSign(y) * direction * theta) / 1000;

		}



	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_l2f(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("l2L: " + l2L());

		double beta = (height / (l2L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);

		// Decrease Velocity
		// updateVelocity(this.vx - x/1000, this.vy - y/1000);
		// updateVelocity(-0,-1);

		if (Math.abs(theta) <= angleStrength && Math.abs(this.vy) <= legStrength)
		{
			 this.normalForce(this.normalForceX, (this.normalForceY + 0.5));
			// this.normalForce(this.normalForceX, 1);


		}
		else {

			// this.normalForce(this.normalForceX, (this.normalForceY + 0.5));


			if (l2J1Angle >= 0)
		   {

		   		beta = theta - angleMaxRate;
				theta = 0;
				height = (l2L() * myMath.acos(beta));
				push_l2j1(x,height, -direction);

		   } else if (theta > angleMaxRate)
			{
				beta = theta - angleMaxRate;
				theta = angleMaxRate;
				height = (l2L() * myMath.acos(beta));
				push_l2j1(x,height, -direction);
			}
			 // Temp
			  this.l2J1Angle += (myMath.getNumSign(y) * direction * theta);
		   this.l2J1AngleV += (myMath.getNumSign(y) * direction * theta) / 1000;
	   }



	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_a1j1(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("a1L: " + a1L());

		double beta = (height / (a1L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);
	  // Temp
		  this.a1Angle += (myMath.getNumSign(y) * direction * theta);
	   this.a1AngleV += (myMath.getNumSign(y) * direction * theta) / 1000;

	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_a2j1(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("a2L: " + a2L());

		double beta = (height / (a2L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);
	  // Temp
		  this.a2Angle += (myMath.getNumSign(y) * direction *  theta);
	   this.a2AngleV += (myMath.getNumSign(y) * direction * theta) / 1000;

	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_a1h(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("a1L: " + a1L());

		double beta = (height / (a1L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);
	  // Temp
		  this.a1J1Angle += (myMath.getNumSign(y) * direction * theta);
	   this.a1J1AngleV += (myMath.getNumSign(y) * direction *  theta) / 1000;

	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
  public void push_a2h(double x, double y, int direction) {

	  double height = (Math.abs(y));
		// System.out.println("height: " + height);
		// System.out.println("a2L: " + a2L());

		double beta = (height / (a2L()));
		// System.out.println("beta: " + beta);

		double theta = myMath.cos(beta);
		// System.out.println("theta: " + theta);
	  // Temp
		  this.a2J1Angle += (myMath.getNumSign(y) * direction * theta);
	   this.a2J1AngleV += (myMath.getNumSign(y) * direction * theta) / 1000;

	   /*
		// Slow Velocity
		   this.vx -= (x/2);
		   this.vy -= (y/2);
		  */

	}
// --------------------------------------------------------------------------------------------------------------- //
  // Weapons
  public void h1Weapon(weapon newWeapon)
  {
  		// System.out.println("Set: h1Weapon"); // For debugging
  	this.h1Weapon = newWeapon;
  }
   public void h2Weapon(weapon newWeapon)
  {
	   // System.out.println("Set: h2Weapon"); // For debugging
  	this.h2Weapon = newWeapon;
  }
  // Actions
  public void punch()
  {
	  // Arm 1


  }
  public void aimWeapons(Point aimPoint)
  {
	  // System.out.println("weaponAim");	  
	  
	double newSlope = 0;
	double x1 = 0;
	double y1 = 0;
	double x2 = 0;
	double y2 = 0;
	double newAngle = 0;
  	if (h1Weapon != null)
  	{

   		 x1 = (a1hx());
  		 y1 = (a1hy());
  		 x2 = aimPoint.x;
  		 y2 = aimPoint.y;

		// System.out.println("Aim: " + aimPoint);

    	// Get slope of pivotPoint to aimPoint
		// newSlope = myMath.getSlope(x1, y1, x2, y2);
		// double newAngle = myMath.angleOfSlope(newSlope);
		// System.out.println("newSlope: " + newSlope + "\t NewAngle: " + newAngle);

		// System.out.println("slope: " + (myMath.getSlope(0,0,7,11)) + "\t Angle: " + (myMath.angleOfSlope(11/7)) + "\t" + (Math.atan(0.2746)));

		 newAngle = myMath.calcTheta(x1, y1, x2, y2);
// System.out.println("newAngle: " + newAngle);
  		h1Weapon.updateTrajectory(newAngle);
  	}
  	
	if (h2Weapon != null)
  	{

   		 x1 = (a2hx());
  		 y1 = (a2hy());
  		 x2 = aimPoint.x;
  		 y2 = aimPoint.y;

		 newAngle = myMath.calcTheta(x1, y1, x2, y2);

  		h2Weapon.updateTrajectory(newAngle);
  	}

  }
  public void fire(world world)
  {
	 // System.out.println("Player: FIRE!");
	if (h1Weapon != null)
  	{
		// System.out.println("Fire: h1Weapon");
		h1Weapon.playerID = this.playerID;
  		h1Weapon.shoot(world);
  	}

	if (h2Weapon != null)
  	{
		// System.out.println("Fire: h2Weapon");
		h2Weapon.playerID = this.playerID;
  		h2Weapon.shoot(world);
  	}
  }



// ---------------------------------------------------------------------------------------------------------- //
//



}
