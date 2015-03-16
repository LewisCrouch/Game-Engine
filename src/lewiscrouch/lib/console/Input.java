package lewiscrouch.lib.console;

import java.util.Scanner;

import lewiscrouch.lib.validation.NumberConstraints;
import lewiscrouch.lib.validation.StringConstraints;

/**
 * Used to read input in various formats.
 * @author Lewis
 *
 */
public class Input
{
	private static Scanner scanner;

	/**
	 * Returns scanner.nextLine
	 * @return
	 */
	public static String readStr()
	{
		return Input.scanner().nextLine();
	}

	/**
	 * Performs a loop. During each iteration, reads input and validates it with the string constraints.
	 * If valid, return. Otherwise, output err and continue to next iteration.
	 * @param sc
	 * @param err
	 * @return
	 */
	public static String forceReadStr(StringConstraints sc, String err)
	{
		String str = null;
		boolean valid = false;
		while(!valid)
		{
			str = Input.readStr();
			if(!(valid = sc.validate(str))) Output.writeLine(err);
		}
		return str;
	}

	/**
	 * Performs a loop. During each iteration, reads input and validates that it's a number.
	 * If valid, return. Otherwise, output err and continue to next iteration.
	 * @param err
	 * @return
	 */
	public static int forceReadNum(String err)
	{
		int n = -1;
		boolean valid = false;
		while(!valid)
		{
			try
			{
				String s = Input.readStr();
				n = Integer.parseInt(s);
				valid = true;
			}
			catch(Exception ex) { }

			if(!valid) Output.writeLine(err);
		}
		return n;
	}

	/**
	 * Performs a loop. During each iteration, reads input and validates it with the number constraints.
	 * If valid, return. Otherwise, output err and continue to next iteration.
	 * @param nc
	 * @param err
	 * @return
	 */
	public static int forceReadNum(NumberConstraints nc, String err)
	{
		int n = -1;
		boolean valid = false;
		while(!valid)
		{
			try
			{
				String s = Input.readStr();
				n = Integer.parseInt(s);
				valid = nc.validate(n);
			}
			catch(Exception ex) { }

			if(!valid) Output.writeLine(err);
		}
		return n;
	}

	public static Scanner scanner()
	{
		if(Input.scanner == null) Input.scanner = new Scanner(System.in);
		return Input.scanner;
	}
}