package net.milkycraft.Listeners;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.Spawnegg;
import net.milkycraft.Enums.EntityCategory;
import net.milkycraft.Event.SpawnEggEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/*
 * Supremely re-written with help from the NoSpawnEgg Source code 
 * written by GooseMonkey - I mainly implemented it here
 * 
 */
public class MySpawnEggListener implements Listener {
	Spawnegg plugin;

	public MySpawnEggListener(Spawnegg instance) {
		plugin = instance;
	}

	private static Logger log = Logger.getLogger("Minecraft");

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				|| e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getPlayer().getItemInHand().getTypeId() == 383) {
				this.OnWorldGuardCheck(e);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void OnWorldGuardCheck(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		final Location loc = e.getPlayer().getLocation();
		if(player.getItemInHand().getTypeId() == 383) {
		if (!Spawnegg.worldguardPlugin.canBuild(player, loc)) {
			e.setCancelled(true);
			player.sendMessage(ChatColor.YELLOW
					+ "You cant use monster eggs in this region!");
		}
		} else {
			this.onSpawnAttempt(e);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpawnAttempt(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		final String playa = e.getPlayer().getName();
		final int monster = plugin.getConfig().getInt("Economy.charge.monster");
		final int animal = plugin.getConfig().getInt("Economy.charge.animal");
		final int npc = plugin.getConfig().getInt("Economy.charge.npc");
		if (player.hasPermission("entitymanager.*")) {
			return;
		}
		SpawnEggEvent seg = new SpawnEggEvent(e);
		if(player.getItemInHand().getTypeId() == 383) {
		if (!onDecidingSpawningFactors(seg)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					ChatColor.GREEN + "[EM]" + ChatColor.RED
							+ "You dont have permission to use "
							+ ChatColor.YELLOW
							+ seg.getEntityBreed().toString().toLowerCase()
							+ ChatColor.RED + " eggs");
		}
		} else {
			if (!player.hasPermission("entitymanager.bypass.charge")
					&& Spawnegg.econ != null) {
				if (seg.getEntityBreed().getCategory() == EntityCategory.ANIMAL) {
					Spawnegg.econ.withdrawPlayer(playa, (double) animal);
				} else if (seg.getEntityBreed().getCategory() == EntityCategory.MONSTER) {
					Spawnegg.econ.withdrawPlayer(playa, (double) monster);
				} else if (seg.getEntityBreed().getCategory() == EntityCategory.NPC) {
					Spawnegg.econ.withdrawPlayer(playa, (double) npc);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
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
			if (plugin.getConfig().getBoolean(
					"disabled.eggs."
							+ e.getEntityBreed().toString().toLowerCase())) {
				if (e.getPlayer().hasPermission(
						"entitymanager."
								+ e.getEntityBreed().toString().toLowerCase())) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	public void alerter(PlayerInteractEvent e, SpawnEggEvent ev) {
		if (plugin.getConfig().getBoolean("EntityManager.Logging")) {
			log.log(Level.WARNING, "[EntityManager] "
					+ e.getPlayer().getDisplayName().toLowerCase()
					+ " tried to use an "
					+ ev.getEntityBreed().toString().toLowerCase() + " egg");
		}
		if (plugin.getConfig().getBoolean("Send-Alerts")) {
			for (Player p : e.getPlayer().getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.DARK_RED
							+ e.getPlayer().getDisplayName()
							+ " tried to use a " + ChatColor.GOLD
							+ ev.getEntityBreed().toString().toLowerCase()
							+ " egg.");
				}
			}
		}
	}
}

