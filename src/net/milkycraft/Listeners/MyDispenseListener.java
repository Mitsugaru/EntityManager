/*
 * 
 */
package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving myDispense events.
 * The class that is interested in processing a myDispense
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMyDispenseListener<code> method. When
 * the myDispense event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MyDispenseEvent
 */
public class MyDispenseListener implements Listener {

	/**
	 * On dispense.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDispense(BlockDispenseEvent event) {
		final ItemStack item = event.getItem();
		final List<Integer> itemz = Settings.items;
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (event.getBlock().getWorld().getName().equals(worldname)) {
				if (item.getType() == Material.MONSTER_EGG) {
					if (Settings.MonsEggs) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				} else if (item.getType() == Material.EGG) {
					if (Settings.ChickEggs) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				} else if (item.getType() == Material.POTION) {
					if (Settings.potionz) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				} else if (item.getType() == Material.FIREBALL) {
					if (Settings.Fballs) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				} else if (item.getType() == Material.EXP_BOTTLE) {
					if (Settings.xBottz) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				for (Integer itemx : itemz) {
					if (event.getItem().getTypeId() == itemx) {
						event.setCancelled(true);
						alert(event);
					}
				}
			}
		}
	}

	/**
	 * Alert.
	 *
	 * @param event the event
	 */
	public final void alert(BlockDispenseEvent event) {
		double x = event.getBlock().getLocation().getX();
		double y = event.getBlock().getLocation().getY();
		double z = event.getBlock().getLocation().getZ();
		final int xx = (int) x;
		final int yy = (int) y;
		final int zz = (int) z;
		final String item = event.getItem().getType().toString().toLowerCase();
		if (Settings.alertz) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.DARK_RED + "Failed dispense of "
							+ ChatColor.GOLD + item + " at: " + xx + "," + yy
							+ "," + zz + ".");
				}
			}
		}
	}
}
