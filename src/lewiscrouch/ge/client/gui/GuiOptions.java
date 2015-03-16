package lewiscrouch.ge.client.gui;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.lib.display.Display;

public class GuiOptions extends GuiScreen
{
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
		this.addControl(new GuiButton(4, x, y + ((h + p) * i++), "Main Menu"));
	}

	@Override
	public void updateScreen()
	{
		
	}

	@Override
	public void renderScreen()
	{
		this.renderDefaultBackground();
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
				this.displayScreen(new GuiMainMenu());
				break;
		}
	}
}
