package lewiscrouch.ge.client;

import java.io.File;
import java.io.IOException;

import lewiscrouch.ge.common.packet.IPacket;
import lewiscrouch.ge.common.packet.PacketResource;
import lewiscrouch.lib.resource.ResourceManager;
import lewiscrouch.lib.util.Base64;
import lewiscrouch.lib.util.Logger;

public class ServerListener extends Thread
{
	private Client client;

	public ServerListener(Client client)
	{
		this.client = client;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				IPacket p = (IPacket) this.client.getObjectInputStream().readObject();

				if(p instanceof PacketResource)
				{
					PacketResource pr = (PacketResource) p;

					File dir = ResourceManager.getResourceFile(this.client.getServerInfo().getHost());
					if(!dir.exists()) dir.mkdir();
	
					String path = this.client.getServerInfo().getHost() + "/";
					Base64.decodeToFile(pr.getData(), path + pr.getFilename());
				}
				else
				{
					for(IServerListener sl : this.client.getServerListeners())
					{
						sl.receivePacket(p);
					}
				}
			}
			catch(IOException ex)
			{
				Logger.err("Server has closed the connection: " + ex);
				ex.printStackTrace();
				break;
			}
			catch(ClassNotFoundException ex)
			{
				
			}
		}
	}
}
