package lewiscrouch.lib.geom;

public class Size
{
	private int width;
	private int height;

	public Size(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public boolean largerThan(Size that)
	{
		return ((this.width * this.height) > (that.width * that.height));
	}

	public boolean smallerThan(Size that)
	{
		return ((this.width * this.height) < (that.width * that.height));
	}

	public boolean equalTo(Size that)
	{
		return ((this.width * this.height) == (that.width * that.height));
	}

	public int getWidth()
	{
		return this.width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}
