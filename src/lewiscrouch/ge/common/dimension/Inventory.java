package lewiscrouch.ge.common.dimension;

public class Inventory
{
	private ItemStack[] items;

	public Inventory(int slots)
	{
		this.items = new ItemStack[slots];
	}

	public boolean addItemStack(ItemStack itemStack)
	{
		for(int i = 0; i < this.items.length; i++)
		{
			if(!this.itemStackExists(i))
			{
				this.items[i] = itemStack;
				return true;
			}
		}
		return false;
	}

	public boolean setItemStack(int index, ItemStack itemStack)
	{
		if(this.itemStackIndexAvailable(index))
		{
			this.items[index] = itemStack;
			return true;
		}
		return false;
	}

	public int getSlots()
	{
		return this.items.length;
	}

	public ItemStack getItemStack(int id)
	{
		if(!this.itemStackExists(id)) return null;
		return this.items[id];
	}

	public boolean itemStackIndexAvailable(int index)
	{
		return index > -1 && index < this.items.length;
	}

	public boolean itemStackExists(int index)
	{
		if(this.itemStackIndexAvailable(index)) return false;
		if(this.items[index] == null) return false;
		return true;
	}

	public ItemStack[] getItems()
	{
		return this.items;
	}
}
