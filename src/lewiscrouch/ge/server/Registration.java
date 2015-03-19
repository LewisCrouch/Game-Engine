package lewiscrouch.ge.server;

import java.util.Date;

/**
 * Stores a players registration credentials.
 * @author Lewis
 *
 */
public class Registration
{
	private String username;
	private String password;
	private String sessionStartDate;

	public Registration(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.sessionStartDate = new Date().toString();
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPassword()
	{
		return this.password;
	}

	public String getSessionStartDate()
	{
		return this.sessionStartDate;
	}
}
