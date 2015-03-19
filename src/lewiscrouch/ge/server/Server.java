package lewiscrouch.ge.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lewiscrouch.ge.common.ServerInfo;
import lewiscrouch.ge.common.dimension.Dimension;
import lewiscrouch.ge.common.dimension.Player;
import lewiscrouch.lib.util.Logger;

public class Server
{
	private ServerInfo serverInfo;
	private List<Connection> connections;

	private List<Registration> registeredPlayers;
	private List<Player> players;

	private Dimension[] dimensions;
	private int dimension;

	private boolean running;

	public Server(ServerInfo serverInfo)
	{
		this.serverInfo = serverInfo;
		this.connections = new ArrayList<Connection>();

		this.registeredPlayers = new ArrayList<Registration>();
		this.players = new ArrayList<Player>();

		this.dimensions = new Dimension[1];
		this.dimensions[this.dimension = 0] = new Dimension("Template");

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

				Connection conn = new Connection(this, socket);
				if(!conn.isDisconnected())
				{
					conn.start();
				}
				else
				{
					this.connections.remove(conn);
				}
			}

			try
			{
				serverSocket.close();
				for(int i = 0; i < this.connections.size(); i++)
				{
					Connection conn = this.connections.get(i);
					try
					{
						conn.getObjectInputStream().close();
						conn.getObjectOutputStream().close();
						conn.getSocket().close();
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

	public boolean isRegistered(String username)
	{
		for(Registration reg : this.registeredPlayers)
		{
			if(reg.getUsername().equalsIgnoreCase(username))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isOnline(String username)
	{
		for(Connection conn : this.connections)
		{
			if(conn.getUsername().equalsIgnoreCase(username))
			{
				return true;
			}
		}
		return false;
	}

	public Registration getPlayerRegistration(String username)
	{
		for(Registration reg : this.registeredPlayers)
		{
			if(reg.getUsername().equalsIgnoreCase(username)) return reg;
		}
		return null;
	}

	public Connection getConnection(String username)
	{
		for(Connection conn : this.connections)
		{
			if(conn.getUsername().equalsIgnoreCase(username)) return conn;
		}
		return null;
	}

	public Player getPlayer(String username)
	{
		for(Player player : this.players)
		{
			if(player.getName().equalsIgnoreCase(username)) return player;
		}
		return null;
	}

	public boolean registerPlayer(String username, String password)
	{
		if(this.isRegistered(username)) return false;

		this.registeredPlayers.add(new Registration(username, password));
		this.players.add(new Player(username, this.getDimension(), this.getDimension().getSpawnPoint()));
		return true;
	}

	public Player loginPlayer(String username, String password, Connection conn)
	{
		if(this.isOnline(username)) return null;

		Registration reg = this.getPlayerRegistration(username);
		if(reg.getPassword().equals(password))
		{
			this.connections.add(conn);
			return this.getPlayer(username);
		}
		return null;
	}

	public boolean logoutPlayer(String username, String reason)
	{
		if(!this.isOnline(username)) return false;

		for(int i = 0; i < this.connections.size(); ++i)
		{
			Connection conn = this.connections.get(i);

			if(conn.getUsername().equalsIgnoreCase(username))
			{
				Logger.info(conn.getUsername() + " disconnected: " + reason);
				this.connections.remove(i);
				this.alertAllPlayers(conn.getUsername() + " disconnected.");
				return true;
			}
		}

		return false;
	}

	public void messageAllPlayers(String sender, String msg)
	{
		for(int i = this.connections.size(); --i >= 0;)
		{
			Connection conn = this.connections.get(i);
			conn.sendMessage(sender, msg);
		}
	}

	public void alertAllPlayers(String msg)
	{
		for(int i = this.connections.size(); --i >= 0;)
		{
			Connection conn = this.connections.get(i);
			conn.sendAlert(msg);
		}
	}

	public ServerInfo getServerInfo()
	{
		return this.serverInfo;
	}

	public List<Connection> getConnections()
	{
		return this.connections;
	}

	public List<Registration> getRegisteredPlayers()
	{
		return this.registeredPlayers;
	}

	public List<Player> getPlayers()
	{
		return this.players;
	}

	public List<Player> getOnlinePlayers()
	{
		List<Player> onlinePlayers = new ArrayList<Player>();
		for(Player player : this.players)
		{
			if(this.isOnline(player.getName()))
			{
				onlinePlayers.add(player);
			}
		}
		return onlinePlayers;
	}

	public String getOnlinePlayersStr()
	{
		String onlinePlayers = "Online players: ";
		boolean first = true;
		for(Player player : this.getOnlinePlayers())
		{
			if(first)
			{
				onlinePlayers += player.getName();
				first = false;
			}
			else
			{
				onlinePlayers += ", " + player.getName();
			}
		}
		return onlinePlayers;
	}

	public Dimension[] getDimensions()
	{
		return this.dimensions;
	}

	public Dimension getDimension()
	{
		return this.dimensions[this.dimension];
	}

	public boolean isRunning()
	{
		return this.running;
	}
}
