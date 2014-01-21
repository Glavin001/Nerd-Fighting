import java.io.Serializable;

public class acceleration implements Serializable
{
	private double ax, ay;

	public acceleration(double ax, double ay)
	{
		this.ax = ax;
		this.ay = ay;
	}

	public double ax()
	{
		return this.ax;
	}

	public double ay()
	{
		return this.ay;
	}
}