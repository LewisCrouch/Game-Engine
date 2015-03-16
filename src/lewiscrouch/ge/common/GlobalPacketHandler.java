package lewiscrouch.ge.common;

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
	public void handlePacket(Packet packet) { }
}
