package lewiscrouch.ge.common.dimension;

import java.util.ArrayList;
import java.util.List;

public class Dimension
{
	private String title;

	private int scale;
	private List<Chunk> chunks;
	private List<Entity> entities; // virtual
	private List<Tile> tiles; // physical

	public Dimension(String title, int scale)
	{
		this.title = title;

		this.scale = scale;
		if(this.scale < 1) this.scale = 1;

		this.chunks = new ArrayList<Chunk>();
		this.entities = new ArrayList<Entity>();
		this.tiles = new ArrayList<Tile>();

		Chunk initial = new Chunk(0, 0);
		this.chunks.add(initial);

		int tempScale = this.scale;
		while(tempScale > 0)
		{
			int tempScaleX = tempScale;
			int tempScaleY = tempScale;
			for(int i = -tempScaleX; i < tempScaleX; i++)
			{
				for(int j = -tempScaleY; j < tempScaleY; j++)
				{
					this.createChunkIfEmpty(initial.getChunkX() + i, initial.getChunkY() + j);
					this.createChunkIfEmpty(initial.getChunkX() - i, initial.getChunkY() - j);
				}
			}
			tempScale--;
		}
	}

	public boolean chunkExists(int x, int y)
	{
		for(Chunk chunk : this.chunks)
		{
			if(chunk.getChunkX() == x)
			{
				if(chunk.getChunkY() == y)
				{
					return true;
				}
			}
		}
		return false;
	}

	public void createChunkIfEmpty(int x, int y)
	{
		if(!this.chunkExists(x, y)) this.chunks.add(new Chunk(x, y));
	}

	public int getScale()
	{
		return this.scale;
	}

	public List<Chunk> getChunks()
	{
		return this.chunks;
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
