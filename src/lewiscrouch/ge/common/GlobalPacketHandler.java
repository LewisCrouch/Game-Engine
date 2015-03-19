package lewiscrouch.ge.common;

import lewiscrouch.ge.common.packet.IPacket;

public class GlobalPacketHandler
	implements IPacketHandler
{
	private static IPacketHandler packetHandler = null;

	public static GlobalPacketHandler instance = new GlobalPacketHandler();

	private GlobalPacketHandler()
	{
		GlobalPacketHandler.packetHandler = this;
	}

	public static void setPacketHandler(IPacketHandler packetHandler)
	{
		GlobalPacketHandler.packetHandler = packetHandler;
	}

	public static IPacketHandler getPacketHandler()
	{
		return GlobalPacketHandler.packetHandler;
	}

	@Override
	public void handlePacket(IPacket packet) { }
}
