package lewiscrouch.lib.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import lewiscrouch.lib.display.renderable.Renderable;
import lewiscrouch.lib.geom.Size;

public class Display extends JFrame
{
	private static final long serialVersionUID = 5147530992917453893L;

	private static Display instance;

	private JPanel content;
	private List<ResizeListener> resizeListeners;
	private Size prevSize;

	public Display(String title, int innerWidth, int innerHeight)
	{
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int sw = (int) ss.getWidth();
		//int sh = (int) ss.getHeight();
		int x = (sw / 2) - (innerWidth / 2);
		//int y = (sh / 2) - (innerHeight / 2);
		int y = 12;
		this.setLocation(x, y);

		this.resizeListeners = new ArrayList<ResizeListener>();
		this.addComponentListener(new ComponentAdapter() 
		{  
			public void componentResized(ComponentEvent ev)
			{
				Iterator<ResizeListener> it = resizeListeners.iterator();
				while(it.hasNext())
				{
					it.next().onResize();
				}
			}
		});

		this.content = new JPanel()
		{
			private static final long serialVersionUID = -1770799654006048449L;

			@Override
			public void paint(Graphics g)
			{
				Graphics2D gfx = (Graphics2D) g;

				gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				gfx.setColor(Color.BLACK);
				gfx.fillRect(0, 0, getWidth(), getHeight());

				RenderQueue.lock();
				Iterator<Renderable> it = RenderQueue.iterator();
				while(it.hasNext())
				{
					Renderable r = it.next();
					r.render(gfx);

					try
					{
						it.remove();
					}
					catch(Exception ex)
					{
						System.err.println("Error removing renderable! " + r);
						ex.printStackTrace();
					}
				}
				RenderQueue.release();
			}
		};
		this.content.setPreferredSize(new Dimension(innerWidth, innerHeight));
		this.content.addMouseMotionListener(new MouseMovementListener());
		this.content.addMouseListener(new MouseButtonListener());
		this.addKeyListener(new Keyboard());
		this.add(this.content);

		this.content.requestFocusInWindow();

		this.pack();

		this.prevSize = new Size(innerWidth, innerHeight);

		this.setVisible(true);
	}

	public void addResizeListener(ResizeListener resizeListener)
	{
		this.resizeListeners.add(resizeListener);
	}

	public void repaintContent()
	{
		this.content.repaint();
	}

	public static void create(String title, int innerWidth, int innerHeight)
	{
		Display.instance = new Display(title, innerWidth, innerHeight);
	}

	public static int getInnerX()
	{
		return Display.instance.content.getX();
	}

	public static int getInnerY()
	{
		return Display.instance.content.getY();
	}

	public static int getInnerWidth()
	{
		return Display.instance.content.getWidth();
	}

	public static int getInnerHeight()
	{
		return Display.instance.content.getHeight();
	}

	public void setInnerSize(int width, int height)
	{
		this.content.setSize(width, height);
		this.pack();
	}

	public Size getPreviousInnerSize()
	{
		return this.prevSize;
	}

	public void setPreviousInnerSize(Size prevSize)
	{
		this.prevSize = prevSize;
	}

	public static Display getInstance()
	{
		return Display.instance;
	}
}

class DisplayNotCreatedException extends Exception
{
	private static final long serialVersionUID = 7884198921959592900L;

	private String message;

	public DisplayNotCreatedException()
	{
		this.message = "Display not created!";
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}
}