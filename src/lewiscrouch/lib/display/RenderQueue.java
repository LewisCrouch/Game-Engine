package lewiscrouch.lib.display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lewiscrouch.lib.display.renderable.Renderable;

public class RenderQueue
{
	private static List<Renderable> queue = Collections.synchronizedList(new ArrayList<Renderable>());
	private static List<Renderable> backup = Collections.synchronizedList(new ArrayList<Renderable>());

	private static boolean locked;

	public static void add(Renderable r)
	{
		if(RenderQueue.locked) RenderQueue.backup.add(r);
		else RenderQueue.queue.add(r);
	}

	public static void remove(Renderable r)
	{
		RenderQueue.queue.remove(r);
	}

	public static Iterator<Renderable> iterator()
	{
		return RenderQueue.queue.iterator();
	}

	public static void lock()
	{
		RenderQueue.locked = true;
	}

	public static void release()
	{
		RenderQueue.locked = false;
		RenderQueue.queue.addAll(RenderQueue.backup);
		RenderQueue.backup.clear();
	}
}
