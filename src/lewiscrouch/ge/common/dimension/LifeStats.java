package lewiscrouch.ge.common.dimension;

public class LifeStats
{
	private int health;
	private int maxHealth;
	private boolean dead;

	public LifeStats(int health)
	{
		this.health = health;
		this.maxHealth = health;
		this.dead = false;
	}

	public void heal(int amount)
	{
		this.health += amount;
		if(this.health > this.maxHealth) this.health = maxHealth;
	}

	public void heal()
	{
		this.health = this.maxHealth;
	}

	public void hurt(int amount)
	{
		this.health -= amount;
	}

	public void kill()
	{
		this.health = 0;
	}

	public int getHealth()
	{
		return this.health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public int getMaxHealth()
	{
		return this.maxHealth;
	}

	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
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
