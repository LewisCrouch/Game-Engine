package lewiscrouch.ge.common.packet;

public class PacketLogout
	implements IPacket
{
	private static final long serialVersionUID = 5646395056625635805L;

	@Override
	public String getKey()
	{
		return "logout";
	}

}
