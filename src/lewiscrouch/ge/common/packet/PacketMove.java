package lewiscrouch.ge.common.packet;

public class PacketMove
	implements IPacket
{
	private static final long serialVersionUID = 4915759032683864317L;

	private String drc;

	public PacketMove(String drc)
	{
		this.drc = drc;
	}

	public String getDirection()
	{
		return this.drc;
	}

	@Override
	public String getKey()
	{
		return "move";
	}
}
