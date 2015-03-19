package lewiscrouch.ge.common.packet;

public class PacketPlayerMessage
	implements IPacket
{
	private static final long serialVersionUID = 8495837989899963287L;

	private String message;

	public PacketPlayerMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return this.message;
	}

	@Override
	public String getKey()
	{
		return "player_message";
	}

}
