package lewiscrouch.games.pro.common;

/**
 * Stores information about a server.
 * @author Lewis
 *
 */
public class ServerInfo
{
	private String host;
	private int port;

	public ServerInfo(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public String getHost()
	{
		return this.host;
	}

	public int getPort()
	{
		return this.port;
	}
}
