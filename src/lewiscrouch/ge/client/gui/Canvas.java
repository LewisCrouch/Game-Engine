package lewiscrouch.ge.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Canvas extends JPanel
{
	private static final long serialVersionUID = -22657661198124280L;

	public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);

	private static Canvas instance;

	private Renderer renderer;

	public Canvas(int width, int height)
	{
		Canvas.instance = this;

		this.setPreferredSize(new Dimension(width, height));

		Mouse mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addKeyListener(new Keyboard());

		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.requestFocus();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D gfx = (Graphics2D) g;

		gfx.setFont(Canvas.DEFAULT_FONT);
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		gfx.setColor(Color.BLACK);
		gfx.fill(new Rectangle2D.Float(0, 0, Canvas.width(), Canvas.height()));

		if(this.renderer != null) this.renderer.render(gfx);
	}

	public static void drawRect(Graphics2D gfx, float x, float y, float w, float h, Color color)
	{
		gfx.setColor(color);
		gfx.fill(new Rectangle2D.Float(x, y, w, h));
	}

	public static void drawEllipse(Graphics2D gfx, float x, float y, float w, float h, Color color)
	{
		gfx.setColor(color);
		gfx.fill(new Ellipse2D.Float(x, y, w, h));
	}

	public static void drawStr(Graphics2D gfx, String str, float x, float y, Color color)
	{
		gfx.setColor(color);
		gfx.drawString(str, x, y);
	}

	public static void drawStrWithShadow(Graphics2D gfx, String str, float x, float y, float dx, float dy, Color color, Color scolor)
	{
		gfx.setColor(scolor);
		gfx.drawString(str, x + dx, y + dy);
		gfx.setColor(color);
		gfx.drawString(str, x, y);
	}

	public static void drawStrWithShadow(Graphics2D gfx, String str, float x, float y, Color color)
	{
		gfx.setColor(new Color(0, 0, 0, 0.5F));
		gfx.drawString(str, x + 1, y + 1);
		gfx.setColor(color);
		gfx.drawString(str, x, y);
	}

	public static void drawStrWithOutline(Graphics2D gfx, String str, float x, float y, float strength, Color color, Color ocolor)
	{
		gfx.setColor(ocolor);
		gfx.drawString(str, x - strength, y);
		gfx.drawString(str, x + strength, y);
		gfx.drawString(str, x, y - strength);
		gfx.drawString(str, x, y + strength);
		gfx.drawString(str, x - strength, y - strength);
		gfx.drawString(str, x + strength, y + strength);
		gfx.drawString(str, x - strength, y + strength);
		gfx.drawString(str, x + strength, y - strength);
		gfx.setColor(color);
		gfx.drawString(str, x, y);
	}

	public static void drawImage(Graphics2D gfx, int i, float x, float y, float width, float height)
	{
		gfx.drawImage(ImageRegister.getImage(i), (int) x, (int) y, (int) width, (int) height, null); 
	}

	public static void drawImage(Graphics2D gfx, int i, float x, float y)
	{
		gfx.drawImage(ImageRegister.getImage(i), (int) x, (int) y, null);
	}

	public static int width()
	{
		return (int) Canvas.instance.getSize().getWidth();
	}

	public static int height()
	{
		return (int) Canvas.instance.getSize().getHeight();
	}

	public static void setRenderer(Renderer renderer)
	{
		Canvas.instance.renderer = renderer;
	}

	public static Canvas getInstance()
	{
		return Canvas.instance;
	}
}
