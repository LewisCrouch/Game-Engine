package lewiscrouch.ge.common;

import java.util.Date;

/**
 * Stores information about a client.
 * @author Lewis
 *
 */
public class Session
{
	private String key;
	private String username;
	private String sessionStartDate;

	public Session(String key, String username)
	{
		this.key = key;
		this.username = username;
		this.sessionStartDate = new Date().toString();
	}

	public String getKey()
	{
		return this.key;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getSessionStartDate()
	{
		return this.sessionStartDate;
	}

	/**
	 * Returns the key.
	 */
	@Override
	public String toString()
	{
		return this.key;
	}
}
