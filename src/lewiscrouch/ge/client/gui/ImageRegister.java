package lewiscrouch.ge.client.gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageRegister
{
	private static final ImageRegister instance = new ImageRegister();

	private static HashMap<Integer, Image> images;
	private static int currentIndex;

	private ImageRegister()
	{
		ImageRegister.images = new HashMap<Integer, Image>();
		ImageRegister.currentIndex = 0;
	}

	public static Image getImage(int i)
	{
		if(ImageRegister.images.containsKey(i))
		{
			return ImageRegister.images.get(i);
		}
		return null;
	}

	public static int registerImage(String path)
	{
		try
		{
			Image img = ImageIO.read(new File(path));
			ImageRegister.images.put(ImageRegister.currentIndex, img);
			return ImageRegister.currentIndex++;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return -1;
		}
	}

	public static boolean removeImage(int i)
	{
		if(ImageRegister.images.containsKey(i))
		{
			ImageRegister.images.remove(i);
			return true;
		}
		else
		{
			return false;
		}
	}

	public static ImageRegister getInstance()
	{
		return ImageRegister.instance;
	}
}
