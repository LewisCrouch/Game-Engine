package lewiscrouch.lib.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores unique keys and generates unique keys.
 * @author Lewis
 *
 */
public class UniqueKey
{
	public static char[] keyChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	private static List<String> keys;

	/**
	 * Returns a randomly generated unique 16 character key.
	 * @return
	 */
	public static String genKey()
	{
		String key = "";
		while(key.length() < 16)
		{
			key += UniqueKey.keyChars[RandomFactory.getInstance().nextInt(UniqueKey.keyChars.length)];
		}

		for(String k : UniqueKey.getKeys())
		{
			if(key.equals(k))
			{
				return UniqueKey.genKey();
			}
		}

		UniqueKey.getKeys().add(key);
		return key;
	}

	public static List<String> getKeys()
	{
		if(UniqueKey.keys == null)
		{
			UniqueKey.keys = new ArrayList<String>();
		}
		return UniqueKey.keys;
	}
}
