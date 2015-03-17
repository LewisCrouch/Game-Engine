package lewiscrouch.ge.client.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.Keyboard;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RImage;
import lewiscrouch.lib.display.renderable.RString;
import lewiscrouch.lib.resource.ImageRegister;

public class GuiMessage extends GuiSubScreen
{
	private int bg;
	private String[] messages;

	public GuiMessage(GuiScreen parent, String[] messages)
	{
		super(parent);
		this.messages = messages;
		this.setVisible(true);
	}

	@Override
	public void init()
	{
		this.bg = ImageRegister.registerImage("message.png");
	}

	@Override
	public void updateScreen()
	{
		if(Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) this.setVisible(false);
		if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) this.setVisible(false);
		if(Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) this.setVisible(false);
		if(Keyboard.isKeyPressed(KeyEvent.VK_E)) this.setVisible(false);
	}

	@Override
	public void renderScreen()
	{
		int w = Display.getInnerWidth();
		int h = 128;
		int x = 0;
		int y = Display.getInnerHeight() - h;
		RImage ri = new RImage(x, y, this.bg);
		ri.setWidth(w);
		ri.setHeight(h);
		RenderQueue.add(ri);

		int i = 0;
		for(String message : this.messages)
		{
			RString rs = new RString(x + 14, y + 14 + (i++ * 28), message);
			rs.setSize(24);
			rs.setShadow(false);
			rs.setFont("Yu Gothic");
			rs.setColor(Color.BLACK);
			RenderQueue.add(rs);
		}
	}

}
