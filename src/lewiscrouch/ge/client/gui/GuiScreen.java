package lewiscrouch.ge.client.gui;

import java.awt.Graphics2D;

public abstract class GuiScreen
{
	public GuiScreen()
	{
		init();
	}

	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D gfx);
}
