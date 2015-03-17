package lewiscrouch.ge.common.dimension;

public class ItemStack
{
	private int itemID;

	private int stackSize;
	private int maxStackSize;

	public ItemStack(int itemID)
	{
		this.itemID = itemID;
		this.stackSize = 1;
		this.maxStackSize = 100;
	}

	public int getItemID()
	{
		return this.itemID;
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

	public Item getItem()
	{
		return Items.getItem(this.itemID);
	}
}
