package lewiscrouch.ge.server;

import lewiscrouch.ge.common.Session;

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
