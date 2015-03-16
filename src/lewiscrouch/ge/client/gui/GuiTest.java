package lewiscrouch.ge.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GuiTest extends GuiScreen
{
	private String title;
	private int startSize;
	private int size;
	private boolean direction;

	@Override
	public void init()
	{
		this.title = "My Title!";
		this.startSize = this.size = 26;
		this.direction = true;
	}

	@Override
	public void update()
	{
		if(this.direction)
		{
			if(++this.size >= this.startSize + (this.startSize / 3))
			{
				this.direction = false;
			}
		}
		else
		{
			if(--this.size <= this.startSize - (this.startSize / 3))
			{
				this.direction = true;
			}
		}
	}

	@Override
	public void render(Graphics2D gfx)
	{
		Canvas.drawRect(gfx, 0, 0, Canvas.width(), Canvas.height(), new Color(50, 50, 50));

		gfx.setFont(new Font("Arial", Font.PLAIN, this.size));

		int x = 15;
		int y = 100;

		Canvas.drawStrWithOutline(gfx, this.title, x, y, 1, Color.WHITE, Color.BLACK);
		gfx.setFont(Canvas.DEFAULT_FONT);
	}
}
