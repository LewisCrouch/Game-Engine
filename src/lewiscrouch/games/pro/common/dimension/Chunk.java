package lewiscrouch.games.pro.common.dimension;

public class Chunk
{
	public static final int MAX_DEPTH = 16;

	private int chunkX;
	private int chunkY;

	public Chunk(int chunkX, int chunkY)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}

	public int getChunkX()
	{
		return this.chunkX;
	}

	public int getChunkY()
	{
		return this.chunkY;
	}
}
