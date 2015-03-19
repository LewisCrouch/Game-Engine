package lewiscrouch.ge.client;

import lewiscrouch.ge.common.packet.IPacket;

public interface IServerListener
{
	public void receivePacket(IPacket packet);
}
