package lewiscrouch.ge.client.gui;

import java.awt.Color;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RRectangle;

public class GuiIngameOptions extends GuiSubScreen
{
	public GuiIngameOptions(GuiScreen parent)
	{
		super(parent);
	}

	@Override
	public void init()
	{
		int s = GuiButton.DEFAULT_SCALE;
		int w = GuiButton.DEFAULT_WIDTH * s;
		int h = GuiButton.DEFAULT_HEIGHT * s;
		int x = (Display.getInnerWidth() / 2) - (w / 2);
		int y = 116;
		int p = 16;
		int i = 0;
		this.addControl(new GuiButton(1, x, y + ((h + p) * i++), ""));
		this.addControl(new GuiButton(2, x, y + ((h + p) * i++), ""));
		this.addControl(new GuiButton(3, x, y + ((h + p) * i++), ""));
		this.addControl(new GuiButton(4, x, y + ((h + p) * i++), "Back to Menu"));
	}

	@Override
	public void updateScreen()
	{
		
	}

	@Override
	public void renderScreen()
	{
		RenderQueue.add(new RRectangle(0, 0, this.getWidth(), this.getHeight(), new Color(0, 0, 0, 0.5F)));
		GuiMainMenu.renderTitle(Game.getInstance().getName(), "Options");
	}

	@Override
	public void actionPerformed(int id)
	{
		switch(id)
		{
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				this.getParent().setSubScreen(new GuiIngameMenu(this.getParent()));
				this.getParent().getSubScreen().setVisible(true);
				break;
		}
	}
}
