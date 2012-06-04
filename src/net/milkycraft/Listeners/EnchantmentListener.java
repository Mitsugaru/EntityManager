package net.milkycraft.listeners;

import java.util.List;

import net.milkycraft.configuration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantmentListener implements Listener {
	private final List<String> worldz = Settings.worlds;
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEnchantAttempt(PrepareItemEnchantEvent e) {
		final World world = e.getEnchantBlock().getWorld();
		if (Settings.world) {
			if (Settings.totalenchant
					&& !e.getEnchanter().hasPermission(
							"entitymanager.enchanting")) {
				e.setCancelled(true);
				e.getEnchanter()
						.sendMessage(
								ChatColor.GREEN
										+ "[EM]"
										+ ChatColor.RED
										+ " You dont have permission to enchant items!");
				alert(e);
				return;
			}
		} else {
			for (String worldname : worldz) {
				if (world.getName() == worldname) {
					if (Settings.totalenchant
							&& !e.getEnchanter().hasPermission(
									"entitymanager.enchanting")) {
						e.setCancelled(true);
						e.getEnchanter()
								.sendMessage(
										ChatColor.GREEN
												+ "[EM]"
												+ ChatColor.RED
												+ " You dont have permission to enchant items!");
						alert(e);
					}
				}
			}
		}
	}

	/**
	 * Alert.
	 * 
	 * @param e
	 *            the e
	 */
	public void alert(PrepareItemEnchantEvent e) {
		if (Settings.alertz) {
			for (Player p : e.getEnchanter().getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.DARK_RED
							+ e.getEnchanter().getDisplayName()
							+ " tried to enchant a " + ChatColor.GOLD
							+ e.getItem().getType().toString().toLowerCase()
							+ ".");
				}
			}
		}
	}
}

