package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving login events.
 * The class that is interested in processing a login
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLoginListener<code> method. When
 * the login event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LoginEvent
 */
public class LoginListener extends EntityManager implements Listener {

	/** The motd. */
	private String motd = ChatColor.GREEN
			+ "This server is running EntityManager 3.7.2 by milkywayz";

	/**
	 * On login.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerJoinEvent e) {
		if (Settings.Motd) {
			e.getPlayer().sendMessage(motd);
			return;
		}
	}

	/**
	 * Gets the motd.
	 * 
	 * @return the message of the day for entitymanager
	 */
	public final String getMotd() {
		return motd;

	}

	/**
	 * Set the motd.
	 *
	 * @param message the new motd
	 * @deprecated MOTD will reset upon reload or restart therefore call this on in your plugins onEnable()
	 */
	public void setMotd(String message) {
		writeWarn("MOTD was set to: " + message);
		motd = message;
	}

}
