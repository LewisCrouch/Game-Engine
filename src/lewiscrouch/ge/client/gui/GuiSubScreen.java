package lewiscrouch.ge.client.gui;

public abstract class GuiSubScreen extends GuiScreen
{
	private GuiScreen parent;

	private boolean visible;

	public GuiSubScreen(GuiScreen parent)
	{
		this.parent = parent;
		this.preInit();
		this.init();
	}

	@Override
	public void preInit()
	{
		this.visible = false;
		super.preInit();
	}

	@Override
	public void update()
	{
		if(this.visible) super.update();
	}

	@Override
	public void render()
	{
		if(this.visible) super.render();
	}

	@Override
	public void updateControls()
	{
		if(this.visible) super.updateControls();
	}

	@Override
	public void updateSubScreen()
	{
		if(this.visible) super.updateSubScreen();
	}

	@Override
	public void renderControls()
	{
		if(this.visible) super.renderControls();
	}

	@Override
	public void renderSubScreen()
	{
		if(this.visible) super.renderSubScreen();
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public GuiScreen getParent()
	{
		return this.parent;
	}
}
