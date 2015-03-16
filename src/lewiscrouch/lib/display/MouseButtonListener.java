package lewiscrouch.lib.display;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseButtonListener extends MouseAdapter
{
	private static boolean lmbDown = false;
	private static boolean mmbDown = false;
	private static boolean rmbDown = false;

	@Override
	public void mouseClicked(MouseEvent ev) { }

	@Override
	public void mouseEntered(MouseEvent ev) { }

	@Override
	public void mouseExited(MouseEvent ev) { }

	@Override
	public void mousePressed(MouseEvent ev)
	{
		MouseButtonListener.lmbDown = ev.getButton() == MouseEvent.BUTTON1;
		MouseButtonListener.mmbDown = ev.getButton() == MouseEvent.BUTTON2;
		MouseButtonListener.rmbDown = ev.getButton() == MouseEvent.BUTTON3;
	}

	@Override
	public void mouseReleased(MouseEvent ev)
	{
		MouseButtonListener.lmbDown = false;
		MouseButtonListener.mmbDown = false;
		MouseButtonListener.rmbDown = false;
	}

	public static boolean isLMBDown()
	{
		return MouseButtonListener.lmbDown;
	}

	public static boolean isMMBDown()
	{
		return MouseButtonListener.mmbDown;
	}

	public static boolean isRMBDown()
	{
		return MouseButtonListener.rmbDown;
	}
}
