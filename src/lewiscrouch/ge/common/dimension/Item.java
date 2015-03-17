package lewiscrouch.ge.common.dimension;

public class Item
{
	private String name;
	private int damage;
	private boolean damageable;

	public Item()
	{
		this.name = "Nameless ;_;";
		this.damage = 100;
		this.damageable = false;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getDamage()
	{
		return this.damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	public boolean isDamageable()
	{
		return this.damageable;
	}

	public void setDamageable(boolean damageable)
	{
		this.damageable = damageable;
	}
}
