package lewiscrouch.ge.client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

public class Mouse
	implements MouseListener, MouseMotionListener
{
	private static int mouseX = -1;
	private static int mouseY = -1;

	private static boolean lmbDown = false;
	private static boolean mmbDown = false;
	private static boolean rmbDown = false;

	@Override
	public void mouseDragged(MouseEvent ev) { }

	@Override
	public void mouseMoved(MouseEvent ev)
	{
		Mouse.mouseX = ev.getX();
		Mouse.mouseY = ev.getY();
	}

	@Override
	public void mouseClicked(MouseEvent ev)
	{
		Mouse.lmbDown = SwingUtilities.isLeftMouseButton(ev);
		Mouse.mmbDown = SwingUtilities.isMiddleMouseButton(ev);
		Mouse.rmbDown = SwingUtilities.isRightMouseButton(ev);
	}

	@Override
	public void mouseEntered(MouseEvent ev)
	{
		Mouse.mouseX = ev.getX();
		Mouse.mouseY = ev.getY();
	}

	@Override
	public void mouseExited(MouseEvent ev)
	{
		Mouse.mouseX = ev.getX();
		Mouse.mouseY = ev.getY();
	}

	@Override
	public void mousePressed(MouseEvent ev)
	{
		Mouse.lmbDown = SwingUtilities.isLeftMouseButton(ev);
		Mouse.mmbDown = SwingUtilities.isMiddleMouseButton(ev);
		Mouse.rmbDown = SwingUtilities.isRightMouseButton(ev);
	}

	@Override
	public void mouseReleased(MouseEvent ev)
	{
		Mouse.lmbDown = false;
		Mouse.mmbDown = false;
		Mouse.rmbDown = false;
	}

	public static int getMouseX()
	{
		return Mouse.mouseX;
	}

	public static int getMouseY()
	{
		return Mouse.mouseY;
	}

	public static boolean isLMBDown()
	{
		return Mouse.lmbDown;
	}

	public static boolean isMMBDown()
	{
		return Mouse.mmbDown;
	}

	public static boolean isRMBDown()
	{
		return Mouse.rmbDown;
	}
}
