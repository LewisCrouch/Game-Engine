package lewiscrouch.ge.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lewiscrouch.ge.common.dimension.Player;
import lewiscrouch.ge.common.packet.IPacket;
import lewiscrouch.ge.common.packet.PacketAlert;
import lewiscrouch.ge.common.packet.PacketLogin;
import lewiscrouch.ge.common.packet.PacketMessage;
import lewiscrouch.ge.common.packet.PacketMove;
import lewiscrouch.ge.common.packet.PacketPlayer;
import lewiscrouch.ge.common.packet.PacketPlayerMessage;
import lewiscrouch.ge.common.packet.PacketRegister;
import lewiscrouch.ge.common.packet.PacketResource;
import lewiscrouch.ge.common.packet.PacketWho;
import lewiscrouch.lib.dimension.Direction;
import lewiscrouch.lib.resource.ResourceManager;
import lewiscrouch.lib.util.Base64;
import lewiscrouch.lib.util.FilenameUtils;
import lewiscrouch.lib.util.Logger;

public class Connection extends Thread
{
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	private Server server;
	private String username;

	private boolean disconnected;

	public Connection(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;

		this.initConnection();
	}

	public void initConnection()
	{
		Logger.info("Client attempting to connect.");
		try
		{
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());

			Object obj = this.input.readObject();
			if(obj instanceof IPacket)
			{
				IPacket p = (IPacket) obj;
				if(p instanceof PacketLogin)
				{
					PacketLogin pl = (PacketLogin) p;
					Player player = this.server.loginPlayer(pl.getUsername(), pl.getPassword(), this);
					if(player == null)
					{
						Logger.err("Player failed to login with username " + pl.getUsername());
						this.sendAlert("Failed to login, disconnecting.");
					}
					else
					{
						Logger.info("Player logged in with username " + pl.getUsername());
						this.sendAlert("Logged in.");
						this.server.alertAllPlayers(pl.getUsername() + " connected.");
						this.sendPacket(new PacketPlayer(this.server.getPlayer(pl.getUsername())));
						this.username = pl.getUsername();
						return;
					}
				}
				else if(p instanceof PacketRegister)
				{
					PacketRegister pr = (PacketRegister) p;
					boolean success = this.server.registerPlayer(pr.getUsername(), pr.getPassword());
					if(!success)
					{
						Logger.err("Player failed to register with username " + pr.getUsername());
						this.sendAlert("Failed to register, disconnecting.");
					}
					else
					{
						Logger.info("Player registered with username " + pr.getUsername());
						this.sendAlert("Registration successful.");

						Player player = this.server.loginPlayer(pr.getUsername(), pr.getPassword(), this);
						if(player == null)
						{
							Logger.err("Player failed to login with username " + pr.getUsername());
							this.sendAlert("Failed to login, disconnecting.");
						}
						else
						{
							Logger.info("Player logged in with username " + pr.getUsername());
							this.sendAlert("Logged in.");
							this.server.alertAllPlayers(pr.getUsername() + " connected.");
							this.sendPacket(new PacketPlayer(this.server.getPlayer(pr.getUsername())));
							this.username = pr.getUsername();
							return;
						}
					}
				}
				else if(p instanceof PacketWho)
				{
					this.sendAlert(this.server.getOnlinePlayersStr());
				}
			}
			else
			{
				Logger.err("Client failed to follow protocol (Object not instance of IPacket) - disconnecting.");
				this.sendAlert("Disconnecting due to failed protocol.");
			}
		}
		catch(IOException ex)
		{
			Logger.err("Exception creating new input/output streams: " + ex);
		}
		catch(ClassNotFoundException ex)
		{
		}

		this.close();
	}

	@Override
	public void run()
	{
		boolean running = true;
		while(running)
		{
			IPacket p = null;
			try
			{
				p = (IPacket) this.input.readObject();
				if(p instanceof PacketMove)
				{
					PacketMove pm = (PacketMove) p;
					Direction drc = Direction.getDirectionFromLabel((String) pm.getDirection());
					Player player = this.server.getPlayer(this.username);
					player.move(drc);
					this.sendPacket(new PacketPlayer(player));
				}
				else if(p instanceof PacketPlayerMessage)
				{
					PacketMessage pm = (PacketMessage) p;
					this.server.messageAllPlayers(this.username, pm.getMessage());
				}
				else if(p instanceof PacketWho)
				{
					this.sendAlert(this.server.getOnlinePlayersStr());
				}
			}
			catch(IOException ex)
			{
				Logger.err("Failed to handle packet from " + this.username + ": " + ex);
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
		}
	}

	public boolean sendAlert(String msg)
	{
		return this.sendPacket(new PacketAlert(msg));
	}

	public boolean sendMessage(String username, String msg)
	{
		return this.sendPacket(new PacketMessage(username, msg));
	}

	public boolean sendPacket(IPacket packet)
	{
		if(!this.socket.isConnected())
		{
			this.close();
			return false;
		}

		try
		{
			this.output.writeObject(packet);
		}
		catch(IOException ex)
		{
			Logger.err("Error sending packet to " + this.getUsername() + ": " + ex);
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
					String filename = "res_img_" + child.getName();
					String type = FilenameUtils.getFileExtension(child);
					if(type.equalsIgnoreCase("png") || type.equalsIgnoreCase("tm"))
					{
						String data = Base64.encodeFromFile(child.getAbsolutePath());
						this.output.writeObject(new PacketResource(filename, type, data));
					}
				}
				catch(IOException ex)
				{
					Logger.err("Error sending resource to " + this.getUsername() + ": " + ex);
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
		this.disconnected = true;

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

	public String getUsername()
	{
		return this.username;
	}

	public boolean isDisconnected()
	{
		return this.disconnected;
	}
}
