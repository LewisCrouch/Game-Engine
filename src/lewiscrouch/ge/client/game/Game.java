package lewiscrouch.ge.client.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import lewiscrouch.ge.client.gui.Canvas;
import lewiscrouch.ge.client.gui.Display;
import lewiscrouch.ge.client.gui.GuiScreen;
import lewiscrouch.ge.client.gui.GuiTest;
import lewiscrouch.ge.client.gui.Renderer;

public class Game
	implements Renderer
{
	private static Game instance;

	private final String name = "GameEngine";
	private final String version = "1.0";
	private final int targetUPS = 24;

	private boolean running;
	private int updates;
	private int seconds;
	private int currentUPS;

	public static int TILE_SIZE = 16;
	public static int TILE_SCALE = 2;

	private GuiScreen currentScreen;

	public Game()
	{
		Game.instance = this;

		Display.create(this.name, 576, 576);
		Canvas.setRenderer(this);

		this.updates = 0;
		this.seconds = 0;
		this.currentUPS = 0;

		this.currentScreen = new GuiTest();

		this.startGame();
	}

	private void startGame()
	{
		long prev = System.nanoTime();
		long curr;
		long second = 1000000000;
		long udiff = second / this.targetUPS;

		long time = prev;
		int updatesThisSecond = 0;

		while(this.running = true)
		{
			curr = System.nanoTime();
			if(curr - prev >= udiff)
			{
				this.update();
				updatesThisSecond++;
				Canvas.getInstance().repaint();
				prev = curr;
			}

			if(System.nanoTime() - second >= time)
			{
				this.currentUPS = updatesThisSecond;
				updatesThisSecond = 0;
				time = curr;
			}
		}
	}

	private void update()
	{
		if(++this.updates % this.targetUPS == 0) this.seconds++;

		if(this.currentScreen != null) this.currentScreen.update();
	}

	public void render(Graphics2D gfx)
	{
		if(this.currentScreen != null) this.currentScreen.render(gfx);

		Canvas.drawStrWithShadow(gfx, this.name + " v" + this.version, 16, 32, Color.WHITE);
		gfx.setFont(new Font("Arial", Font.PLAIN, 12));
		Canvas.drawStrWithShadow(gfx, this.currentUPS + " FPS", 16, 48, new Color(250, 250, 250));
		gfx.setFont(Canvas.DEFAULT_FONT);
	}

	public String getName()
	{
		return this.name;
	}

	public String getVersion()
	{
		return this.version;
	}

	public int getTargetUPS()
	{
		return this.targetUPS;
	}

	public boolean isRunning()
	{
		return this.running;
	}

	public long getUpdates()
	{
		return this.updates;
	}

	public int getSecondsRunning()
	{
		return this.seconds;
	}

	public int getCurrentUPS()
	{
		return this.currentUPS;
	}

	public GuiScreen getCurrentScreen()
	{
		return this.currentScreen;
	}

	public void setCurrentScreen(GuiScreen currentScreen)
	{
		this.currentScreen = currentScreen;
	}

	public static Game getInstance()
	{
		return Game.instance;
	}
}
