package lewiscrouch.ge.common.dimension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.ge.client.gui.GuiMessage;
import lewiscrouch.lib.dimension.Direction;
import lewiscrouch.lib.geom.Point;
import lewiscrouch.lib.resource.ResourceManager;
import lewiscrouch.lib.util.Logger;

public class Tile extends DimensionElement
{
	private int imageID;
	private List<Integer> backgroundImages;

	private boolean solid;
	private boolean breakable;
	private int durability;
	private boolean renderPriority;

	private boolean hasEvent;
	private List<TileEvent> eventsPlayerEnter;
	private List<TileEvent> eventsPlayerStartLeave;
	private List<TileEvent> eventsPlayerLeave;
	private List<TileEvent> eventsPlayerInteract;

	public Tile(Dimension dimension, Point dimensionCoords)
	{
		super(dimension, dimensionCoords);

		this.imageID = 0;
		this.backgroundImages = new ArrayList<Integer>();

		this.solid = false;
		this.breakable = false;
		this.durability = 10;
		this.renderPriority = false;

		this.hasEvent = false;
		this.eventsPlayerEnter = new ArrayList<TileEvent>();
		this.eventsPlayerStartLeave = new ArrayList<TileEvent>();
		this.eventsPlayerLeave = new ArrayList<TileEvent>();
		this.eventsPlayerInteract = new ArrayList<TileEvent>();
	}

	public static Tile decodeTile(Dimension dimension, Point dimensionCoords, String encoded)
	{
		try
		{
			Tile tile = new Tile(dimension, dimensionCoords);
			String[] parts = encoded.split(",");
			for(String part : parts)
			{
				String key = part.substring(0, 1);
				String value = part.substring(1);
				if(key.equalsIgnoreCase("t"))
				{
					return Tile.loadTile(dimension, dimensionCoords, part);
				}
				else if(key.equalsIgnoreCase("i"))
				{
					tile.imageID = Integer.parseInt(value);
				}
				else if(key.equalsIgnoreCase("g"))
				{
					tile.addBackgroundImage(Integer.parseInt(value));
				}
				else if(key.equalsIgnoreCase("s"))
				{
					if(value.equalsIgnoreCase("1")) tile.solid = true; 
				}
				else if(key.equalsIgnoreCase("b"))
				{
					if(value.equalsIgnoreCase("1")) tile.breakable = true; 
				}
				else if(key.equalsIgnoreCase("d"))
				{
					tile.durability = Integer.parseInt(value);
				}
				else if(key.equalsIgnoreCase("r"))
				{
					if(value.equalsIgnoreCase("1")) tile.renderPriority = true; 
				}
				else if(key.equalsIgnoreCase("e"))
				{
					String event = part.substring(1, 3);
//					System.out.println("Event: " + event);
					int action = Integer.parseInt(part.substring(3, part.indexOf("-")));
//					System.out.println("Action: " + action);
					value = part.substring(part.indexOf("-") + 1);
//					System.out.println("Value: " + value + "\n");

					if(event.equalsIgnoreCase("pe")) tile.eventsPlayerEnter.add(new TileEvent(action, value));
					else if(event.equalsIgnoreCase("ps")) tile.eventsPlayerStartLeave.add(new TileEvent(action, value));
					else if(event.equalsIgnoreCase("pl")) tile.eventsPlayerLeave.add(new TileEvent(action, value));
					else if(event.equalsIgnoreCase("pi")) tile.eventsPlayerInteract.add(new TileEvent(action, value));

					tile.hasEvent = true;
				}
			}
			return tile;
		}
		catch(Exception ex)
		{
			System.err.println("Failed to decode tile: " + encoded);
			ex.printStackTrace();
			return null;
		}
	}

	public static Tile loadTile(Dimension dimension, Point dimensionCoords, String file)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(ResourceManager.getResourceFile(file + ".tile")));

			String line = null;
			while((line = reader.readLine()) != null)
			{
				return Tile.decodeTile(dimension, dimensionCoords, line);
			}
		}
		catch(Exception ex)
		{
			System.err.println("Failed to load tile: " + file);
			ex.printStackTrace();
		}
		finally
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				}
				catch(IOException ex)
				{
					
				}
			}
		}
		return null;
	}

	public void processEvent(TileEvent te, Player player)
	{
		try
		{
			if(EnumTileEvent.KILL_PLAYER.equalTo(te.getAction())) player.getLifeStats().kill();
			else if(EnumTileEvent.CHANGE_MAP.equalTo(te.getAction())) Logger.info("Tile event CHANGE_MAP not yet implemented!"); // TODO
			else if(EnumTileEvent.MOVE_PLAYER.equalTo(te.getAction()))
			{
				int drc = Integer.parseInt(te.getValue());
				if(Direction.NORTH.compare(drc)) player.moveUp();
				if(Direction.EAST.compare(drc)) player.moveRight();
				if(Direction.SOUTH.compare(drc)) player.moveDown();
				if(Direction.WEST.compare(drc)) player.moveLeft();
			}
			else if(EnumTileEvent.SOLIDIFY.equalTo(te.getAction())) this.solid = true;
			else if(EnumTileEvent.SWIM.equalTo(te.getAction()))
			{
				if(te.getValue().equalsIgnoreCase("1")) player.setSwimming(true);
			}
			else if(EnumTileEvent.TELEPORT.equalTo(te.getAction()))
			{
				String[] coords = te.getValue().split(":");
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				player.setDimensionCoords(x, y);
			}
			else if(EnumTileEvent.BREAK.equalTo(te.getAction()))
			{
				this.setSolid(false);
				this.setImageID(Integer.parseInt(te.getValue()));
			}
			else if(EnumTileEvent.MSG.equalTo(te.getAction()))
			{
				String[] msg = te.getValue().replace("+", " ").split("/");
				Game.getInstance().getCurrentScreen().setSubScreen(new GuiMessage(Game.getInstance().getCurrentScreen(), msg));
			}
		}
		catch(Exception ex)
		{
			System.err.println("Failed to process event (Action: " + te.getAction() + ", Value = " + te.getValue() + ")!");
			ex.printStackTrace();
		}
	}

	public void onPlayerEnter(Player player)
	{
		Iterator<TileEvent> it = this.eventsPlayerEnter.iterator();
		while(it.hasNext())
		{
			TileEvent te = (TileEvent) it.next();
			String[] parts = te.getValue().split("-");
			int drc = Integer.parseInt(parts[0]);
			String value = parts[1];
			if(player.getMoveDirection().compare(drc) || drc == 0)
			{
				this.processEvent(new TileEvent(te.getAction(), value), player);
			}
		}
	}
	public void onPlayerStartLeave(Player player)
	{
		Iterator<TileEvent> it = this.eventsPlayerStartLeave.iterator();
		while(it.hasNext())
		{
			TileEvent te = (TileEvent) it.next();
			this.processEvent(te, player);
		}
	}
	public void onPlayerLeave(Player player)
	{
		Iterator<TileEvent> it = this.eventsPlayerLeave.iterator();
		while(it.hasNext())
		{
			TileEvent te = (TileEvent) it.next();
			String[] parts = te.getValue().split("-");
			int drc = Integer.parseInt(parts[0]);
			String value = parts[1];
			if(player.getMoveDirection().compare(drc))
			{
				this.processEvent(new TileEvent(te.getAction(), value), player);
			}
		}
	}
	public void onPlayerInteract(Player player)
	{
		Iterator<TileEvent> it = this.eventsPlayerInteract.iterator();
		while(it.hasNext())
		{
			TileEvent te = (TileEvent) it.next();
			this.processEvent(te, player);
		}
	}

	public void addBackgroundImage(int imageID)
	{
		this.backgroundImages.add(imageID);
	}

	public int getImageID()
	{
		return this.imageID;
	}

	public void setImageID(int imageID)
	{
		this.imageID = imageID;
	}

	public List<Integer> getBackgroundImages()
	{
		return this.backgroundImages;
	}

	public boolean isSolid()
	{
		return this.solid;
	}

	public void setSolid(boolean solid)
	{
		this.solid = solid;
	}

	public boolean isBreakable()
	{
		return this.breakable;
	}

	public void setBreakable(boolean breakable)
	{
		this.breakable = breakable;
	}

	public int getDurability()
	{
		return this.durability;
	}

	public void setDurability(int durability)
	{
		this.durability = durability;
	}

	public boolean hasRenderPriority()
	{
		return this.renderPriority;
	}

	public void setRenderPriority(boolean renderPriority)
	{
		this.renderPriority = renderPriority;
	}

	public boolean hasEvent()
	{
		return this.hasEvent;
	}

	public List<TileEvent> getPlayerEnterEvents()
	{
		return this.eventsPlayerEnter;
	}

	public List<TileEvent> getPlayerStartLeaveEvents()
	{
		return this.eventsPlayerLeave;
	}

	public List<TileEvent> getPlayerLeaveEvents()
	{
		return this.eventsPlayerLeave;
	}

	public List<TileEvent> getPlayerInteractEvents()
	{
		return this.eventsPlayerInteract;
	}
}