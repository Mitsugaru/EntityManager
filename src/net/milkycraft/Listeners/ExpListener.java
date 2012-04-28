package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;
import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ExpListener implements Listener {
	/*
	 * Class tested and working
	 */
	Spawnegg plugin;

	public ExpListener(Spawnegg instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		if (Settings.totalexp) {
			e.setDroppedExp(0);
			for (Player en : e.getEntity().getWorld().getPlayers()) {
				if (en.getExp() > 0 && !en.hasPermission("entitymanager.admin")) {
					en.setExp(0);
					en.sendMessage(ChatColor.GREEN + "[EM]" + ChatColor.RED
							+ " Your exp was reset because it's disabled!");
				}
			}
		}
	}
}
