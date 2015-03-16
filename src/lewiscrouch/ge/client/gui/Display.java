package lewiscrouch.ge.client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Display extends JFrame
{
	private static final long serialVersionUID = -2911481244113000423L;

	private static Display instance;

	private Display(String title, int width, int height)
	{
		this.setTitle(title);
		//this.setPreferredSize(new Dimension(width, height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int sw = (int) ss.getWidth();
		//int sh = (int) ss.getHeight();
		int x = sw / 2 - width / 2;
		//int y = sh / 2 - height / 2;
		this.setLocation(x, 2);

		this.add(new Canvas(width, height));

		this.pack();
		this.setVisible(true);
	}

	public static void create(String title, int width, int height)
	{
		Display.instance = new Display(title, width, height);
	}

	public static Display getInstance()
	{
		return Display.instance;
	}
}
