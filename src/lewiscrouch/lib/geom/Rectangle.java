package lewiscrouch.lib.geom;

public class Rectangle
{
	private Point point;
	private Size size;

	public Rectangle(Point point, Size size)
	{
		this.point = point;
		this.size = size;
	}

	public Rectangle(Point point, int width, int height)
	{
		this(point, new Size(width, height));
	}

	public Rectangle(int x, int y, Size size)
	{
		this(new Point(x, y), size);
	}

	public Rectangle(int x, int y, int width, int height)
	{
		this(new Point(x, y), new Size(width, height));
	}

	public boolean equalTo(Rectangle that)
	{
		return this.getPoint().equalTo(that.getPoint()) && this.getSize().equalTo(that.getSize());
	}

	public boolean outOfBounds(Rectangle bounds)
	{
		return this.outOfBoundsHorizontally(bounds) || this.outOfBoundsVertically(bounds);
	}

	public boolean outOfBoundsHorizontally(Rectangle bounds)
	{
		return this.getX() < bounds.getX() || this.getX() + this.getWidth() > bounds.getX() + bounds.getWidth();
	}

	public boolean outOfBoundsVertically(Rectangle bounds)
	{
		return this.getY() < bounds.getY() || this.getY() + this.getHeight() > bounds.getY() + bounds.getHeight();
	}

	public boolean intersects(Rectangle that)
	{
		return this.intersectsHorizontally(that) || this.intersectsVertically(that);
	}

	public boolean intersectsHorizontally(Rectangle that)
	{
		return this.getX() > that.getX() && this.getX() + this.getWidth() < that.getX() + that.getWidth();
	}

	public boolean intersectsVertically(Rectangle that)
	{
		return this.getY() > that.getY() && this.getY() + this.getHeight() < that.getY() + that.getHeight();
	}

	public double distFromCenter(Rectangle that)
	{
		Point a = new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
		Point b = new Point(that.getX() + that.getWidth() / 2, that.getY() + that.getHeight() / 2);
		return a.dist(b);
	}

	public int distFromCenterRounded(Rectangle that)
	{
		Point a = new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
		Point b = new Point(that.getX() + that.getWidth() / 2, that.getY() + that.getHeight() / 2);
		return a.distRounded(b);
	}

	public int getX()
	{
		return this.point.getX();
	}

	public void setX(int x)
	{
		this.point.setX(x);
	}

	public int getY()
	{
		return this.point.getY();
	}

	public void setY(int y)
	{
		this.point.setY(y);
	}

	public Point getPoint()
	{
		return this.point;
	}

	public void setPoint(Point point)
	{
		this.point = point;
	}

	public void setPoint(int x, int y)
	{
		this.point = new Point(x, y);
	}

	public int getWidth()
	{
		return this.size.getWidth();
	}

	public void setWidth(int width)
	{
		this.size.setWidth(width);
	}

	public int getHeight()
	{
		return this.size.getHeight();
	}

	public void setHeight(int height)
	{
		this.setHeight(height);
	}

	public Size getSize()
	{
		return this.size;
	}

	public void setSize(Size size)
	{
		this.size = size;
	}

	public void setSize(int width, int height)
	{
		this.size = new Size(width, height);
	}

	public Rectangle copy()
	{
		return new Rectangle(this.point.getX(), this.point.getY(), this.size.getWidth(), this.size.getHeight());
	}
}
