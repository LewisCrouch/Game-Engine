package lewiscrouch.ge.common.dimension;

import lewiscrouch.lib.util.Logger;

public class Items
{
	private static Item[] items;

	public static boolean registerItem(int id, Item item)
	{
		if(!Items.itemIndexAvailable(id))
		{
			Logger.err("Failed to register item: " + id + " exceeds item cap!");
			return false;
		}
		if(Items.itemExists(id))
		{
			Logger.err("Failed to register item: " + id + " already registered!");
			return false;
		}

		Items.getItems()[id] = item;
		Logger.info("Item with id " + id + " registered.");

		return true;
	}

	public static Item[] getItems()
	{
		if(Items.items == null) Items.items = new Item[1024];
		return Items.items;
	}

	public static Item getItem(int id)
	{
		if(!Items.itemExists(id)) return null;
		return Items.getItems()[id];
	}

	public static boolean itemIndexAvailable(int id)
	{
		return id > -1 && id < Items.getItems().length;
	}

	public static boolean itemExists(int id)
	{
		if(Items.itemIndexAvailable(id)) return false;
		if(Items.getItems()[id] == null) return false;
		return true;
	}
}
