package lewiscrouch.ge.common.dimension;

import lewiscrouch.lib.geom.Point;

public class Player extends Entity
{
	private LifeStats lifeStats;
	private Inventory inventory;

	public Player(String name, Dimension dimension, Point dimensionCoords)
	{
		super(dimension, dimensionCoords);

		this.setName(name);

		this.lifeStats = new LifeStats(this, 100);
		this.inventory = new Inventory(10);
	}

	public LifeStats getLifeStats()
	{
		return this.lifeStats;
	}

	public void setLifeStats(LifeStats lifeStats)
	{
		this.lifeStats = lifeStats;
	}

	public Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public String toString()
	{
		String dimension = this.getDimension().getTitle();
		String player = this.getImageID() + ";" + this.getDimensionCoords().getX() + ";" + this.getDimensionCoords().getY();
		String lifeStats = this.lifeStats.toString();
		String inventory = this.inventory.toString();
		return dimension + " " + player + " " + lifeStats + " " + inventory;
	}
}
