package net.milkycraft.api;

import net.milkycraft.configuration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The Class DropManager.
 */
public class DropManager implements Listener {

	/** The Constant instance. */
	protected static final DropManager instance = new DropManager();
	
	/**
	 * On attempted item drop.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onAttemptedItemDrop(PlayerDropItemEvent e) {
		if (e.getPlayer() == null) {
			return;
		}

		if (e.getPlayer().getGameMode() == null) {
			return;
		}
		final Player player = e.getPlayer();
		final String mode = e.getPlayer().getGameMode().toString()
				.toLowerCase();
		for (String worldname : Settings.worlds) {
			if (player.getWorld().getName().equals(worldname)) {
				if (player.getGameMode() == GameMode.CREATIVE) {
					if (!player.hasPermission("entitymanager.creative.drop")
							&& Settings.cDrop) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.GREEN + "[EM] "
								+ ChatColor.RED
								+ "You dont have permission to drop items in "
								+ mode + " mode.");
						return;
					}
				}
				if (player.getGameMode() == GameMode.SURVIVAL) {
					if (!player.hasPermission("entitymanager.survival.drop")
							&& Settings.sDrop) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.GREEN + "[EM] "
								+ ChatColor.RED
								+ "You dont have permission to drop items in "
								+ mode + " mode.");
						return;
					}
				}
				for (Integer bitem : Settings.bitems) {
					if (e.getItemDrop().getItemStack().getTypeId() == bitem) {
						if (!player
								.hasPermission("entitymanager.bypass.blacklist")) {
							final Material mat = new ItemStack(bitem).getType();
							e.setCancelled(true);
							player.sendMessage(ChatColor.GREEN + "[EM]"
									+ ChatColor.RED + "The item: "
									+ ChatColor.YELLOW
									+ mat.toString().toLowerCase()
									+ ChatColor.RED
									+ " is on the item blacklist!");
						}
					}
				}
			}
		}
	}

	/**
	 * On entity death.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDeath(EntityDeathEvent e) {
		final Entity player = e.getEntity();
		for (String worldname : Settings.worlds) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (Settings.onDeath) {
					e.getDrops().clear();
					return;
				}
				/*
				 * Filters through the drops of dead entities If its a player,
				 * check if they have permission, if they dont remove the
				 * blacklisted drops If its any other entity, remove the blocked
				 * items from there droppings
				 */
				for (Integer bitem : Settings.bitems) {
					if (e.getEntity() instanceof Player) {
						if (!((Player) player)
								.hasPermission("entitymanager.bypass.blacklist")) {
							e.getDrops().remove(bitem);
						}
					} else {
						e.getDrops().remove(bitem);
					}
				}
			}
		}
	}
	
	/**
	 * Adds a item to the item drop blacklist.
	 *
	 * @param itemid the item id of the item / block you want to block from being dropped
	 * @deprecated Only way for method to reflect changes is to reload server.
	 */
	public void addBitem(Integer itemid) {
		if(itemid == null || itemid == 0) {
			return;
		}
		Settings.getConfig().getIntegerList("block.ItemDrop.Blacklisted-items").add(itemid);
	}
	
	/**
	 * Adds a item to the dispenser blacklist.
	 *
	 * @param itemid the item id of the item / block you want to block from being dispensed
	 * @deprecated Only way for method to reflect changes is to reload server.
	 */
	public void addBDItem(Integer itemid) {
		if(itemid == null || itemid == 0) {
			return;
		}
		Settings.getConfig().getIntegerList("block.Dispense.Items").add(itemid);
	}

	/**
	 * Gets the drop manager.
	 * @author milkywayz
	 * @category DropManager
	 * @since 3.5ish
	 * @see DropManager
	 * @return the drop manager class
	 */
	public static DropManager getDropManager() {
		return instance;
	}
}
