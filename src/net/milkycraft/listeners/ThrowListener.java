package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;
import net.milkycraft.permissions.PermissionHandler;
import net.milkycraft.permissions.PermissionNode;

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
 * The listener interface for receiving throw events. The class that is
 * interested in processing a throw event implements this interface, and the
 * object created with that class is registered with a component using the
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
	 * @param e
	 *            the PlayerInteractEvent
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void OnThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		final ItemStack item = e.getItem();
		if (item == null) {
			return;
		}
		if (PermissionHandler.has(player, PermissionNode.THROW_ALL)) {
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getPlayer().getWorld()
						.getName())) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK
					|| e.getAction() == Action.RIGHT_CLICK_AIR) {
				if (item.getType() == Material.EXP_BOTTLE) {
					if (Settings.xpbott
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_XPBOTTLES)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.FIREBALL) {
					if (Settings.fire
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_FIRECHARGES)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.EGG) {
					if (Settings.egg
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_CHICKENEGGS)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.ENDER_PEARL) {
					if (Settings.pearl
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_ENDERPEARLS)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.EYE_OF_ENDER) {
					if (Settings.eye
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_ENDEREYES)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.BOAT) {
					if (Settings.boatz
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_BOATS)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.MINECART) {
					if (Settings.cartz
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_MINECARTS)) {
						e.setCancelled(true);
						messager(e);
						return;
					}
				} else if (item.getType() == Material.POTION) {
					if (Settings.potion
							&& !PermissionHandler.has(player,
									PermissionNode.THROW_POTIONS)) {
						e.setCancelled(true);
						messager(e);
						return;
					} // Cancelled
				} // Material

			} // Action
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
				if (PermissionHandler.has(player, PermissionNode.ADMIN)) {
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
