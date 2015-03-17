package lewiscrouch.ge.common.dimension;

import java.util.ArrayList;
import java.util.List;

import lewiscrouch.lib.dimension.Direction;
import lewiscrouch.lib.geom.Point;

public class Dimension
{
	private String title;

	private List<Entity> entities; // virtual
	private List<Tile> tiles; // physical

	public Dimension(String title, int scale)
	{
		this.title = title;

		this.entities = new ArrayList<Entity>();
		this.tiles = new ArrayList<Tile>();
	}

	public boolean tileExists(int x, int y)
	{
		for(Tile tile : this.tiles)
		{
			if(tile.getDimensionCoords().equalTo(new Point(x, y)))
			{
				return true;
			}
		}
		return false;
	}

	public boolean canMoveInDirection(Point dimensionCoords, Direction drc)
	{
		if(drc.compare(Direction.NORTH)) return this.tileExists(dimensionCoords.getX(), dimensionCoords.getY() - 1);
		if(drc.compare(Direction.EAST)) return this.tileExists(dimensionCoords.getX() + 1, dimensionCoords.getY());
		if(drc.compare(Direction.SOUTH)) return this.tileExists(dimensionCoords.getX(), dimensionCoords.getY() + 1);
		if(drc.compare(Direction.WEST)) return this.tileExists(dimensionCoords.getX() - 1, dimensionCoords.getY());
		return false;
	}

	public List<Entity> getEntities()
	{
		return this.entities;
	}

	public List<Tile> getTiles()
	{
		return this.tiles;
	}

	public String getTitle()
	{
		return this.title;
	}
}
