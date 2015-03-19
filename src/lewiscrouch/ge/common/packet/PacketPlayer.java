package lewiscrouch.ge.common.packet;

import lewiscrouch.ge.common.dimension.Player;

public class PacketPlayer
	implements IPacket
{
	private static final long serialVersionUID = -387657393894702062L;

	private Player player;

	public PacketPlayer(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	@Override
	public String getKey()
	{
		return "player";
	}
}
