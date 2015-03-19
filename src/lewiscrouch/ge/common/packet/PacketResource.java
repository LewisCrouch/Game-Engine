package lewiscrouch.ge.common.packet;

public class PacketResource
	implements IPacket
{
	private static final long serialVersionUID = -6866726496246107923L;

	private String filename;
	private String type;
	private String data;

	public PacketResource(String filename, String type, String data)
	{
		this.filename = filename;
		this.type = type;
		this.data = data;
	}

	public String getFilename()
	{
		return this.filename;
	}

	public String getType()
	{
		return this.type;
	}

	public String getData()
	{
		return this.data;
	}

	@Override
	public String getKey()
	{
		return "resource";
	}
	
}
