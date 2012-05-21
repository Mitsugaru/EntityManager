package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;
import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving login events. The class that is
 * interested in processing a login event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addLoginListener<code> method. When
 * the login event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see LoginEvent
 */
public class LoginListener implements Listener {

	/** The plugin. */
	Spawnegg plugin;

	/**
	 * Instantiates a new clear sign.
	 * 
	 * @param instance
	 *            the instance
	 */
	public LoginListener(Spawnegg instance) {
		plugin = instance;
	}

	/** The motd. */
	private String motd = ChatColor.GREEN
			+ "This server is running EntityManager 3.5.1 by milkywayz";

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
	 * @return the motd
	 */
	public String getMotd() {
		return motd;
	}

}
