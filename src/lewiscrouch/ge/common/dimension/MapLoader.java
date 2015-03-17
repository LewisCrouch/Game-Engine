package lewiscrouch.ge.common.dimension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lewiscrouch.lib.geom.Point;
import lewiscrouch.lib.resource.ResourceManager;
import lewiscrouch.lib.util.Logger;

public class MapLoader
{
	private Dimension dimension;
	private String mapFilename;

	public MapLoader(Dimension dimension, String mapFilename)
	{
		this.dimension = dimension;
		this.mapFilename = mapFilename;
	}

	public List<Tile> loadMap()
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(ResourceManager.getResourceFile(this.mapFilename)));

			List<Tile> tiles = new ArrayList<Tile>();

			String line = null;
			int y = 0;
			while((line = reader.readLine()) != null)
			{
				int x = 0;
				String[] ts = line.split(" ");
				for(String encoded : ts)
				{
					if(!encoded.equalsIgnoreCase("n"))
					{
						Point p = new Point(x, y);
						for(Tile tile : tiles)
						{
							if(tile.getDimensionCoords().equalTo(p))
							{
								Logger.err("Map file '" + this.mapFilename + "' appears corrupt (tile overlap)!");
								return null;
							}
						}
						tiles.add(Tile.decodeTile(this.dimension, p, encoded));
					}
					x++;
				}
				y++;
			}

			return tiles;
		}
		catch(Exception ex)
		{
			Logger.err("Failed to load map from file '" + this.mapFilename + "': " + ex);
		}
		finally
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				}
				catch(IOException ex1)
				{
					ex1.printStackTrace();
				}
			}
		}
		return null;
	}

	public Dimension getDimension()
	{
		return this.dimension;
	}

	public String getMapFilename()
	{
		return this.mapFilename;
	}
}
