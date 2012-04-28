package net.milkycraft.api;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropManager implements Listener {
	private static volatile DropManager instance;
	public HashMap<Integer, Player> alerters = new HashMap<Integer, Player>();

	@EventHandler(priority = EventPriority.HIGH)
	public void onAttemptedItemDrop(PlayerDropItemEvent e) {
		final Player player = e.getPlayer();
		List<Integer> bitems = Settings.bitems;
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (player.getWorld().getName().equals(worldname)) {
				if (player.getGameMode() == GameMode.CREATIVE && Settings.cDrop) {
					if (!player.hasPermission("entitymanager.creative.drop")) {
						e.setCancelled(true);
						warnPlayerOnDrop(e);
					}
				} else if (player.getGameMode() == GameMode.SURVIVAL
						&& Settings.sDrop) {
					if (!player.hasPermission("entitymanager.survival.drop")) {
						e.setCancelled(true);
						warnPlayerOnDrop(e);
					}
				}
				for (Integer bitem : bitems) {
					if (e.getItemDrop().getItemStack().getTypeId() == bitem) {
						if (!player
								.hasPermission("entitymanager.bypass.blacklist")) {
							Material mat = new ItemStack(bitem).getType();
							e.setCancelled(true);
							player.sendMessage(ChatColor.GREEN + "[EM]"
									+ ChatColor.RED + " The item: "
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

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDeath(EntityDeathEvent e) {
		final Entity player = e.getEntity();
		List<Integer> bitems = Settings.bitems;
		List<String> worldz = Settings.worlds;
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

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		List<String> worldz = Settings.worlds;
		List<Integer> bitems = Settings.bitems;
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

	public void warnPlayerOnDrop(PlayerDropItemEvent e) {
		final Player player = e.getPlayer();
		final String mode = e.getPlayer().getGameMode().toString()
				.toLowerCase();
		player.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
				+ "You dont have permission to drop items in " + mode + " mode");
		if (Settings.kicking && !player.hasPermission("entitymanager.admin")) {
			AtomicInteger count = new AtomicInteger(5);
			alerters.put(count.incrementAndGet(), e.getPlayer());
			if (alerters.containsValue(16)) {
				player.kickPlayer("Tried to drop items despite warnings");
				count.set(0);
			}
		}
	}
/*
 * TODO: Test the below API methods until they are reliable and useful
 */
	/**
	 * 
	 * @param e
	 * @return the player that dropped the item
	 * @deprecated Method could result in errors, nulls, or just plain not work.
	 *             A better method to get the same return is in the works.
	 */
	public Player getPlayerInvolved(PlayerDropItemEvent e) {
		return e.getPlayer();
	}

	/**
	 * 
	 * @param e
	 * @return the world that the item drop occurred in
	 *  @deprecated Method could result in errors, nulls, or just plain not work.
	 *             A better method to get the same return is in the works.
	 */
	public World getWorldInvolved(PlayerDropItemEvent e) {
		return e.getPlayer().getWorld();
	}

	/**
	 * 
	 * @param e
	 * @return the item involved in the event
	 * @deprecated Method could result in errors, nulls, or just plain not work.
	 *             A better method to get the same return is in the works.
	 * 
	 */
	public Item getItemDropped(PlayerDropItemEvent e) {
		return e.getItemDrop();
	}

	public static DropManager getDropManager() {
		if (instance == null) {
			instance = new DropManager();
		}
		return instance;
	}
}
