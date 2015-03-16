package lewiscrouch.ge.client.gui;

import java.awt.Color;

import lewiscrouch.lib.display.Mouse;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RRectangle;
import lewiscrouch.lib.display.renderable.RString;

public abstract class GuiControl
{
	protected static boolean mouseDownFromPrev = false;

	private GuiScreen parent;

	private int id;

	private boolean focused;
	private boolean focusable;
	private boolean visible;

	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	private RString label;

	private boolean mouseDown;

	public GuiControl(int id, int x, int y, int width, int height, Color color)
	{
		this.id = id;
		this.focused = false;
		this.focusable = false;
		this.visible = true;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.label = new RString(this.x, this.y, "");

		this.mouseDown = false;

		this.init();
	}

	public void init()
	{
		
	}

	public void update()
	{
		if(!this.visible) return;
	}

	public void render()
	{
		if(!this.visible) return;
		RenderQueue.add(new RRectangle(this.x, this.y, this.width, this.height, this.color));
	}

	public void onMouseDown() { }
	public void onMouseClick() { }
	public void onMouseUp() { }
	public void onMouseHover() { }

	public boolean isMouseDown()
	{
		return this.mouseDown;
	}

	public void setMouseDown(boolean mouseDown)
	{
		this.giveFocus();
		this.mouseDown = mouseDown;
	}

	public boolean isHovered()
	{
		int mx = Mouse.getMouseX();
		int my = Mouse.getMouseY();
		return (mx > this.getX() && mx < this.getX() + this.getWidth()) && (my > this.getY() && my < this.getY() + this.getHeight());
	}

	public int getID()
	{
		return this.id;
	}

	public boolean isFocused()
	{
		return this.focused;
	}

	public void giveFocus()
	{
		this.focused = true;
		this.parent.changeFocus(this.id);
	}

	public void removeFocus()
	{
		this.focused = false;
	}

	public boolean isFocusable()
	{
		return this.focusable;
	}

	public void setFocusable(boolean focusable)
	{
		this.focusable = focusable;
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public int getX()
	{
		return this.x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return this.width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public RString getLabel()
	{
		return this.label;
	}

	public void setLabel(RString label)
	{
		this.label = label;
	}

	public boolean hasLabel()
	{
		if(this.label.getText() == null) return false;
		if(this.label.getText() == "") return false;
		return true;
	}

	public boolean hasParent()
	{
		return this.parent != null;
	}

	public GuiScreen getParent()
	{
		return this.parent;
	}

	public void setParent(GuiScreen parent)
	{
		this.parent = parent;
	}
}
