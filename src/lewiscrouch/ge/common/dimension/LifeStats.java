package lewiscrouch.ge.common.dimension;

public class LifeStats
{
	private Entity entity;

	private int health;
	private int maxHealth;

	public LifeStats(Entity entity, int health)
	{
		this.entity = entity;
		this.health = health;
		this.maxHealth = health;
	}

	public void update()
	{
		if(this.isDead()) this.entity.setDead(true);
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

	public Entity getEntity()
	{
		return this.entity;
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
		return this.health <= 0;
	}
}
