package lewiscrouch.ge.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lewiscrouch.ge.client.game.Game;
import lewiscrouch.lib.display.Display;
import lewiscrouch.lib.display.Mouse;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RImage;
import lewiscrouch.lib.resource.ImageRegister;

public abstract class GuiScreen
{
	private static final int DEFAULT_BACKGROUND_ID = ImageRegister.registerImage("brick_floor.png");

	private List<GuiControl> controls;
	private GuiSubScreen subScreen;

	private boolean initOnResize;

	public GuiScreen()
	{
		this.preInit();
		this.init();
	}

	public void preInit()
	{
		this.controls = new ArrayList<GuiControl>();
		this.initOnResize = true;
	}

	public void update()
	{
		this.updateControls();
		this.updateSubScreen();
		this.updateScreen();
	}

	public void render()
	{
		this.renderScreen();
		this.renderControls();
		this.renderSubScreen();
	}

	public abstract void init();
	public abstract void updateScreen();
	public abstract void renderScreen();

	public void onResize()
	{
		this.onScreenResize();
		if(this.initOnResize)
		{
			this.preInit();
			this.init();
		}
	}
	public void onScreenResize() { }
	public void actionPerformed(int id) { }

	public void addControl(GuiControl control)
	{
		control.setParent(this);
		this.controls.add(control);
	}

	public void updateControls()
	{
		Iterator<GuiControl> it = this.controls.iterator();
		while(it.hasNext())
		{
			GuiControl control = it.next();

			if(Mouse.isLMBDown())
			{
				if(control.isHovered())
				{
					if(!GuiControl.mouseDownFromPrev)
					{
						if(!control.isMouseDown())
						{
							control.onMouseClick();
						}
						control.setMouseDown(true);
						control.onMouseDown();

						GuiControl.mouseDownFromPrev = true;
					}
				}
			}
			else
			{
				if(control.isMouseDown())
				{
					control.setMouseDown(false);
					control.onMouseUp();
				}

				GuiControl.mouseDownFromPrev = false;
			}

			control.update();
		}
	}

	public void changeFocus(int id)
	{
		Iterator<GuiControl> it = this.controls.iterator();
		while(it.hasNext())
		{
			GuiControl ctrl = it.next();
			if(ctrl.getID() != id) ctrl.removeFocus();
		}
	}

	public void updateSubScreen()
	{
		if(this.subScreen != null) this.subScreen.update();
	}

	public void renderControls()
	{
		Iterator<GuiControl> it = this.controls.iterator();
		while(it.hasNext())
		{
			GuiControl control = it.next();
			control.render();
		}
	}

	public void renderSubScreen()
	{
		if(this.subScreen != null) this.subScreen.render();
	}

	public void renderDefaultBackground()
	{
		for(int x = 0; x < (Display.getInnerWidth() / 64) + 1; x++)
		{
			for(int y = 0; y < (Display.getInnerHeight() / 64) + 1; y++)
			{
				RImage ri = new RImage(x * 64, y * 64, GuiScreen.DEFAULT_BACKGROUND_ID);
				ri.setWidth(64);
				ri.setHeight(64);
				RenderQueue.add(ri);
			}
		}
	}

	public void displayScreen(GuiScreen screen)
	{
		Game.getInstance().displayScreen(screen);
	}

	public int getWidth()
	{
		return Display.getInnerWidth();
	}

	public int getHeight()
	{
		return Display.getInnerHeight();
	}

	public GuiSubScreen getSubScreen()
	{
		return this.subScreen;
	}

	public void setSubScreen(GuiSubScreen subScreen)
	{
		this.subScreen = subScreen;
	}

	public boolean initOnResize()
	{
		return this.initOnResize;
	}

	public void setInitOnResize(boolean initOnResize)
	{
		this.initOnResize = initOnResize;
	}
}
