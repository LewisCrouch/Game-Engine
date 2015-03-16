package lewiscrouch.lib.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageRegister
{
	private static final ImageRegister instance = new ImageRegister();

	private static HashMap<Integer, BufferedImage> images;
	private static int currentIndex;

	private ImageRegister()
	{
		ImageRegister.images = new HashMap<Integer, BufferedImage>();
		ImageRegister.currentIndex = 0;
	}

	public static BufferedImage getImage(int i)
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
			BufferedImage img = ImageIO.read(new File("resources/" + path));
			ImageRegister.images.put(ImageRegister.currentIndex, img);
			return ImageRegister.currentIndex++;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return -1;
		}
	}

	public static int registerImage(BufferedImage img)
	{
		ImageRegister.images.put(ImageRegister.currentIndex, img);
		return ImageRegister.currentIndex++;
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
