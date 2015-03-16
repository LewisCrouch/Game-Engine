package lewiscrouch.ge.client.gui;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import lewiscrouch.lib.display.Keyboard;
import lewiscrouch.lib.display.RenderQueue;
import lewiscrouch.lib.display.renderable.RRectangle;
import lewiscrouch.lib.display.renderable.RString;

public class GuiTextField extends GuiControl
{
	public static final char[] ALLOWED_CHARACTERS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"£$%^&*()-=_+[]{};'#:@~,./<>?`".toCharArray();

	private String text;
	private int maxLength;
	private int firstTypeDelay;
	private int typeDelay;
	private boolean first;
	private int waited;
	private char lastChar;
	private boolean selected;

	public GuiTextField(int id, int x, int y, int width)
	{
		super(id, x, y, width, 32, Color.BLACK);
	}

	@Override
	public void init()
	{
		this.text = "";
		this.maxLength = 24;
		this.firstTypeDelay = 10;
		this.typeDelay = 2;
		this.first = true;
		this.waited = 0;
		this.lastChar = (char) -1;
		this.selected = false;
	}

	@Override
	public void update()
	{
		if(!this.isFocused()) return;

		if(this.waited > 0) this.waited--;

		if(!Keyboard.isKeyDown())
		{
			this.first = true;
			return;
		}

		if(Keyboard.isKeyPressed(KeyEvent.VK_ENTER))
		{
			if(this.getText() != "")
			{
				this.onSubmit(this.text);
				if(this.hasParent()) this.getParent().actionPerformed(this.getID());
			}
			return;
		}

		if(Keyboard.isKeyPressed(KeyEvent.VK_BACK_SPACE) || Keyboard.isKeyPressed(KeyEvent.VK_DELETE))
		{
			if(this.selected) this.text = "";

			if(this.text != "")
			{
				if(this.text.length() == 1)
				{
					this.text = "";
				}
				else
				{
					this.text = (this.text.substring(0, this.text.length() - 1));
				}
			}

			this.selected = false;
		}

		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL) && Keyboard.isKeyPressed(KeyEvent.VK_A))
		{
			this.selected = true;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL) && Keyboard.isKeyPressed(KeyEvent.VK_X))
		{
			if(this.selected)
			{
				this.setClipboard(this.text);
				this.text = "";
				this.selected = false;
			}
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL) && Keyboard.isKeyPressed(KeyEvent.VK_C))
		{
			this.setClipboard(this.text);
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL) && Keyboard.isKeyPressed(KeyEvent.VK_V))
		{
			this.text += this.getClipboard();
			if(this.selected) this.text = this.getClipboard();
			if(this.text.length() > this.maxLength) this.text = this.text.substring(0, this.maxLength);
			this.selected = false;
		}

		if(this.text.length() < this.maxLength - 1)
		{
			for(char c : GuiTextField.ALLOWED_CHARACTERS)
			{
				if(("" + Keyboard.getLastChar()).equalsIgnoreCase("" + c))
				{
					if(this.waited == 0 || this.lastChar != Keyboard.getLastChar())
					{
						if(this.selected) this.text = "";
						String s = c + "";
						if(!Keyboard.isKeyDown(KeyEvent.VK_SHIFT)) s = s.toLowerCase();
						this.text = this.text + s;
						this.waited = this.typeDelay;
						if(this.first) this.waited = this.firstTypeDelay;
						this.first = false;
						this.lastChar = Keyboard.getLastChar();
						this.selected = false;
					}
				}
			}
		}
	}

	@Override
	public void render()
	{
		super.render();

		if(this.selected) RenderQueue.add(new RRectangle(this.getX() + 8, this.getY() + 8, this.getWidth() - 16, this.getHeight() - 16, new Color(0, 80, 180)));

		this.getLabel().setText(this.text);

		this.getLabel().setX(this.getX() + 8);
		this.getLabel().setY(this.getY() + 1 + (this.getHeight() / 2 - this.getLabel().getStringHeight() / 2));
		RenderQueue.add(this.getLabel());

		Color c = new Color(1, 1, 1, 0.5F);
		if(this.isFocused()) c = Color.WHITE;
		RenderQueue.add(new RRectangle(this.getX(), this.getY(), this.getWidth(), 2, c));
		RenderQueue.add(new RRectangle(this.getX(), this.getY(), 2, this.getHeight(), c));
		RenderQueue.add(new RRectangle(this.getX() + this.getWidth() - 2, this.getY(), 2, this.getHeight(), c));
		RenderQueue.add(new RRectangle(this.getX(), this.getY() + this.getHeight() - 2, this.getWidth(), 2, c));
	}

	public void onSubmit(String text)
	{
		
	}

	public void setClipboard(String str)
	{
		StringSelection strSel = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSel, null);
	}

	public String getClipboard()
	{
		try
		{
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		}
		catch(HeadlessException ex)
		{
			ex.printStackTrace();
		}
		catch(UnsupportedFlavorException ex)
		{
			ex.printStackTrace();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public int getStringWidth()
	{
		return new RString(0, 0, this.text).getStringWidth();
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getMaxLength()
	{
		return this.maxLength;
	}

	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}

	public boolean isSelected()
	{
		return this.selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
}
