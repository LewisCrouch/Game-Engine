package lewiscrouch.lib.util;

public class TimeConverter
{
	public static String format(int totalSeconds)
	{
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	public int totalSecondsToHours(int totalSeconds)
	{
		return totalSeconds / 3600;
	}

	public int totalSecondsToMinutes(int totalSeconds)
	{
		return (totalSeconds % 3600) / 60;
	}

	public int totalSecondsToSeconds(int totalSeconds)
	{
		return (totalSeconds % 3600) / 60;
	}
}
