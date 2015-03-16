package lewiscrouch.ge.client.gui;

import java.awt.Color;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RImage;
import lewiscrouch.lib.display.renderable.RRectangle;
import lewiscrouch.lib.display.renderable.RString;
import lewiscrouch.lib.resource.ImageRegister;

public class GuiButton extends GuiControl
{
	public static final int DEFAULT_IMAGE_ID = ImageRegister.registerImage("button.png");

	public static final int DEFAULT_SCALE = 3;
	public static final int DEFAULT_HEIGHT = Game.SIZE;
	public static final int DEFAULT_WIDTH = GuiButton.DEFAULT_HEIGHT * 4;

	public GuiButton(int id, int x, int y, int scale, String text)
	{
		super(id, x, y, (GuiButton.DEFAULT_HEIGHT * scale) * 4, GuiButton.DEFAULT_HEIGHT * scale, Color.BLACK);

		RString rs = new RString(0, 0, text);
		rs.setSize(this.getHeight() / 2);
		rs.setX(this.getX() + ((this.getWidth() / 2) - (rs.getStringWidth() / 2)));
		rs.setY(this.getY() + ((this.getHeight() / 2) - (rs.getStringHeight() / 2)));
		this.setLabel(rs);
	}

	public GuiButton(int id, int x, int y, String text)
	{
		this(id, x, y, GuiButton.DEFAULT_SCALE, text);
	}

	@Override
	public void render()
	{
		super.render();

		int i = this.getHeight();
		for(int x = 0; x < this.getWidth() / i; x++)
		{
			for(int y = 0; y < this.getHeight() / i; y++)
			{
				RImage ri = new RImage(this.getX() + (x * i), this.getY() + (y * i), GuiButton.DEFAULT_IMAGE_ID);
				ri.setWidth(i);
				ri.setHeight(i);
				RenderQueue.add(ri);
			}
		}

		Color c = Color.BLACK;
		if(this.isFocused()) c = Color.WHITE;
		RenderQueue.add(new RRectangle(this.getX(), this.getY(), this.getWidth(), 2, c));
		RenderQueue.add(new RRectangle(this.getX(), this.getY(), 2, this.getHeight(), c));
		RenderQueue.add(new RRectangle(this.getX() + this.getWidth() - 2, this.getY(), 2, this.getHeight(), c));
		RenderQueue.add(new RRectangle(this.getX(), this.getY() + this.getHeight() - 2, this.getWidth(), 2, c));

		if(this.isHovered()) RenderQueue.add(new RRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight(), new Color(0, 0, 0, 0.5F)));

		if(this.hasLabel()) RenderQueue.add(this.getLabel());
	}

	@Override
	public void onMouseClick()
	{
		if(this.hasParent()) this.getParent().actionPerformed(this.getID());
	}
}
