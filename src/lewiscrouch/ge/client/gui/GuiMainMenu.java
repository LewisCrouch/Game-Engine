package lewiscrouch.ge.client.gui;

import java.awt.Color;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RRectangle;
import lewiscrouch.lib.display.renderable.RString;

public class GuiMainMenu extends GuiScreen
{
	private String title;
	private String splash;
	private int splashSize;
	private int splashState;

	@Override
	public void init()
	{
		String name = Game.getInstance().getName();
		//this.title = name.split(" ")[0];
		//this.splash = name.split(" ")[1];
		this.title = name;
		this.splash = "Main Menu";
		this.splashSize = 12;
		this.splashState = 0;

		int s = GuiButton.DEFAULT_SCALE;
		int w = GuiButton.DEFAULT_WIDTH * s;
		int h = GuiButton.DEFAULT_HEIGHT * s;
		int x = (Display.getInnerWidth() / 2) - (w / 2);
		int y = 116;
		int p = 16;
		int i = 0;
		this.addControl(new GuiButton(1, x, y + ((h + p) * i++), "New Game"));
		this.addControl(new GuiButton(2, x, y + ((h + p) * i++), "Load Game"));
		this.addControl(new GuiButton(3, x, y + ((h + p) * i++), "Options"));
		this.addControl(new GuiButton(4, x, y + ((h + p) * i++), "Exit Game"));

		GuiTextField txt = new GuiTextField(5, x, y + ((h + p) * i++), w);
		txt.setText("Testing TextField");
		this.addControl(txt);
	}

	@Override
	public void updateScreen()
	{
		if(this.splashState == 0)
		{
			if(++this.splashSize >= 17) this.splashState++;
		}
		else if(this.splashState < 10)
		{
			this.splashState++;
		}
		else
		{
			if(--this.splashSize <= 12) this.splashState = 0;
		}
	}

	@Override
	public void renderScreen()
	{
		this.renderDefaultBackground();
		GuiMainMenu.renderTitle(this.title, this.splash);
	}

	public void actionPerformed(int id)
	{
		switch(id)
		{
			case 1:
				this.displayScreen(new GuiIngame());
				break;
			case 2:
				System.out.println("Load Game");
				break;
			case 3:
				this.displayScreen(new GuiOptions());
				break;
			case 4:
				System.exit(0);
				break;
		}
	}

	public static void renderTitle(String title, String subtitle)
	{
		int x = Display.getInnerWidth() / 2;
		int y = 16;

		RString rsTitle = new RString(0, y, title);
		rsTitle.setFont("Yu Gothic");
		rsTitle.setSize(48);
		rsTitle.setX(x - (rsTitle.getStringWidth() / 2));
		rsTitle.setColor(Color.WHITE);
		rsTitle.setShadowX(0);
		rsTitle.setShadowY(2);

		int w = rsTitle.getStringWidth() + 20;
		int h = 76;
		RRectangle rr = new RRectangle(x - (w / 2), 20, w, h, new Color(0, 0, 0, 0.6F));
		RenderQueue.add(rr);

		RenderQueue.add(rsTitle);

		RString rsSubtitle = new RString(0, y + rsTitle.getSize() + 6, subtitle);
		rsSubtitle.setFont("Yu Gothic");
		rsSubtitle.setSize(16);
		rsSubtitle.setX(x - (rsSubtitle.getStringWidth() / 2));
		RenderQueue.add(rsSubtitle);
	}
}
