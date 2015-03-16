package lewiscrouch.ge.client.game;

import lewiscrouch.ge.client.gui.GuiMainMenu;
import lewiscrouch.ge.client.gui.GuiScreen;
import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.ResizeListener;
import lewiscrouch.lib.util.TimeConverter;

public class Game
	implements ResizeListener
{
	public static int SCALE = 5;
	public static int SIZE = 16;
	public static int SCALED_SIZE() { return Game.SIZE * Game.SCALE; }

	private static Game instance;

	private final String name = "Game Test";
	private final String version = "1.0";
	private final int targetUPS = 24;

	private boolean running;
	private int updates;
	private int seconds;
	private int currentUPS;

	private GuiScreen currentScreen;

	public Game()
	{
		Game.instance = this;

		Display.create(this.name, 576, 576);
		Display.getInstance().addResizeListener(this);

		this.updates = 0;
		this.seconds = 0;
		this.currentUPS = 0;

		this.displayScreen(new GuiMainMenu());

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
				this.render();
				Display.getInstance().repaintContent();
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
		if(++this.updates % this.targetUPS == 0)
		{
			this.seconds++;
		}

		if(this.currentScreen != null)
		{
			this.currentScreen.update();
		}
	}

	public void render()
	{
		if(this.currentScreen != null)
		{
			this.currentScreen.render();
		}

//		RString rs = new RString(2, 2, this.name + " v" + this.version);
//		rs.setFont("Yu Gothic");
//		rs.setY(Display.getInnerHeight() - rs.getStringHeight() + 2);
//		rs.setColor(new Color(0, 0, 0));
//		rs.setShadow(false);
//		rs.setStyle(java.awt.Font.BOLD);
//		RenderQueue.add(rs);
	}

	@Override
	public void onResize()
	{
		if(Game.SCALED_SIZE() * 9 < Display.getInnerWidth() || Game.SCALED_SIZE() * 9 < Display.getInnerHeight())
		{
			Game.SCALE++;
		}
		if(Game.SCALED_SIZE() * 9 > Display.getInnerWidth() || Game.SCALED_SIZE() * 9 > Display.getInnerHeight())
		{
			Game.SCALE--;
		}

		if(this.currentScreen != null) this.currentScreen.onResize();
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

	public String getTimeRunning()
	{
		return TimeConverter.format(this.seconds);
	}

	public int getCurrentUPS()
	{
		return this.currentUPS;
	}

	public GuiScreen getCurrentScreen()
	{
		return this.currentScreen;
	}

	public void displayScreen(GuiScreen currentScreen)
	{
		this.currentScreen = currentScreen;
	}

	public static Game getInstance()
	{
		return Game.instance;
	}
}
