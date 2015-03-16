package lewiscrouch.lib.display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMovementListener extends MouseMotionAdapter
{
	private static int mouseX = -1;
	private static int mouseY = -1;

	@Override
	public void mouseDragged(MouseEvent ev)
	{
		MouseMovementListener.mouseX = ev.getX();
		MouseMovementListener.mouseY = ev.getY();
	}

	@Override
	public void mouseMoved(MouseEvent ev)
	{
		MouseMovementListener.mouseX = ev.getX();
		MouseMovementListener.mouseY = ev.getY();
	}

	public static int getMouseX()
	{
		return MouseMovementListener.mouseX;
	}

	public static int getMouseY()
	{
		return MouseMovementListener.mouseY;
	}
}
