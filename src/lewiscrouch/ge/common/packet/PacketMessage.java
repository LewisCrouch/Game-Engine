package lewiscrouch.ge.common.packet;

public class PacketMessage
	implements IPacket
{
	private static final long serialVersionUID = 1104412429091364348L;

	private String sender;
	private String message;

	public PacketMessage(String sender, String message)
	{
		this.sender = sender;
		this.message = message;
	}

	public String getSender()
	{
		return this.sender;
	}

	public String getMessage()
	{
		return this.message;
	}

	@Override
	public String getKey()
	{
		return "msg";
	}
}
