/*
 * 
 */
package net.milkycraft.listeners;

import java.util.List;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;
import net.milkycraft.enums.EntityCategory;
import net.milkycraft.events.SpawnEggEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving spawnEgg events. The class that is
 * interested in processing a spawnEgg event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addSpawnEggListener<code> method. When
 * the spawnEgg event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see SpawnEggEvent
 */
public class SpawnEggListener extends EntityManager implements Listener {

	/** The worldz. */
	private List<String> worldz = WorldSettings.worlds;

	/** The monster. */
	private static int monster = Settings.mons;

	/** The animal. */
	private static int animal = Settings.animal;

	/** The npc. */
	private static int npc = Settings.npc;

	/**
	 * On spawn attempt.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpawnAttempt(PlayerInteractEvent e) {
		/* Variables */
		final Player player = e.getPlayer();
		final String playa = e.getPlayer().getName();
		final Location loc = e.getPlayer().getLocation();
		final SpawnEggEvent seg = new SpawnEggEvent(e);
		/* Done with variables , Null check time */
		if (e.getItem() == null) {
			return;
		}
		if (!(e.getItem().getTypeId() == 383)) {
			return;
		}
		if (e.getClickedBlock() == null) {
			return;
		}
		if (e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_AIR) {
			return;
		}
		/* Check if player has the node */
		if (player.hasPermission("entitymanager.*")) {
			return;
		}

		for (String wname : worldz) {
			if (Settings.world || player.getWorld().getName().equals(wname)) {
				if (player.getItemInHand().getTypeId() == 383) {
					if (!worldguardPlugin.canBuild(player, loc)) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.YELLOW
								+ "You cant use spawner eggs in this region!");
						if (Settings.logging) {
							writeLog(e.getPlayer()
									+ " tried to throw a spawner egg in "
									+ worldguardPlugin.getRegionManager(
											e.getPlayer().getWorld())
											.getApplicableRegions(loc));
						}
						return;
					}

					final Material mat = e.getClickedBlock().getType();
					if (!(mat == Material.CHEST || mat == Material.FURNACE || mat == Material.DISPENSER)) {
						if (!onDecidingSpawningFactors(seg)) {
							e.setCancelled(true); // Cancel
							e.getPlayer()
									// Tell player
									.sendMessage(
											ChatColor.GREEN
													+ "[EM]"
													+ ChatColor.RED
													+ "You dont have permission to use "
													+ ChatColor.YELLOW
													+ seg.getEntityBreed()
															.toString()
															.toLowerCase()
													+ ChatColor.RED + " eggs");
							alerter(seg); // Alert admins
						} else { // Handle egg charges based on the egg trying
									// to be used
							if (!player
									.hasPermission("entitymanager.bypass.charge")
									&& econ != null) {
								if (seg.getEntityBreed().getCategory() == EntityCategory.ANIMAL) {
									econ.withdrawPlayer(playa, animal);
								} else if (seg.getEntityBreed().getCategory() == EntityCategory.MONSTER) {
									econ.withdrawPlayer(playa, monster);
								} else if (seg.getEntityBreed().getCategory() == EntityCategory.NPC) {
									econ.withdrawPlayer(playa, npc);
								} // end of specific charge block
							} // end of bypass perm charge block
						} // End of charges
					} else {
						player.sendMessage(ChatColor.AQUA
								+ "Opened that "
								+ e.getClickedBlock().getType().toString()
										.toLowerCase() + " with a "
								+ seg.getEntityBreed().toString().toLowerCase()
								+ " egg");
					}
				} // Item check
			} // World
		} // World Iterator
	}

	/**
	 * On deciding spawning factors.
	 * 
	 * @param e
	 *            the e
	 * @return true, if successful
	 */
	public boolean onDecidingSpawningFactors(SpawnEggEvent e) {
		if (e.getPlayer().hasPermission("entitymanager.*")) {
			return true;
		}
		if (e.getEntityBreed().getCategory() == EntityCategory.SPECIAL) {
			return true;
		}
		if (e.getEntityBreed().getCategory() == EntityCategory.UNKNOWN) {
			return true;
		}
		if (e.getEntityBreed().getCategory() != EntityCategory.UNKNOWN) {
			if (Settings.getConfig().getBoolean(
					"disabled.eggs."
							+ e.getEntityBreed().toString().toLowerCase())) {
				if (e.getPlayer().hasPermission(
						"entitymanager."
								+ e.getEntityBreed().toString().toLowerCase())) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * Spawn a entity based on its metadata 383:97 = Snow golem, 383:99 =
	 * Irongolem, 383:200 = Ender crystal.
	 *
	 * @param e the interact event
	 * @author milkywayz
	 * @since 3.8.1
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void spawnNonNaturalEntity(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}
		/*We want right clicks on blocks only*/
		if (e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			return;
		}
		if (!(e.getItem().getTypeId() == 383)) {
			return;
		}
		final Location loc = e.getClickedBlock().getLocation().add(0, 1, 0);
		final Block block = e.getClickedBlock();
		final Player player = e.getPlayer();
		if(e.getItem().getDurability() == 40) {
			if (player.hasPermission("entitymanager.spawn.minecart")) {
				block.getWorld().spawn(loc, Minecart.class);
				return;
			} else {
				player.sendMessage(ChatColor.RED
						+ "You don't have permission to spawn minecarts");
				return;
			}
		} else if (e.getItem().getDurability() == 41) {
			if (player.hasPermission("entitymanager.spawn.boat")) {
				if(loc.getBlock().isLiquid() || loc.getBlock().getTypeId() == 0) {
				block.getWorld().spawn(loc, Boat.class);
				return;
				} else {
					player.sendMessage(ChatColor.RED + "That boat would be caught in a block!");
				}
			} else {
				player.sendMessage(ChatColor.RED
						+ "You don't have permission to spawn boats");
				return;
			}
		} else if (e.getItem().getDurability() == 97) {
			if (player.hasPermission("entitymanager.spawn.snowgolem")) {
				block.getWorld().spawn(loc, Snowman.class);
				return;
			} else {
				player.sendMessage(ChatColor.RED
						+ "You don't have permission to spawn snow golems");
				return;
			}
		} else if (e.getItem().getDurability() == 99) {
			if (player.hasPermission("entitymanager.spawn.irongolem")) {			
				block.getWorld().spawn(loc, IronGolem.class);
				return;
			} else {
				player.sendMessage(ChatColor.RED
						+ "You don't have permission to spawn iron golems");
				return;
			}
		} else if (e.getItem().getDurability() == 200) {
			if (player.hasPermission("entitymanager.spawn.crystal")) {
				if(loc.getBlock().getTypeId() == 0) {
				block.getWorld().spawn(loc, EnderCrystal.class);
				return;
				}
			} else {
				player.sendMessage(ChatColor.RED
						+ "You don't have permission to spawn ender crystals");
				return;
			}
		}
	}

	/**
	 * Alerter.
	 * 
	 * @param ev
	 *            the ev
	 */
	public final void alerter(SpawnEggEvent ev) {
		if (Settings.logging) {
			writeWarn("[EntityManager] "
					+ ev.getPlayer().getDisplayName().toLowerCase()
					+ " tried to use an "
					+ ev.getEntityBreed().toString().toLowerCase() + " egg");
		}
		if (Settings.alertz) {
			for (Player p : ev.getPlayer().getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.DARK_RED
							+ ev.getPlayer().getDisplayName()
							+ " tried to use a " + ChatColor.GOLD
							+ ev.getEntityBreed().toString().toLowerCase()
							+ ChatColor.DARK_RED + " egg.");
				}
			}
		}
	}
}
