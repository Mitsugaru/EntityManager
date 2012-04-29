/*
 * 
 */
package net.milkycraft.Listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.Spawnegg;
import net.milkycraft.ASEConfiguration.Settings;
import net.milkycraft.Enums.EntityCategory;
import net.milkycraft.Event.SpawnEggEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving mySpawnEgg events.
 * The class that is interested in processing a mySpawnEgg
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMySpawnEggListener<code> method. When
 * the mySpawnEgg event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MySpawnEggEvent
 */
public class MySpawnEggListener implements Listener {

	/** The log. */
	private static Logger log = Logger.getLogger("Minecraft");

	/**
	 * On spawn attempt.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpawnAttempt(PlayerInteractEvent e) {
		/* Variables */
		final Player player = e.getPlayer();
		final String playa = e.getPlayer().getName();
		final int monster = Settings.mons;
		final int animal = Settings.animal;
		final int npc = Settings.npc;		
		final Location loc = e.getPlayer().getLocation();
		List<String> worldz = Settings.worlds;
		SpawnEggEvent seg = new SpawnEggEvent(e);
		/* Done with variables , Null check time*/
		if(e.getItem() == null) {
			return;
			}
		if(e.getClickedBlock() == null) {			
			return;
		}
		if(e.getAction() == Action.LEFT_CLICK_AIR ||
			e.getAction() == Action.RIGHT_CLICK_AIR) {
				return;
			}
		/* Check if player has the node */
		if (player.hasPermission("entitymanager.*")) {
			return;
		}
		
		for (String worldname : worldz) {
			if (e.getPlayer().getWorld().getName().equals(worldname)) {
				if (player.getItemInHand().getTypeId() == 383) {
					/*
					 * 	If player is in a region where they cant build
					 *  Then dont let them use the monster egg
					 *  Prevents people trolling others property 
					 *  If worldguard isnt installed this is just ignored
					 */
					if (!Spawnegg.worldguardPlugin.canBuild(player, loc)) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.YELLOW
								+ "You cant use monster eggs in this region!");
						return;
					}
					/* Check if the block they click with the item isnt a chest, furnace, etc 
					 * Due to the fact that the clicked block can be null (if clicking air, entities, etc)
					 * We gotta define the variable down here */
					final Material mat = e.getClickedBlock().getType();
					if (!(mat == Material.CHEST || mat == Material.FURNACE || mat == Material.DISPENSER)) {
						if (!onDecidingSpawningFactors(seg)) {
							e.setCancelled(true); // Cancel
							e.getPlayer() // Tell player
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
						} else { // Handle egg charges based on the egg trying to be used
							if (!player
									.hasPermission("entitymanager.bypass.charge")
									&& Spawnegg.econ != null) {
								if (seg.getEntityBreed().getCategory() == EntityCategory.ANIMAL) {
									Spawnegg.econ.withdrawPlayer(playa,
											(double) animal);
								} else if (seg.getEntityBreed().getCategory() == EntityCategory.MONSTER) {
									Spawnegg.econ.withdrawPlayer(playa,
											(double) monster);
								} else if (seg.getEntityBreed().getCategory() == EntityCategory.NPC) {
									Spawnegg.econ.withdrawPlayer(playa,
											(double) npc);
								} // end of specific charge block
							} // end of bypass perm charge block
						}  // End of charges 
					} else {
						player.sendMessage(ChatColor.RED + "Opened InventoryHolder with MonsterEgg");
					}
				} // Item check
			} // World
		} // World Iterator
	} // Listener

	/**
	 * On deciding spawning factors.
	 *
	 * @param e the e
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
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getPlayer().getWorld().getName().equals(worldname)) {
				if (e.getEntityBreed().getCategory() != EntityCategory.UNKNOWN) {
					if (Settings.getConfig().getBoolean(
							"disabled.eggs."
									+ e.getEntityBreed().toString()
											.toLowerCase())) {
						if (e.getPlayer().hasPermission(
								"entitymanager."
										+ e.getEntityBreed().toString()
												.toLowerCase())) {
							return true;
						} else {
							return false;
						}
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Spawn ender crystal.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void SpawnEnderCrystal(PlayerInteractEvent e) {
		if(e.getItem() == null) {
			return;
		}
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) {
			return;
		}
		final Location loc = e.getClickedBlock().getLocation();
		final Block block = e.getClickedBlock();
		final Player player = e.getPlayer();
		if (e.getItem().getTypeId() == 383) {
			if(e.getItem().getDurability() == 200) {
			if (e.getPlayer().hasPermission("entitymanager.crystal")) {
				block.getWorld().spawn(loc, EnderCrystal.class);
				return;
			} else {
				e.getPlayer()
						.sendMessage(
								ChatColor.GREEN
										+ "[EM]"
										+ "You don't have permission to place ender crystals!");
				if (Settings.logging) {
					log.log(Level.WARNING, "[EntityManager] "
							+ player.getDisplayName().toLowerCase()
							+ " tried to place a Ender Crystal");
				}
				}
			}
		}
	}

	/**
	 * Alerter.
	 *
	 * @param ev the ev
	 */
	public final void alerter(SpawnEggEvent ev) {
		if (Settings.logging) {
			log.log(Level.WARNING, "[EntityManager] "
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
