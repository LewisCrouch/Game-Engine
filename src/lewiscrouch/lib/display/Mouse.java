package lewiscrouch.lib.display;

public class Mouse
{
	public static boolean isLMBDown()
	{
		return MouseButtonListener.isLMBDown();
	}

	public static boolean isMMBDown()
	{
		return MouseButtonListener.isMMBDown();
	}

	public static boolean isRMBDown()
	{
		return MouseButtonListener.isRMBDown();
	}

	public static int getMouseX()
	{
		return MouseMovementListener.getMouseX();
	}

	public static int getMouseY()
	{
		return MouseMovementListener.getMouseY();
	}
}
