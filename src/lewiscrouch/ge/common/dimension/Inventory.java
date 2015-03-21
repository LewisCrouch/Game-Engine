package lewiscrouch.ge.common.dimension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lewiscrouch.lib.util.Base64;

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

	public String encode()
		throws Exception
	{
		try
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);

			oos.writeInt(this.getSlots());

			for(ItemStack is : this.items)
			{
				oos.writeObject(is);
			}

			oos.close();
			return Base64.encodeBytes(out.toByteArray());
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}

	public static Inventory decode(String data)
		throws Exception
	{
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(Base64.decode(data));
			ObjectInputStream ois = new ObjectInputStream(in);

			Inventory inv = new Inventory(ois.readInt());

			for(int i = 0; i < inv.getSlots(); i++)
			{
				inv.setItemStack(i, (ItemStack) ois.readObject());
			}

			ois.close();
			return inv;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
}
