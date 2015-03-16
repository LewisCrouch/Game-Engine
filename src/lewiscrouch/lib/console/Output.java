package lewiscrouch.lib.console;

import java.util.List;

/**
 * Used to print out data in various formats.
 * @author Lewis
 *
 */
public class Output
{
	/**
	 * Calls System.out.print and passes the str parameter.
	 * @param str
	 */
	public static void write(String str)
	{
		System.out.print(str);
	}

	/**
	 * Calls System.out.println and passes the str parameter.
	 * @param str
	 */
	public static void writeLine(String str)
	{
		System.out.println(str);
	}

	/**
	 * Calls System.err.println and passes the str parameter.
	 * @param str
	 */
	public static void writeErr(String str)
	{
		System.err.println(str);
	}

	/**
	 * Calls System.out.println with no parameter.
	 */
	public static void newLine()
	{
		System.out.println();
	}

	/**
	 * Prints a line with a titled format.
	 * E.g. passing the string "Title" would return "=== Title ===".
	 * @param str
	 */
	public static void writeTitle(String str)
	{
		Output.writeLine("=== " + str + " ===");
	}

	/**
	 * Returns the str indented with a bullet prefix.
	 * E.g. passing the string "Bullet" would return "    - Bullet".
	 * @param str
	 */
	public static void writeBullet(String str)
	{
		Output.writeLine("    - " + str);
	}

	/**
	 * Returns the str indented with n prefixing it.
	 * E.g. passing the string "Numbered" and the number 1 would return "    1. Numbered".
	 * @param n
	 * @param str
	 */
	public static void writeNumbered(int n, String str)
	{
		Output.writeLine("    " + n + ". " + str);
	}

	/**
	 * Returns the str indented with a letter prefixing it.
	 * E.g. passing the string "Lettered" and the number 2 would return "    C. Lettered".
	 * @param n The index of the letter.
	 * @param str
	 */
	public static void writeLettered(int n, String str)
	{
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		String letter = "?";
		if(n > -1 && n < alphabet.length) letter = alphabet[n] + "";
		Output.writeLine("    " + letter + ". " + str);
	}

	/**
	 * Prints each item in the list using the Output.writeBullet method.
	 * @param lst
	 */
	public static void writeList(List<String> lst)
	{
		for(String str : lst)
		{
			Output.writeBullet(str);
		}
	}

	/**
	 * Prints each item in the array using the Output.writeBullet method.
	 * @param arr
	 */
	public static void writeArr(String[] arr)
	{
		for(String str : arr)
		{
			Output.writeBullet(str);
		}
	}

	/**
	 * Prints each item in the list using the Output.writeNumbered method.
	 * @param lst
	 */
	public static void writeNumberedList(List<String> lst)
	{
		for(int i = 0; i < lst.size(); i++)
		{
			Output.writeNumbered((i + 1), lst.get(i));
		}
	}

	/**
	 * Prints each item in the array using the Output.writeNumbered method.
	 * @param arr
	 */
	public static void writeNumberedArr(String[] arr)
	{
		for(int i = 0; i < arr.length; i++)
		{
			Output.writeNumbered((i + 1), arr[i]);
		}
	}

	/**
	 * Prints each item in the list using the Output.writeLettered method.
	 * @param lst
	 */
	public static void writeLetteredList(List<String> lst)
	{
		for(int i = 0; i < lst.size(); i++)
		{
			Output.writeLettered((i + 1), lst.get(i));
		}
	}

	/**
	 * Prints each item in the array using the Output.writeLettered method.
	 * @param arr
	 */
	public static void writeLetteredArr(String[] arr)
	{
		for(int i = 0; i < arr.length; i++)
		{
			Output.writeLettered(i, arr[i]);
		}
	}
}
