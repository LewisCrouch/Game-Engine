package lewiscrouch.games.pro.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lewiscrouch.games.pro.common.ServerInfo;
import lewiscrouch.games.pro.common.Session;
import lewiscrouch.games.pro.common.dimension.Dimension;
import lewiscrouch.lib.util.Logger;
import lewiscrouch.lib.util.UniqueKey;

public class Server
{
	private ServerInfo serverInfo;
	private List<ServerSession> registeredClients;
	private List<ClientListener> onlineClients;

	private Dimension[] dimensions;

	private boolean running;

	public Server(ServerInfo serverInfo)
	{
		this.serverInfo = serverInfo;
		this.registeredClients = new ArrayList<ServerSession>();
		this.onlineClients = new ArrayList<ClientListener>();

		this.dimensions = new Dimension[1];
		this.dimensions[0] = new Dimension("MyDim", 1);

		this.running = false;
	}

	public void start()
	{
		this.running = true;

		try
		{
			ServerSocket serverSocket = new ServerSocket(this.serverInfo.getPort());

			while(this.running)
			{
				Logger.info("Server waiting for clients on port " + this.serverInfo.getPort());

				Socket socket = serverSocket.accept();

				if(!this.running) break;

				ClientListener cl = new ClientListener(this, socket);
				if(!cl.isDisconnected())
				{
					this.onlineClients.add(cl);
					cl.start();
				}
			}

			try
			{
				serverSocket.close();
				for(int i = 0; i < this.onlineClients.size(); i++)
				{
					ClientListener cl = this.onlineClients.get(i);
					try
					{
						cl.getObjectInputStream().close();
						cl.getObjectOutputStream().close();
						cl.getSocket().close();
					}
					catch(IOException ex)
					{
						Logger.err("Failed to close client thread correctly!");
					}
				}
			}
			catch(IOException ex)
			{
				Logger.err("Exception closing the server and clients: " + ex);
			}
		}
		catch(IOException ex)
		{
			Logger.err("Exception on new ServerSocket: " + ex);
		}
	}

	public void stop()
	{
		this.running = false;

		try
		{
			new Socket("localhost", this.serverInfo.getPort());
		}
		catch(IOException ex)
		{
			
		}
	}

	public boolean clientExists(String username)
	{
		for(Session client : this.registeredClients)
		{
			if(client.getUsername().equalsIgnoreCase(username))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isClientOnline(String username)
	{
		for(ClientListener client : this.onlineClients)
		{
			if(client.getSession().getUsername().equalsIgnoreCase(username))
			{
				return true;
			}
		}
		return false;
	}

	public ServerSession getClientByUsername(String username)
	{
		for(ServerSession client : this.registeredClients)
		{
			if(client.getUsername().equalsIgnoreCase(username)) return client;
		}
		return null;
	}

	public ClientListener getOnlineClientByUsername(String username)
	{
		for(ClientListener client : this.onlineClients)
		{
			if(client.getSession().getUsername().equalsIgnoreCase(username)) return client;
		}
		return null;
	}

	public ServerSession getClientByKey(String key)
	{
		for(ServerSession client : this.registeredClients)
		{
			if(client.toString().equalsIgnoreCase(key)) return client;
		}
		return null;
	}

	public ClientListener getOnlineClientByKey(String key)
	{
		for(ClientListener client : this.onlineClients)
		{
			if(client.getSession().toString().equalsIgnoreCase(key)) return client;
		}
		return null;
	}

	public boolean registerClient(String username, String password)
	{
		if(this.clientExists(username)) return false;

		this.registeredClients.add(new ServerSession(UniqueKey.genKey(), username, password));
		return true;
	}

	public String loginClient(String username, String password, ClientListener cl)
	{
		if(!this.isClientOnline(username)) return null;

		ServerSession client = this.getClientByUsername(username);
		if(client.getPassword().equals(password))
		{
			
			return client.toString();
		}
		return null;
	}

	public boolean logoutClient(String username, String reason)
	{
		if(!this.isClientOnline(username)) return false;

		for(int i = 0; i < this.onlineClients.size(); ++i)
		{
			ClientListener cl = this.onlineClients.get(i);

			if(cl.getSession().getUsername().equalsIgnoreCase(username))
			{
				Logger.info(cl.getSession().getUsername() + " disconnected: " + reason);
				this.onlineClients.remove(i);
				this.messageAllClients(cl.getSession().getUsername() + " disconnected.");
				return true;
			}
		}

		return false;
	}

	public void messageAllClients(String msg)
	{
		for(int i = this.onlineClients.size(); --i >= 0;)
		{
			ClientListener cl = this.onlineClients.get(i);
			if(!cl.msgClient(msg))
			{
				this.onlineClients.remove(i);
				this.logoutClient(cl.getSession().getUsername(), "disconnected");
			}
		}
	}

	public ServerInfo getServerInfo()
	{
		return this.serverInfo;
	}

	public List<ServerSession> getRegisteredClients()
	{
		return this.registeredClients;
	}

	public List<ClientListener> getOnlineClients()
	{
		return this.onlineClients;
	}

	public Dimension[] getDimensions()
	{
		return this.dimensions;
	}

	public boolean isRunning()
	{
		return this.running;
	}
}
