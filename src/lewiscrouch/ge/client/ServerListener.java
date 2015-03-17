package lewiscrouch.ge.client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import lewiscrouch.ge.common.Packet;
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
				Packet msg = (Packet) this.client.getObjectInputStream().readObject();

				File dir = ResourceManager.getResourceFile(this.client.getServerInfo().getHost());
				if(!dir.exists()) dir.mkdir();

				String path = this.client.getServerInfo().getHost() + "/";
				String start = "";
				if(msg.getKey().startsWith(start = "res_img_"))
				{
					Base64.decodeToFile(msg.getValue().toString(), ResourceManager.getResourcePath(path + msg.getKey().substring(start.length())));
				}
				else if(msg.getKey().startsWith(start = "res_tile_"))
				{
					PrintWriter out = new PrintWriter(ResourceManager.getResourceFile(path + msg.getKey().substring(start.length()) + ".tile"));
					out.println(msg.getValue().toString());
					out.close();
				}
				else
				{
					Logger.info(msg.getValue().toString());
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
