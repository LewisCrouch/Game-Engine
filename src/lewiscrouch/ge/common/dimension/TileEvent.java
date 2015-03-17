package lewiscrouch.ge.common.dimension;

public class TileEvent
{
	private int action;
	private String value;

	public TileEvent(int action, String value)
	{
		this.action = action;
		this.value = value;
	}

	public int getAction()
	{
		return this.action;
	}

	public String getValue()
	{
		return this.value;
	}
}
