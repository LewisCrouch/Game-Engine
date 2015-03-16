package lewiscrouch.ge.client.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.Keyboard;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RRectangle;

public class GuiIngameMenu extends GuiSubScreen
{
	public GuiIngameMenu(GuiScreen parent)
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
		this.addControl(new GuiButton(1, x, y + ((h + p) * i++), "Resume Game"));
		this.addControl(new GuiButton(2, x, y + ((h + p) * i++), "Save Game"));
		this.addControl(new GuiButton(3, x, y + ((h + p) * i++), "Options"));
		this.addControl(new GuiButton(4, x, y + ((h + p) * i++), "Main Menu"));
	}

	@Override
	public void updateScreen()
	{
		if(Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			this.setVisible(false);
		}
	}

	@Override
	public void renderScreen()
	{
		RenderQueue.add(new RRectangle(0, 0, this.getWidth(), this.getHeight(), new Color(0, 0, 0, 0.5F)));
		GuiMainMenu.renderTitle(Game.getInstance().getName(), "Menu");
	}

	@Override
	public void actionPerformed(int id)
	{
		switch(id)
		{
			case 1:
				this.setVisible(false);
				break;
			case 2:
				System.out.println("TODO: SAVE GAME");
				break;
			case 3:
				this.getParent().setSubScreen(new GuiIngameOptions(this.getParent()));
				this.getParent().getSubScreen().setVisible(true);
				break;
			case 4:
				Display.getInstance().setResizable(true);
				this.displayScreen(new GuiMainMenu());
				break;
		}
	}
}
