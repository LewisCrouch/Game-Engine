package lewiscrouch.lib.display.renderable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class REllipse
	implements Renderable
{
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;

	public REllipse(int x, int y, int width, int height, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void render(Graphics2D gfx)
	{
		gfx.setColor(this.color);
		gfx.fill(new Ellipse2D.Float(this.x, this.y, this.width, this.height));
	}

	public REllipse copy()
	{
		return new REllipse(this.x, this.y, this.width, this.height, this.color);
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

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}