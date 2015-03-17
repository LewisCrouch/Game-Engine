package lewiscrouch.ge.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lewiscrouch.ge.common.Packet;
import lewiscrouch.ge.common.Session;
import lewiscrouch.lib.resource.ResourceManager;
import lewiscrouch.lib.util.Base64;
import lewiscrouch.lib.util.FilenameUtils;
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
				if(p.getValue() instanceof String)
				{
					if(p.getKey().equalsIgnoreCase("register"))
					{
						Logger.info("Client attempting to register.");

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
								Logger.info(key);
								if(key != null)
								{
									this.session = new Session(key, username);

									Logger.info("Sending resources to client with username " + username + ".");
									this.msgClient("Attempting to download resources from server.");
									if(!this.sendResources()) this.msgClient("Resources may not have been downloaded successfully!");

									Logger.info("Client with username " + username + " connected.");
									this.server.messageAllClients(username + " connected.");
									this.msgClient("Connected.");
									return;
								}
								Logger.err("Client login unsuccessful.");
								this.msgClient("Login unsuccessful.");
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
						Logger.info("Client attempting to login.");

						String cred = (String) p.getValue();

						if(cred.indexOf(":") > 0)
						{
							String[] parts = cred.split(":");
							String username = parts[0];
							String password = parts[1];

							String key = this.server.loginClient(username, password, this);
							Logger.info(key);
							if(key != null)
							{
								this.session = new Session(key, username);

								Logger.info("Client with username " + username + " connected.");
								this.server.messageAllClients(username + " connected.");
								this.msgClient("Connected.");
								return;
							}
							Logger.err("Client login unsuccessful.");
							this.msgClient("Login unsuccessful.");
						}
						else
						{
							Logger.err("Client failed to follow protocol (Username and password misformatted) - disconnecting.");
							this.msgClient("Disconnecting due to failed protocol.");
						}
					}
					else if(p.getKey().equalsIgnoreCase("who"))
					{
						Logger.info("Client attempting to get players online.");

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
						this.msgClient("Command not recognized.");
						return;
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

	public boolean sendResources()
	{
		boolean success = true;

		File dir = new File(ResourceManager.RESOURCE_DIR);
		File[] directoryListing = dir.listFiles();
		if(directoryListing != null)
		{
			for(File child : directoryListing)
			{
				if(!this.socket.isConnected())
				{
					this.close();
					return false;
				}

				try
				{
					String ext = FilenameUtils.getFileExtension(child);
					if(ext.equalsIgnoreCase("png"))
					{
						String data = Base64.encodeFromFile(child.getAbsolutePath());
						this.output.writeObject(new Packet("res_img_" + child.getName(), data));
					}
					else if(ext.equalsIgnoreCase("tile"))
					{
						BufferedReader reader = null;
						try
						{
							reader = new BufferedReader(new FileReader(child));

							String line = null;
							while((line = reader.readLine()) != null)
							{
								this.output.writeObject(new Packet("res_tile_" + child.getName(), line));
								break;
							}
						}
						catch(Exception ex)
						{
							Logger.err("Error sending resource to " + this.session.getUsername() + ": " + ex);
							success = false;
						}
						finally
						{
							if(reader != null)
							{
								try
								{
									reader.close();
								}
								catch(IOException ex)
								{
									
								}
							}
						}
					}
				}
				catch(IOException ex)
				{
					Logger.err("Error sending resource to " + this.session.getUsername() + ": " + ex);
					success = false;
				}
			}
		}
		else
		{
			Logger.info("Could not find resources to send to client.");

			success = false;
		}

		return success;
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
