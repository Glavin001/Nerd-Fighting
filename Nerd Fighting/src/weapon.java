import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;


public class weapon implements Serializable {

	// myMath
	myMath myMath = new myMath();

	// PlayerID
	long playerID;

	// Details
	projectile proj1 = null;
	projectile proj2 = null;
	Color weapColor;

	// Working Variables/Physics
	double x = 0; // x position of handle, for player hand.
	double y = 0; // y position of handle, for player hand.
	double slope = 0;
	long lastFire = 0; // Last time fired
	double fireInterval1 = 30; // in milliseconds
	double fireInterval2 = 30; // in milliseconds

	public weapon(long playerID)
	{
		this.playerID = playerID;
	}

	public void playerID(long playerID)
	{
		this.playerID = playerID;

		try // Reset playerID
		{
			if (proj1 != null) proj1.playerID(this.playerID);
		} catch (Exception e) { }

		try // Reset playerID
		{
			if (proj2 != null) proj2.playerID(this.playerID);
		} catch (Exception e) { }

	}

	// ---------------------------------------------------------------------- //
	// DrawThis
	public void drawThis(screen screen)
	{


		// Set Weapon Color
		screen.graphics2d.setColor(weapColor);
		screen.graphics2d.fillPolygon(polyGun());
	}

	// -------------------------- //
	// Shooting
	public void shoot(world world)
	{
		if (true)
		{
			this.primaryFire(world);
		}
		else
		{
			this.secondaryFire(world);
		}
	}
	public void primaryFire(world world)
	{
		if ((proj1 != null) && (lastFire == 0 || (System.currentTimeMillis() - lastFire) >= fireInterval1 ))
		{
			// Create new projectile to fire
			projectile proj = new projectile(proj1);

			// Adjust proj
			proj.newPoint(fireOrigin());

			// Fire proj
			proj.fire(this.playerID);

			// Add projectile to world, thus firing.
			world.addProjectile(proj);

			// Update lastfire
			lastFire = System.currentTimeMillis();

		}
	}
	public void secondaryFire(world world )
	{
		if ((proj2 != null) && (lastFire == 0 || (System.currentTimeMillis() - lastFire) >= fireInterval2) )
		{
			// Create new projectile to fire
			projectile proj =  new projectile(proj2);

			// Adjust proj
			proj.newPoint(fireOrigin());

			// Fire proj
			proj.fire(this.playerID);

			// Add projectile to world, thus firing.
			world.addProjectile(proj);


			// Update lastfire
			lastFire = System.currentTimeMillis();
		}
	}

	// -------------------------------------------------- //
	public void setProj1(projectile newProj)
	{
		newProj.playerID(this.playerID);
		this.proj1 = newProj;
	}
	public void setProj2(projectile newProj)
	{
		newProj.playerID(this.playerID);
		this.proj2 = newProj;
	}
	public Polygon polyGun()
	{
		Polygon polyGun = new Polygon();

		return polyGun;
	}
	public Point fireOrigin()
	{
		Point projOrigin = new Point();
		projOrigin.x = (int) this.x;
		projOrigin.y = (int) this.y;

		return (projOrigin);
	}
	public void weaponPosition(Point newPosition)
	{
		// newPosition = Point of hand = Point of Weapon's handle
		this.x = newPosition.x;
		this.y = newPosition.y;

		// Update projectile spawn points based on slope and weapon configuration.
		// this.updateTrajectory();

	}

	public void updateTrajectory(double slope)
	{
		this.slope = slope;
		// Update slope of proj1 and proj2
		if (proj1 != null)
		{
				proj1.newSlope(this.slope);
		}
		if (proj2 != null)
		{
			proj2.newSlope(this.slope);
		}
	}

}
