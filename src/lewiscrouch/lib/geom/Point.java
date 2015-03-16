package lewiscrouch.lib.geom;

public class Point
{
	private int x;
	private int y;

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public double dist(Point that)
	{
		double dx = this.x - that.x;
		double dy = this.y - that.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public int distRounded(Point that)
	{
		return (int) Math.ceil(this.dist(that));
	}

	public boolean equalTo(Point that)
	{
		return ((this.x == that.x && this.y == that.y));
	}

	public int getX()
	{
		return this.x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Point copy()
	{
		return new Point(this.x, this.y);
	}
}
