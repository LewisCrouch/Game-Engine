package lewiscrouch.ge.client.gui;

import java.awt.event.KeyEvent;

import lewiscrouch.lib.display.Keyboard;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RString;

public class GuiIngame extends GuiScreen
{
	private GuiChat chat;

	@Override
	public void init()
	{
		this.setSubScreen(new GuiIngameMenu(this));

		this.addControl(this.chat = new GuiChat(1));
		this.chat.giveFocus();
	}

	@Override
	public void updateScreen()
	{
		if(this.chat.isVisible()) return;
		if(Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) this.chat.setVisible(true);

		if(Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			this.setSubScreen(new GuiIngameMenu(this));
			this.getSubScreen().setVisible(true);
		}
	}

	@Override
	public void renderScreen()
	{
		this.renderDefaultBackground();

		RString rs = new RString(20, 20, "Press 'Enter' for chat.");
		rs.setSize(24);
		rs.setShadow(true);
		RenderQueue.add(rs);
	}
}