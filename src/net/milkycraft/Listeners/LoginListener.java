package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {
	Spawnegg plugin;

	public LoginListener(Spawnegg instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerJoinEvent e) {
		if (plugin.getConfig().getBoolean("EntityManager.Login-MOTD")) {
			e.getPlayer()
					.sendMessage(
							ChatColor.GREEN
									+ "[EM]"
									+ ChatColor.GREEN
									+ " EntityManager by milkywayz is installed on this server!");
			return;
		}
	}
}
