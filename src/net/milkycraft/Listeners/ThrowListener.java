/*
 * 
 */
package net.milkycraft.Listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ThrowListener implements Listener {

	/* Random note.. im tired right now*/
	/** The log. */
	public final Logger log = Logger.getLogger("Minecraft");

	/**
	 * On throw.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		final ItemStack item = e.getItem();
		final List<String> worldz = Settings.worlds;
		if (item == null) {
			return;
		}

		for (String worldname : worldz) {
			if (player.getWorld().getName().equals(worldname)) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK
						|| e.getAction() == Action.RIGHT_CLICK_AIR) {
					if (item.getType() == Material.EXP_BOTTLE) {
						if (Settings.xpbott
								&& !player
										.hasPermission("entitymanager.xpbottles")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.FIREBALL) {
						if (Settings.fire
								&& !player
										.hasPermission("entitymanager.firecharges")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.EGG) {
						if (Settings.egg
								&& !player
										.hasPermission("entitymanager.chickeneggs")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.ENDER_PEARL) {
						if (Settings.pearl
								&& !player
										.hasPermission("entitymanager.enderpearls")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.EYE_OF_ENDER) {
						if (Settings.eye
								&& !player
										.hasPermission("entitymanager.endereyes")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.BOAT) {
						if (Settings.boatz
								&& !player.hasPermission("entitymanager.boats")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.MINECART) {
						if (Settings.cartz
								&& !player
										.hasPermission("entitymanager.minecarts")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.POTION) {
						if (Settings.potion
								&& !player
										.hasPermission("entitymanager.potions")) {
							e.setCancelled(true);
							messager(e);
							return;
						} // Cancelled
					} // Material

				} // Action
			} // World
		}
	}


	/**
	 * Alerts player they cant do an action.
	 * 
	 * @param e
	 *            the e
	 */
	public final void messager(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		player.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
				+ "You dont have permission for "
				+ e.getItem().getType().toString().toLowerCase() + "'s.");
		if (Settings.logging) {
			log.log(Level.WARNING, "[EntityManager] "
					+ player.getDisplayName().toLowerCase()
					+ " tried to use a "
					+ e.getItem().getType().toString().toLowerCase());
		}
		if (Settings.alertz) {
			for (Player  p : player.getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.DARK_RED
							+ e.getPlayer().getDisplayName()
							+ " tried to use a " + ChatColor.GOLD
							+ e.getItem().getType().toString().toLowerCase()
							+ ".");
				}
			}
		}
	}
}