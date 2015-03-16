package lewiscrouch.games.pro.client;

import java.io.IOException;

import lewiscrouch.games.pro.common.Packet;
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
				Logger.info(msg.getValue().toString());
			}
			catch(IOException ex)
			{
				Logger.err("Server has closed the connection: " + ex);
				break;
			}
			catch(ClassNotFoundException ex)
			{
				
			}
		}
	}
}
