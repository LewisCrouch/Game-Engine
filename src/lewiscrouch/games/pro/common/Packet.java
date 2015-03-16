package lewiscrouch.games.pro.common;

import java.io.Serializable;

public class Packet
	implements Serializable
{
	private static final long serialVersionUID = 3072834644031679125L;

	private String key;
	private Object value;

	public Packet(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}

	public void processPacket()
	{
		GlobalPacketHandler.getPacketHandler().handlePacket(this);
	}

	public String getKey()
	{
		return this.key;
	}

	public Object getValue()
	{
		return this.value;
	}
}
