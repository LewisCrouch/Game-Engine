package lewiscrouch.ge.client.gui;

import lewiscrouch.lib.display.renderable.RString;

public class GuiSmallButton extends GuiButton
{
	public GuiSmallButton(int id, int x, int y, int scale, String text)
	{
		super(id, x, y, scale, text);
		this.setWidth(this.getHeight());

		RString rs = new RString(0, 0, text);
		rs.setSize(this.getHeight() / 2);
		rs.setX(this.getX() + ((this.getWidth() / 2) - (rs.getStringWidth() / 2)));
		rs.setY(this.getY() + ((this.getHeight() / 2) - (rs.getStringHeight() / 2)));
		this.setLabel(rs);
	}

	public GuiSmallButton(int id, int x, int y, String text)
	{
		this(id, x, y, GuiButton.DEFAULT_SCALE, text);
	}
}
