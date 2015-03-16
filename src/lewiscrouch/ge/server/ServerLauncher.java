package lewiscrouch.ge.server;

import lewiscrouch.ge.common.ServerInfo;
import lewiscrouch.ge.common.dimension.Chunk;
import lewiscrouch.ge.common.dimension.Dimension;
import lewiscrouch.lib.console.Output;

public class ServerLauncher
{
	public static void main(String[] args)
	{
		Dimension dim = new Dimension("Test", 50);

		for(int i = -dim.getScale(); i < dim.getScale(); i++)
		{
			for(int j = -dim.getScale(); j < dim.getScale(); j++)
			{
				for(Chunk chunk : dim.getChunks())
				{
					int x = chunk.getChunkX();
					int y = chunk.getChunkY();
					if(x == i && y == j)
					{
						Output.write("0");
					}
				}
			}
			Output.newLine();
		}

		ServerInfo serverInfo = new ServerInfo("localhost", 1337);
		Server server = new Server(serverInfo);
		server.start();
	}
}
