package lewiscrouch.games.pro.common;

import java.util.List;

/**
 * Used to communicate with server or client.
 * @author Lewis
 *
 */
public interface IChat
{
	/**
	 * Sends a message to the server from the client.
	 * @param msg
	 */
	public void sendMessage(String msg);

	/**
	 * Sends an alert to the client.
	 * @param alert
	 */
	public void sendAlert(String alert);

	/**
	 * Gets all messages received.
	 * @return
	 */
	public List<String> getMessages();

	/**
	 * Gets all alerts received.
	 * @return
	 */
	public List<String> getAlerts();
}
