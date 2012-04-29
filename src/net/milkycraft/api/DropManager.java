/*
 * 
 */
package net.milkycraft.api;

import java.util.List;
import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;


/**
 * The Class DropManager.
 */
public class DropManager implements Listener {
	
	/** The Constant instance. */
	private final static DropManager instance = new DropManager();

	/**
	 * On attempted item drop.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onAttemptedItemDrop(PlayerDropItemEvent e) {
		final Player player = e.getPlayer();
		final String mode = e.getPlayer().getGameMode().toString().toLowerCase();
		final List<Integer> bitems = Settings.bitems;
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (player.getWorld().getName().equals(worldname)) {
				if (player.getGameMode() == GameMode.CREATIVE) {
					if (!player.hasPermission("entitymanager.creative.drop") && Settings.cDrop) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
								+ "You dont have permission to drop items in " + mode + " mode.");	
						return;
					}
				} 
				if (player.getGameMode() == GameMode.SURVIVAL) {
					if (!player.hasPermission("entitymanager.survival.drop") && Settings.sDrop) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
								+ "You dont have permission to drop items in " + mode + " mode.");	
						return;
					}
				}
				for (Integer bitem : bitems) {
					if (e.getItemDrop().getItemStack().getTypeId() == bitem) {
						if (!player
								.hasPermission("entitymanager.bypass.blacklist")) {
							Material mat = new ItemStack(bitem).getType();
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
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDeath(EntityDeathEvent e) {
		final Entity player = e.getEntity();
		final List<Integer> bitems = Settings.bitems;
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (Settings.onDeath) {
					e.getDrops().clear();
					return;
				}
				for (Integer bitem : bitems) {
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
	 * On block break.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		final List<String> worldz = Settings.worlds;
		final List<Integer> bitems = Settings.bitems;
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (Settings.bDestroy
						&& !e.getPlayer().hasPermission(
								"entitymanager.block.drop")) {
					e.getBlock().getDrops().clear();
					return;
				}
				for (Integer bitem : bitems) {
					if (e.getBlock().getTypeId() == bitem) {
						e.getBlock().getDrops().remove(bitem);
					}
				}
			}
		}
	}

/**
 * Gets the drop manager.
 *
 * @return The dropmanager, so far nothing to get...
 */
	public static DropManager getDropManager() {
		return instance;
	}
}
