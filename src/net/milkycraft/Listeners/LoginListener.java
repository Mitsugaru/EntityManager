package net.milkycraft.listeners;


import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

	public class LoginListener implements Listener {

		/** The plugin. */
		EntityManager plugin;

		/**
		 * Instantiates a new clear sign.
		 * 
		 * @param instance
		 *            the instance
		 */
		public LoginListener(EntityManager instance) {
			plugin = instance;
		}

		/** The motd. */
		private final static String motd = ChatColor.GREEN
				+ "This server is running EntityManager 3.6.2 by milkywayz";

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
		public static String getMotd() {		
			return motd;
			
		}

	}

