package lewiscrouch.ge.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lewiscrouch.ge.common.ServerInfo;
import lewiscrouch.ge.common.packet.IPacket;
import lewiscrouch.lib.util.Logger;

public class Client
{
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	private ServerInfo serverInfo;

	private List<IServerListener> serverListeners;

	public Client(ServerInfo serverInfo)
	{
		this.serverInfo = serverInfo;
		this.serverListeners = new ArrayList<IServerListener>();
	}

	public boolean start()
	{
		try
		{
			this.socket = new Socket(this.serverInfo.getHost(), this.serverInfo.getPort());
		}
		catch(Exception ex)
		{
			Logger.err("Failed to connect to server.");
			return false;
		}

		String msg = "Connection accepted " + this.socket.getInetAddress() + ":" + this.socket.getPort();
		Logger.info(msg);

		try
		{
			this.input = new ObjectInputStream(this.socket.getInputStream());
			this.output = new ObjectOutputStream(this.socket.getOutputStream());
		}
		catch(IOException ex)
		{
			Logger.err("Exception creating new input/output streams: " + ex);
		}

		new ServerListener(this).start();

		return true;
	}

	public void sendPacket(IPacket p)
	{
		try
		{
			this.output.writeObject(p);
		}
		catch(IOException ex)
		{
			Logger.err("Failed to send packet to server: " + ex);
		}
	}

	public void disconnect()
	{
		try
		{
			if(this.input != null) this.input.close();
		}
		catch(Exception ex)
		{
		}

		try
		{
			if(this.output != null) this.output.close();
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

	public void addServerListener(IServerListener serverListener)
	{
		this.serverListeners.add(serverListener);
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

	public ServerInfo getServerInfo()
	{
		return this.serverInfo;
	}

	public List<IServerListener> getServerListeners()
	{
		return this.serverListeners;
	}
}
