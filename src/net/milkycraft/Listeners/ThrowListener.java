package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving throw events.
 * The class that is interested in processing a throw
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addThrowListener<code> method. When
 * the throw event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ThrowEvent
 */
public class ThrowListener extends EntityManager implements Listener {

	/**
	 * On attempted throw.
	 *
	 * @param e the PlayerInteractEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void OnThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		final ItemStack item = e.getItem();
		if (item == null) {
			return;
		}
		if (player.hasPermission("entitymanager.throw.*")) {
			return;
		}
		if (!(item.getType() == Material.EXP_BOTTLE
				|| item.getType() == Material.FIREBALL
				|| item.getType() == Material.EGG
				|| item.getType() == Material.ENDER_PEARL
				|| item.getType() == Material.EYE_OF_ENDER
				|| item.getType() == Material.BOAT
				|| item.getType() == Material.POTION || item.getType() == Material.MINECART)) {
			return;
		}
		for (String worldname : Settings.worlds) {
			if (Settings.world || player.getWorld().getName().equals(worldname)) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK
						|| e.getAction() == Action.RIGHT_CLICK_AIR) {
					if (item.getType() == Material.EXP_BOTTLE) {
						if (Settings.xpbott
								&& !player
										.hasPermission("entitymanager.throw.xpbottles")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.FIREBALL) {
						if (Settings.fire
								&& !player
										.hasPermission("entitymanager.throw.firecharges")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.EGG) {
						if (Settings.egg
								&& !player
										.hasPermission("entitymanager.throw.chickeneggs")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.ENDER_PEARL) {
						if (Settings.pearl
								&& !player
										.hasPermission("entitymanager.throw.enderpearls")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.EYE_OF_ENDER) {
						if (Settings.eye
								&& !player
										.hasPermission("entitymanager.throw.endereyes")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.BOAT) {
						if (Settings.boatz
								&& !player
										.hasPermission("entitymanager.throw.boats")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.MINECART) {
						if (Settings.cartz
								&& !player
										.hasPermission("entitymanager.throw.minecarts")) {
							e.setCancelled(true);
							messager(e);
							return;
						}
					} else if (item.getType() == Material.POTION) {
						if (Settings.potion
								&& !player
										.hasPermission("entitymanager.throw.potions")) {
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
			writeLog("[EntityManager] " + player.getDisplayName().toLowerCase()
					+ " tried to use a "
					+ e.getItem().getType().toString().toLowerCase());
		}
		if (Settings.alertz) {
			for (Player p : player.getServer().getOnlinePlayers()) {
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
