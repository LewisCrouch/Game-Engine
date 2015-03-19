package lewiscrouch.ge.common.packet;

public class PacketLogin
	implements IPacket
{
	private static final long serialVersionUID = -3154906248164983597L;

	private String username;
	private String password;

	public PacketLogin(String username, String password)
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
		return "login";
	}
}
