package lewiscrouch.ge.common.dimension;

public class Player extends Entity
{
	private String key;

	private LifeStats lifeStats;

	public Player(String key, int dimensionX, int dimensionY)
	{
		super(dimensionX, dimensionY);

		this.key = key;

		this.lifeStats = new LifeStats(100);
	}

	public LifeStats getLifeStats()
	{
		return this.lifeStats;
	}

	public void setLifeStats(LifeStats lifeStats)
	{
		this.lifeStats = lifeStats;
	}

	public String getKey()
	{
		return this.key;
	}
}
