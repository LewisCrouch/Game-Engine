package lewiscrouch.games.pro.client;

import lewiscrouch.games.pro.common.Packet;
import lewiscrouch.games.pro.common.ServerInfo;
import lewiscrouch.lib.console.Input;
import lewiscrouch.lib.console.Output;

public class ClientLauncher
{
	public static void main(String[] args)
	{
		ServerInfo serverInfo = new ServerInfo("localhost", 1337);
		Client client = new Client(serverInfo);
		client.start();

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
				else if(cmd.equalsIgnoreCase("register"))
				{
					Output.write("Enter username:");
					String username = Input.readStr();
					Output.write("Enter password:");
					String password = Input.readStr();
					client.sendPacket(new Packet("register", username + ":" + password));
				}
				else if(cmd.equalsIgnoreCase("login"))
				{
					Output.write("Enter username:");
					String username = Input.readStr();
					Output.write("Enter password:");
					String password = Input.readStr();
					client.sendPacket(new Packet("login", username + ":" + password));
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
