package lewiscrouch.lib.display.renderable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class RString
	implements Renderable
{
	private int x;
	private int y;
	private int style;
	private int size;
	private int spacing;
	private String font;
	private String text;
	private Color color;
	private boolean shadow;
	private int shadowX;
	private int shadowY;
	private Color shadowColor;

	public RString(int x, int y, String text)
	{
		this.x = x;
		this.y = y;
		this.style = Font.PLAIN;
		this.size = 14;
		this.spacing = 2;
		this.font = "Yu Gothic";
		this.text = text;
		this.color = Color.WHITE;
		this.shadow = true;
		this.shadowX = 1;
		this.shadowY = 1;
		this.shadowColor = new Color(0, 0, 0, 0.5F);
	}

	@Override
	public void render(Graphics2D gfx)
	{
		int displacedY = (this.y + this.size) + this.size / 8;

		Font font = gfx.getFont();
		gfx.setFont(new Font(this.font, this.style, this.size));
		if(this.shadow)
		{
			gfx.setColor(this.shadowColor);
			gfx.drawString(this.text, this.x + this.shadowX, displacedY + this.shadowY);
		}
		gfx.setColor(this.color);
		gfx.drawString(this.text, this.x, displacedY);
		gfx.setFont(font);
	}

	public RString copy()
	{
		RString rs = new RString(this.x, this.y, this.text);
		rs.size = this.size;
		rs.font = this.font;
		rs.color = this.color;
		rs.shadow = this.shadow;
		rs.shadowX = this.shadowX;
		rs.shadowY = this.shadowY;
		rs.shadowColor = this.shadowColor;
		rs.style = this.style;
		return rs;
	}

	public Font buildFont()
	{
		return new Font(this.font, this.style, this.size);
	}

	public int getStringWidth()
	{
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);     
		Font font = this.buildFont();
		return (int) (font.getStringBounds(this.text, frc).getWidth());
	}

	public int getStringHeight()
	{
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);     
		Font font = this.buildFont();
		return (int) (font.getStringBounds(this.text, frc).getHeight());
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
		this.y = y;
	}

	public int getStyle()
	{
		return this.style;
	}

	public void setStyle(int style)
	{
		this.style = style;
	}

	public int getSize()
	{
		return this.size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getSpacing()
	{
		return this.spacing;
	}

	public void setSpacing(int spacing)
	{
		this.spacing = spacing;
	}

	public String getFont()
	{
		return this.font;
	}

	public void setFont(String font)
	{
		this.font = font;
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public boolean hasShadow()
	{
		return this.shadow;
	}

	public void setShadow(boolean shadow)
	{
		this.shadow = shadow;
	}

	public int getShadowX()
	{
		return this.shadowX;
	}

	public void setShadowX(int shadowX)
	{
		this.shadowX = shadowX;
	}

	public int getShadowY()
	{
		return this.shadowY;
	}

	public void setShadowY(int shadowY)
	{
		this.shadowY = shadowY;
	}

	public Color getShadowColor()
	{
		return this.shadowColor;
	}

	public void setShadowColor(Color shadowColor)
	{
		this.shadowColor = shadowColor;
	}
}
