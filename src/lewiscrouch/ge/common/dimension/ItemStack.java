package lewiscrouch.ge.common.dimension;

public class ItemStack
{
	private Item item;

	private int stackSize;
	private int maxStackSize;

	public ItemStack(Item item)
	{
		this.item = item;
		this.stackSize = 1;
		this.maxStackSize = 100;
	}

	public void removeStack()
	{
		this.stackSize--;
	}

	public Item getItem()
	{
		return this.item;
	}

	public int getStackSize()
	{
		return this.stackSize;
	}

	public void setStackSize(int stackSize)
	{
		this.stackSize = stackSize;
	}

	public int getMaxStackSize()
	{
		return this.maxStackSize;
	}

	public void setMaxStackSize(int maxStackSize)
	{
		this.maxStackSize = maxStackSize;
	}
}
