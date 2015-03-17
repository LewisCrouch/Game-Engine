package lewiscrouch.ge.common.dimension;

import lewiscrouch.lib.dimension.Direction;
import lewiscrouch.lib.geom.Point;

public abstract class Entity extends DimensionElement
{
	private int imageID;

	private boolean moving;
	private Direction moveDirection;
	private int moveTime;
	private int maxMoveTime;

	private boolean swimming;

	public Entity(Dimension dimension, Point dimensionCoords)
	{
		super(dimension, dimensionCoords);

		this.imageID = 0;

		this.moving = false;
		this.moveDirection = Direction.NORTH;
	}

	public void update()
	{
		if(this.moving)
		{
			if(--this.moveTime <= 0)
			{
				this.moving = false;
				this.moveTime = this.maxMoveTime;
				if(this.moveDirection.compare(Direction.NORTH)) this.getDimensionCoords().decrementY(1);
				if(this.moveDirection.compare(Direction.EAST)) this.getDimensionCoords().incrementX(1);;
				if(this.moveDirection.compare(Direction.SOUTH)) this.getDimensionCoords().incrementY(1);;
				if(this.moveDirection.compare(Direction.WEST)) this.getDimensionCoords().decrementX(1);;
			}
		}
	}

	public void move(Direction drc)
	{
		if(!this.moving && this.getDimension().canMoveInDirection(this.getDimensionCoords(), drc))
		{
			this.moveTime = this.maxMoveTime;
			this.moving = true;
			this.moveDirection = drc;
		}
	}

	public void moveUp()
	{
		this.move(Direction.NORTH);
	}

	public void moveRight()
	{
		this.move(Direction.EAST);
	}

	public void moveDown()
	{
		this.move(Direction.SOUTH);
	}

	public void moveLeft()
	{
		this.move(Direction.WEST);
	}

	public boolean isMoving()
	{
		return this.moving;
	}

	public Direction getMoveDirection()
	{
		return this.moveDirection;
	}

	public int getMoveTime()
	{
		return this.moveTime;
	}

	public int getMaxMoveTime()
	{
		return this.maxMoveTime;
	}

	public boolean isSwimming()
	{
		return this.swimming;
	}

	public void setSwimming(boolean swimming)
	{
		this.swimming = true;
	}

	public int getImageID()
	{
		return this.imageID;
	}
}
