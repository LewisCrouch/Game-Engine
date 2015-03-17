package lewiscrouch.ge.common.dimension;

import lewiscrouch.lib.geom.Point;

public class DimensionElement
{
	private Dimension dimension;
	private Point dimensionCoords;
	private String name;
	private boolean dead;

	public DimensionElement(Dimension dimension, Point dimensionCoords)
	{
		this.dimension = dimension;
		this.dimensionCoords = dimensionCoords;
		this.name = "Nameless ;_;";
		this.dead = false;
	}

	public Dimension getDimension()
	{
		return this.dimension;
	}

	public Point getDimensionCoords()
	{
		return this.dimensionCoords;
	}

	public void setDimensionCoords(int dimensionX, int dimensionY)
	{
		this.dimensionCoords = new Point(dimensionX, dimensionY);
	}

	public void setDimensionCoords(Point dimensionCoords)
	{
		this.dimensionCoords = dimensionCoords;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isDead()
	{
		return this.dead;
	}

	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
}
