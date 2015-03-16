package lewiscrouch.games.pro.common.dimension;

public class Entity
{
	private int dimensionX;
	private int dimensionY;

	public Entity(int dimensionX, int dimensionY)
	{
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
	}

	public double distance(int x, int y)
	{
		return Math.sqrt((this.dimensionX - x) * (this.dimensionX - x) + (this.dimensionY - y) * (this.dimensionY - y));
	}

	public int getDimensionX()
	{
		return this.dimensionX;
	}

	public void setDimensionX(int dimensionX)
	{
		this.dimensionX = dimensionX;
	}

	public int getDimensionY()
	{
		return this.dimensionY;
	}

	public void setDimensionY(int dimensionY)
	{
		this.dimensionY = dimensionY;
	}
}
