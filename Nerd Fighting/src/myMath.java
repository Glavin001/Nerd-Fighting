import java.io.Serializable;
import java.awt.Point;


public class myMath implements Serializable {

	static final double degToRad = Math.PI/180;
	static final double radToDeg = 180/Math.PI;


	public myMath ()
	{

	}

	public double calcTheta(double x1, double y1, double x2, double y2)
	{
		double theta = 0;
		double delta_x = x2 - x1;
		double delta_y = y2 - y1;
		double theta_radians = Math.atan2(delta_x, delta_y);
		theta = Math.toDegrees(theta_radians);

		/*
		double theta;
		if ( x2 - x1 == 0 )
			if ( y2 > y1 )
				theta = 0;
			else
				theta =  Math.PI;
			else
			{
			theta = Math.atan( (y2 - y1) / (x2 - x1) );
				if ( x2 > x1 )
				theta = (Math.PI ) / 2.0f - theta;
				else
				theta = (Math.PI ) * 1.5f - theta;
			};
		return theta;
		*/

		return theta;

	}

	public double angleOfSlope(double slope)
	{
		double angle = 0;
		double angle1 = ((double) atan((double)slope));
		double angle2 = ((double) tan((double)slope));
		// System.out.println("slope: " + slope + "\t aTan: " + angle1 + "\t Tan:" + angle2);

		angle = angle1;
		return (angle);
	}

	public double getSlope(Point pt1, Point pt2)
	{
		double slope = 0;
		slope = (double) (((double) pt2.y - (double) pt1.y)/((double) pt2.x - (double) pt1.x));
		return slope;
	}

	public double getSlope(double x1, double y1, double x2, double y2)
	{
		double slope = 0;
		slope = ((y2 - y1)/(x2 - x1));
		return slope;
	}

	public int getNumSign(double theNum)
	{
		return (int) (theNum / (Math.abs(theNum)));
	}

	public  double radToDegrees(double rad)
	{
		return (double) (rad * radToDeg);
	}

	public static  double degToRad(double deg)
	{
		return (double) (deg * degToRad);
	}

	public static double getWithin(double degrees, double within)
	{
		while (degrees > within) {degrees -= within;}
		return degrees;
	}

	public static double cos(double degrees)
	{
		return (Math.cos((degToRad(degrees))));
	}

	public static double sin(double degrees)
	{
		return (Math.sin((degToRad(degrees))));
	}

	public static double tan(double degrees)
	{
		return (Math.tan((degToRad(degrees))));
	}

	public static  double acos(double degrees)
	{
		return (Math.acos((degToRad(degrees))));
	}

	public  double asin(double degrees)
	{
		return (Math.asin((degToRad(degrees))));	}

	public  double atan(double degrees)
	{
	//	System.out.println("atan: Degrees=" + degrees);
		double atan = 0;
		double atan1 =  Math.toDegrees(Math.atan((Math.toDegrees(degrees))));
		double atan2 = Math.toRadians(Math.atan(((degrees))));

	//	System.out.println("atan1: " + atan1 + "\t atan2: " + atan2);

		atan = atan1;
		return ((atan));
	}

}
