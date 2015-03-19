package lewiscrouch.ge.common.packet;

public class PacketRegister
	implements IPacket
{
	private static final long serialVersionUID = -9192928163378428153L;

	private String username;
	private String password;

	public PacketRegister(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPassword()
	{
		return this.password;
	}

	@Override
	public String getKey()
	{
		return "register";
	}
}
