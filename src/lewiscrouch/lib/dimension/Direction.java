package lewiscrouch.lib.dimension;

/**
 * Used to compare directions and represent a direction as an integer or a string.
 * @author Lewis
 *
 */
public enum Direction
{
	NORTH(0, "North"),
	NORTH_EAST(1, "North East"),
	EAST(2, "East"),
	SOUTH_EAST(3, "South East"),
	SOUTH(4, "South"),
	SOUTH_WEST(5, "South West"),
	WEST(6, "West"),
	NORTH_WEST(7, "North West");

	private int value;
	private String label;

	private Direction(int value, String label)
	{
		this.value = value;
		this.label = label;
	}

	/**
	 * Returns a Direction with the value matching the specified value.
	 * If no Direction with said value is found, returns null.
	 * @param value
	 * @return
	 */
	public static Direction getDirectionFromValue(int value)
	{
		if(Direction.NORTH.value == value) return Direction.NORTH;
		if(Direction.EAST.value == value) return Direction.EAST;
		if(Direction.SOUTH.value == value) return Direction.SOUTH;
		if(Direction.WEST.value == value) return Direction.WEST;
		return null;
	}

	/**
	 * Returns a Direction with the label matching the specified label (case-insensitive).
	 * If no Direction with said label is found, returns null.
	 * @param label
	 * @return
	 */
	public static Direction getDirectionFromLabel(String label)
	{
		if(Direction.NORTH.label.equalsIgnoreCase(label)) return Direction.NORTH;
		if(Direction.EAST.label.equalsIgnoreCase(label)) return Direction.EAST;
		if(Direction.SOUTH.label.equalsIgnoreCase(label)) return Direction.SOUTH;
		if(Direction.WEST.label.equalsIgnoreCase(label)) return Direction.WEST;
		return null;
	}

	/**
	 * Compares this Direction with the specified Direction.
	 * If the Directions have the same values, returns true.
	 * Otherwise, returns false.
	 * @param that
	 * @return
	 */
	public boolean compare(Direction that)
	{
		return this.value == that.value;
	}

	/**
	 * Compares this Directions value with the specified value.
	 * If the values match, returns true. Otherwise, returns false.
	 * @param thatValue
	 * @return
	 */
	public boolean compare(int thatValue)
	{
		return this.value == thatValue;
	}

	/**
	 * Compares this Directions label with the specified label (case-insensitive).
	 * If the labels match, returns true. Otherwise, returns false.
	 * @param thatLabel
	 * @return
	 */
	public boolean compare(String thatLabel)
	{
		return this.label.equalsIgnoreCase(thatLabel);
	}

	/**
	 * Returns the value representing this Direction.
	 * @return
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * Returns the label representing this Direction.
	 * @return
	 */
	public String getLabel()
	{
		return this.label;
	}
}
