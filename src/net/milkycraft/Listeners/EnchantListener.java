package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantListener implements Listener {
	/*
	 * Class Tested and working
	 */
	Spawnegg plugin;

	public EnchantListener(Spawnegg instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnchantAttempt(PrepareItemEnchantEvent e) {
		if (plugin.getConfig().getBoolean("Disable.Enchanting")
				&& !e.getEnchanter().hasPermission("entitymanager.enchanting")) {
			e.setCancelled(true);
			e.getEnchanter().sendMessage(
					ChatColor.GREEN + "[EM]" + ChatColor.RED
							+ " You dont have permission to enchant items!");
			if (plugin.getConfig().getBoolean("Send-Alerts")) {
				for (Player p : e.getEnchanter().getServer().getOnlinePlayers()) {
					if (p.hasPermission("entitymanager.admin")) {
						p.sendMessage(ChatColor.GREEN
								+ "[EM] "
								+ ChatColor.DARK_RED
								+ e.getEnchanter().getDisplayName()
								+ " tried to enchant a "
								+ ChatColor.GOLD
								+ e.getItem().getType().toString()
										.toLowerCase() + ".");
					}
				}
			}
		}
	}
}
