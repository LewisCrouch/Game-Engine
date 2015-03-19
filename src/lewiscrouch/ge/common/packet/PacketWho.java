package lewiscrouch.ge.common.packet;

public class PacketWho
	implements IPacket
{
	private static final long serialVersionUID = 197157636654559972L;

	@Override
	public String getKey()
	{
		return "who";
	}
}
