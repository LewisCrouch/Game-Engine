package lewiscrouch.ge.client;

import lewiscrouch.ge.common.Packet;
import lewiscrouch.ge.common.ServerInfo;
import lewiscrouch.lib.console.Input;
import lewiscrouch.lib.util.Logger;
import lewiscrouch.lib.validation.StringConstraints;

public class ClientLauncher
{
	public static void main(String[] args)
	{
		ServerInfo serverInfo = new ServerInfo("localhost", 1337);
		Client client = new Client(serverInfo);
		client.start();

		Logger.info("Do you have an account? (Y/N)");
		StringConstraints sc = new StringConstraints();
		sc.setAllowedChars("yYnN".toCharArray());
		sc.setMinLength(1);
		sc.setMaxLength(1);
		boolean registered = Input.forceReadStr(sc, "Enter Y (Yes) or N (No)!").equalsIgnoreCase("y") ? true : false;

		if(!registered)
		{
			Logger.info("Register for an account.");

			Logger.info("Enter username:");
			sc = new StringConstraints();
			sc.setMinLength(3);
			sc.setMaxLength(12);
			sc.setAllowedChars("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".toCharArray());
			String username = Input.forceReadStr(sc, "Must be between 6 and 12 characters (inclusive), and only contain 'A-Z', '0-9' and '_'!");

			Logger.info("Enter password:");
			sc = new StringConstraints();
			sc.setMinLength(6);
			String password = Input.forceReadStr(sc, "Must be at least 6 characters long!");

			client.sendPacket(new Packet("register", username + ":" + password));
		}
		else
		{
			Logger.info("Login to server.");

			Logger.info("Enter username:");
			String username = Input.readStr();

			Logger.info("Enter password:");
			String password = Input.readStr();

			client.sendPacket(new Packet("login", username + ":" + password));
		}

		while(true)
		{
			String msg = Input.readStr();
			if(msg.startsWith("/"))
			{
				String cmd = msg.substring(1);
				if(cmd.equalsIgnoreCase("who"))
				{
					client.sendPacket(new Packet("who", ""));
				}
				else if(cmd.equalsIgnoreCase("logout"))
				{
					client.sendPacket(new Packet("logout", ""));
					client.disconnect();
					System.exit(0);
				}
			}
			else
			{
				client.sendPacket(new Packet("msg", msg));
			}
		}
	}
}
