package lewiscrouch.games.pro.server;

import lewiscrouch.games.pro.common.Session;

public class ServerSession extends Session
{
	private String password;

	public ServerSession(String key, String username, String password)
	{
		super(key, username);
		this.password = password;
	}

	public String getPassword()
	{
		return this.password;
	}
}
