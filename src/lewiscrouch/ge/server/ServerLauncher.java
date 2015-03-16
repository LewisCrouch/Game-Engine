package lewiscrouch.ge.server;

import lewiscrouch.ge.common.ServerInfo;

public class ServerLauncher
{
	public static void main(String[] args)
	{
		ServerInfo serverInfo = new ServerInfo("localhost", 1337);
		Server server = new Server(serverInfo);
		server.start();
	}
}
