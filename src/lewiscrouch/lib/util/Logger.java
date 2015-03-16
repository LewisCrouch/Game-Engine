package lewiscrouch.lib.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lewiscrouch.lib.console.Output;

/**
 * Prints a string with a specified level of severity and a timestamp.
 * Also stores all messages.
 * @author Lewis
 *
 */
public class Logger
{
	private static List<String> log;

	public static void display(String tag, String msg)
	{
		String formatted = new SimpleDateFormat("hh:mm:ss").format(new Date()) + " [" + tag + "] " + msg;
		Logger.getLog().add(formatted);
		Output.writeLine(formatted);
	}

	public static void info(String msg)
	{
		Logger.display("INFO", msg);
	}

	public static void err(String msg)
	{
		Logger.display("ERR", msg);
	}

	public static void saveLog(String logFile)
	{
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(logFile));

			for(String msg : Logger.log)
			{
				writer.write(msg);
				writer.newLine();
			}
		}
		catch(Exception ex)
		{
			Logger.err("Failed to save log to '" + logFile + "'!");
		}
		finally
		{
			if(writer != null)
			{
				try
				{
					writer.close();
				}
				catch(Exception ex)
				{
					
				}
			}
		}
	}

	public static List<String> getLog()
	{
		if(Logger.log == null)
		{
			Logger.log = new ArrayList<String>();
		}
		return Logger.log;
	}
}
