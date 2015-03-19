package lewiscrouch.ge.client.gui;

import java.awt.event.KeyEvent;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.ge.common.dimension.Dimension;
import lewiscrouch.ge.common.dimension.Player;
import lewiscrouch.ge.common.dimension.Tile;
import lewiscrouch.lib.display.Keyboard;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RImage;
import lewiscrouch.lib.display.renderable.RString;

public class GuiIngame extends GuiScreen
{
	private GuiChat chat;

	private Dimension dimension;
	private int viewportX;
	private int viewportY;

	@Override
	public void init()
	{
		this.setSubScreen(new GuiIngameMenu(this));

		this.addControl(this.chat = new GuiChat(1));
		this.chat.giveFocus();

		this.dimension = new Dimension("Template");

		Game.getInstance().setPlayer(new Player("Lewis", this.dimension, this.dimension.getSpawnPoint()));

		this.viewportX = 0;
		this.viewportY = 0;
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

		Player player = Game.getInstance().getPlayer();
		int speed = 8;
		if(Keyboard.isKeyDown(KeyEvent.VK_SHIFT)) speed *= 2;
		if(!player.isMoving())
		{
			if(Keyboard.isKeyDown(KeyEvent.VK_W))
			{
//				Game.getInstance().getClient().sendPacket(new PacketMove(Direction.NORTH.getLabel()));
				this.viewportY -= speed;
			}
			if(Keyboard.isKeyDown(KeyEvent.VK_A))
			{
//				Game.getInstance().getClient().sendPacket(new PacketMove(Direction.EAST.getLabel()));
				this.viewportX -= speed;
			}
			if(Keyboard.isKeyDown(KeyEvent.VK_S))
			{
//				Game.getInstance().getClient().sendPacket(new PacketMove(Direction.SOUTH.getLabel()));
				this.viewportY += speed;
			}
			if(Keyboard.isKeyDown(KeyEvent.VK_D))
			{
//				Game.getInstance().getClient().sendPacket(new PacketMove(Direction.WEST.getLabel()));
				this.viewportX += speed;
			}
		}
	}

	@Override
	public void renderScreen()
	{
		for(Tile tile : this.dimension.getTiles())
		{
			for(Integer imageID : tile.getBackgroundImages())
			{
				int x = tile.getDimensionCoords().getX();
				int y = tile.getDimensionCoords().getY();
				RImage ri = new RImage(x, y, imageID);
				ri.setWidth(Game.SCALED_SIZE());
				ri.setHeight(Game.SCALED_SIZE());
				ri.setX(this.viewportX + Game.SCALED_SIZE() * x);
				ri.setY(this.viewportY + Game.SCALED_SIZE() * y);
				RenderQueue.add(ri);
			}

			int x = tile.getDimensionCoords().getX();
			int y = tile.getDimensionCoords().getY();
			int imageID = tile.getImageID();
			RImage ri = new RImage(x, y, imageID);
			ri.setWidth(Game.SCALED_SIZE());
			ri.setHeight(Game.SCALED_SIZE());
			ri.setX(this.viewportX + Game.SCALED_SIZE() * x);
			ri.setY(this.viewportY + Game.SCALED_SIZE() * y);
			RenderQueue.add(ri);
		}

		RString rs = null;

		rs = new RString(10, 10, "Map: " + this.dimension.getTitle());
		rs.setSize(18);
		rs.setShadow(true);
		RenderQueue.add(rs);

		rs = new RString(10, 30, "Viewport X" + this.viewportX + ", Y" + this.viewportY);
		rs.setShadow(true);
		RenderQueue.add(rs);
	}

	public GuiChat getChat()
	{
		return this.chat;
	}

	public Dimension getDimension()
	{
		return this.dimension;
	}
}