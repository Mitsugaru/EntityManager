/*
 * 
 */
package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving enchantment events.
 * The class that is interested in processing a enchantment
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEnchantmentListener<code> method. When
 * the enchantment event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EnchantmentEvent
 */
public class EnchantmentListener extends EntityManager implements Listener {

	/**
	 * On enchant attempt.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEnchantAttempt(PrepareItemEnchantEvent e) {
		final World world = e.getEnchantBlock().getWorld();
		for (String worldname : WorldSettings.worlds) {
			if (Settings.world || world.getName() == worldname) {
				if (Settings.totalenchant
						&& !e.getEnchanter().hasPermission(
								"entitymanager.allow.enchanting")) {
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

	/**
	 * Alert.
	 * 
	 * @param e
	 *            the e
	 */
	public void alert(PrepareItemEnchantEvent e) {
		if (Settings.logging) {
			writeLog(e.getEnchanter().getDisplayName() + " tried to enchant a "
					+ e.getItem().getType().toString().toLowerCase());
		}
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
