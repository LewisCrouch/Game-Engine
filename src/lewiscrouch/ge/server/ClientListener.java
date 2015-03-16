package lewiscrouch.ge.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lewiscrouch.ge.common.Packet;
import lewiscrouch.ge.common.Session;
import lewiscrouch.lib.util.Logger;

public class ClientListener extends Thread
{
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	private Server server;
	private Session session;

	private boolean disconnected;

	public ClientListener(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;

		Logger.info("Client attempting to connect.");
		try
		{
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());

			Object obj = this.input.readObject();
			if(obj instanceof Packet)
			{
				Packet p = (Packet) obj;
				Logger.info("Key: " + p.getKey());
				if(p.getValue() instanceof String)
				{
					if(p.getKey().equalsIgnoreCase("register"))
					{
						System.out.println("Client attempting to register.");

						String cred = (String) p.getValue();

						if(cred.indexOf(":") > 0)
						{
							String[] parts = cred.split(":");
							String username = parts[0];
							String password = parts[1];
							boolean success = this.server.registerClient(username, password);

							if(success)
							{
								String key = this.server.loginClient(username, password, this);

								this.session = new Session(key, username);

								Logger.info("Client with username " + username + " connected.");
								this.server.messageAllClients(username + " connected.");

								return;
							}
							else
							{
								Logger.err("Client registration unsuccessful.");
								this.msgClient("Registration unsuccessful.");
							}
						}
						else
						{
							Logger.err("Client failed to follow protocol (Username and password misformatted) - disconnecting.");
							this.msgClient("Disconnecting due to failed protocol.");
						}
					}
					else if(p.getKey().equalsIgnoreCase("login"))
					{
						System.out.println("Client attempting to login.");

						String cred = (String) p.getValue();

						if(cred.indexOf(":") > 0)
						{
							String[] parts = cred.split(":");
							String username = parts[0];
							String password = parts[1];
							String key = this.server.loginClient(username, password, this);

							this.session = new Session(key, username);

							Logger.info("Client with username " + username + " connected.");
							this.server.messageAllClients(username + " connected.");
							return;
						}
						else
						{
							Logger.err("Client failed to follow protocol (Username and password misformatted) - disconnecting.");
							this.msgClient("Disconnecting due to failed protocol.");
						}
					}
					else if(p.getKey().equalsIgnoreCase("who"))
					{
						System.out.println("Client attempting to get players online.");

						this.msgClient("Players online:");
						String players = "";
						boolean first = true;
						for(int i = 0; i < this.server.getOnlineClients().size(); ++i)
						{
							ClientListener cl = this.server.getOnlineClients().get(i);
							if(first)
							{
								players += cl.getSession().getUsername();
								first = false;
							}
							else
							{
								players += ", " + cl.getSession().getUsername();
							}
						}
						if(players.isEmpty()) players = "No one is online.";
						this.msgClient(players);
						return;
					}
					else
					{
						Logger.err("Client failed to follow protocol (Command not recognised) - disconnecting.");
						this.msgClient("Disconnecting due to failed protocol.");
					}
				}
				else
				{
					Logger.err("Client failed to follow protocol (Packet value not instance of string) - disconnecting.");
					this.msgClient("Disconnecting due to failed protocol.");
				}
			}
			else
			{
				Logger.err("Client failed to follow protocol (Object not instance of Packet) - disconnecting.");
				this.msgClient("Disconnecting due to failed protocol.");
			}
		}
		catch(IOException ex)
		{
			Logger.err("Exception creating new input/output streams: " + ex);
		}
		catch(ClassNotFoundException ex)
		{
		}

		this.disconnected = true;
		this.close();
	}

	@Override
	public void run()
	{
		boolean running = true;
		while(running)
		{
			Packet p = null;
			try
			{
				p = (Packet) this.input.readObject();
				if(p.getKey() == null || p.getValue() == null)
				{
					throw new Exception("Packet contains null");
				}
			}
			catch(IOException ex)
			{
				this.server.logoutClient(this.session.getUsername(), "Connection reset");
				break;
			}
			catch(ClassNotFoundException ex)
			{
				break;
			}
			catch(Exception ex)
			{
				break;
			}

			switch(p.getKey())
			{
				case "who":
					this.msgClient("Players online:");
					String players = "";
					boolean first = true;
					for(int i = 0; i < this.server.getOnlineClients().size(); ++i)
					{
						ClientListener cl = this.server.getOnlineClients().get(i);
						if(first)
						{
							players += cl.getSession().getUsername();
							first = false;
						}
						else
						{
							players += ", " + cl.getSession().getUsername();
						}
					}
					if(players.isEmpty()) players = "No one is online.";
					this.msgClient(players);
					break;
				case "msg":
					Logger.info(this.getSession().getUsername() + ": " + p.getValue().toString());
					this.server.messageAllClients(this.getSession().getUsername() + ": " + p.getValue().toString());
					break;
				case "logout":
					this.server.logoutClient(this.getSession().getUsername(), "quit");
					running = false;
					break;
			}
		}
	}

	public boolean msgClient(String msg)
	{
		if(!this.socket.isConnected())
		{
			this.close();
			return false;
		}

		try
		{
			this.output.writeObject(new Packet("msg", msg));
		}
		catch(IOException ex)
		{
			Logger.err("Error sending message to " + this.session.getUsername() + ": " + ex);
		}

		return true;
	}

	private void close()
	{
		try
		{
			if(this.output != null) this.output.close();
		}
		catch(Exception ex)
		{
		}

		try
		{
			if(this.input != null) this.input.close();
		}
		catch(Exception ex)
		{
		}

		try
		{
			if(this.socket != null) this.socket.close();
		}
		catch(Exception ex)
		{
		}
	}

	public Socket getSocket()
	{
		return this.socket;
	}

	public ObjectInputStream getObjectInputStream()
	{
		return this.input;
	}

	public ObjectOutputStream getObjectOutputStream()
	{
		return this.output;
	}

	public Server getServer()
	{
		return this.server;
	}

	public Session getSession()
	{
		return this.session;
	}

	public boolean isDisconnected()
	{
		return this.disconnected;
	}
}
