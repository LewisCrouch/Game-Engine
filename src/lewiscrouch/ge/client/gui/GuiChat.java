package lewiscrouch.ge.client.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.Keyboard;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RRectangle;

public class GuiChat extends GuiTextField
{
	public GuiChat(int id)
	{
		super(id, 4, Display.getInnerHeight() - (32 + 4), Display.getInnerWidth() - 8);
		this.setColor(new Color(0, 0, 0, 180));

		this.setVisible(false);
		this.setMaxLength(9999);
	}

	@Override
	public void update()
	{
		if(!this.isVisible()) return;

		super.update();

		if(Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) this.setVisible(false);
		if(Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) this.setVisible(false);
	}

	@Override
	public void render()
	{
		if(!this.isVisible()) return;

		RenderQueue.add(new RRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getColor()));

		if(this.isSelected()) RenderQueue.add(new RRectangle(this.getX() + 8, this.getY() + 8, this.getWidth() - 16, this.getHeight() - 16, new Color(0, 80, 180, 180)));

		this.getLabel().setText(this.getText());

		this.getLabel().setX(this.getX() + 8);
		this.getLabel().setY(this.getY() + 8);//1 + (this.getHeight() / 2 - this.getLabel().getStringHeight() / 2));
		RenderQueue.add(this.getLabel());
	}
}
