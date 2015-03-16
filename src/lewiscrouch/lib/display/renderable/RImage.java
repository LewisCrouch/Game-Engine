package lewiscrouch.lib.display.renderable;

import java.awt.Graphics2D;
import java.awt.Image;

import lewiscrouch.lib.resource.ImageRegister;

public class RImage
	implements Renderable
{
	private int x;
	private int y;
	private int width;
	private int height;
	private int imageID;

	public RImage(int x, int y, int imageID)
	{
		this.x = x;
		this.y = y;
		this.width = -1;
		this.height = -1;
		this.imageID = imageID;
	}

	@Override
	public void render(Graphics2D gfx)
	{
		Image image = ImageRegister.getImage(this.imageID);
		if(this.width == -1 || this.height == -1)
		{
			this.width = image.getWidth(null);
			this.height = image.getHeight(null);
		}
		gfx.drawImage(image, this.x, this.y, this.width, this.height, null);
	}

	public RImage copy()
	{
		RImage ri = new RImage(this.x, this.y, this.imageID);
		ri.width = this.width;
		ri.height = this.height;
		return ri;
	}

	public int getX()
	{
		return this.x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setY(int y)
	{
		this. y = y;
	}

	public int getWidth()
	{
		return this.width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getImageID()
	{
		return this.imageID;
	}

	public void setImageIDr(int imageID)
	{
		this.imageID = imageID;
	}
}