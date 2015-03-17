package lewiscrouch.ge.common.dimension;

public enum EnumTileEvent
{
	KILL_PLAYER(0),
	INCREASE_SPEED(1),
	DECREASE_SPEED(2),
	CHANGE_MAP(3),
	MOVE_PLAYER(4),
	SOLIDIFY(5),
	SWIM(6),
	TELEPORT(7),
	BREAK(8),
	MSG(9),
	MOVE(10);

	private int value;

	private EnumTileEvent(int value)
	{
		this.value = value;
	}

	public boolean equalTo(int value)
	{
		return this.value == value;
	}

	public boolean equalTo(EnumTileEvent that)
	{
		return this.equalTo(that.value);
	}
}
