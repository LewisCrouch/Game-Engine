package lewiscrouch.lib.display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard
	implements KeyListener
{
	private static boolean[] keyStates;
	private static boolean[] checkKeyStates;
	private static boolean keyDown;
	private static int lastKey;
	private static char lastChar;

	public Keyboard()
	{
		Keyboard.keyStates = new boolean[1024];
		Keyboard.checkKeyStates = new boolean[1024];
		Keyboard.keyDown = false;
		Keyboard.lastKey = -1;
	}

	@Override
	public void keyPressed(KeyEvent ev)
	{
		Keyboard.lastKey = ev.getKeyCode();
		Keyboard.lastChar = ev.getKeyChar();
		if(!Keyboard.checkKeyStates[ev.getKeyCode()]) Keyboard.keyStates[ev.getKeyCode()] = true;
		Keyboard.keyDown = true;
	}

	@Override
	public void keyReleased(KeyEvent ev)
	{
		Keyboard.keyStates[ev.getKeyCode()] = false;
		Keyboard.checkKeyStates[ev.getKeyCode()] = false;
		Keyboard.keyDown = false;
	}

	@Override
	public void keyTyped(KeyEvent ev)
	{
		Keyboard.lastKey = ev.getKeyCode();
		Keyboard.lastChar = ev.getKeyChar();
		if(!Keyboard.checkKeyStates[ev.getKeyCode()]) Keyboard.keyStates[ev.getKeyCode()] = true;
		Keyboard.keyDown = true;
	}

	public static boolean isKeyDown()
	{
		return Keyboard.keyDown;
	}

	public static boolean isKeyDown(int key)
	{
		return Keyboard.keyStates[key];
	}

	public static boolean isKeyPressed(int key)
	{
		if(Keyboard.keyStates[key])
		{
			Keyboard.keyStates[key] = false;
			return true;
		}
		return false;
	}

	public static boolean checkKey(int key)
	{
		if(Keyboard.keyStates[key])
		{
			Keyboard.keyStates[key] = false;
			return Keyboard.checkKeyStates[key] = true;
		}
		return false;
	}

	public static int getLastKey()
	{
		return Keyboard.lastKey;
	}

	public static char getLastChar()
	{
		return Keyboard.lastChar;
	}
}
