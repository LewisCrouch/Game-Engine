package lewiscrouch.ge.common.packet;

public class PacketAlert
	implements IPacket
{
	private static final long serialVersionUID = 3036228410978955695L;

	private String message;

	public PacketAlert(String message)
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
		return "alert";
	}

}
