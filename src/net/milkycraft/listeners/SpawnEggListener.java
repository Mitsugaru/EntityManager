package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;
import net.milkycraft.permissions.PermissionHandler;
import net.milkycraft.permissions.PermissionNode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnEggListener extends EntityManager implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSpawnAttempt(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}
		if (e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.LEFT_CLICK_BLOCK) {
			return;
		}
		if (e.getClickedBlock() == null) {
			return;
		}
		 Location loc = e.getClickedBlock().getLocation().add(0, 1, 0);
		final String playa = e.getPlayer().getName();

		if (e.getItem().getTypeId() == 383) {
			if (!worldguardPlugin.canBuild(e.getPlayer(), loc)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.YELLOW
								+ "You cant use spawner eggs in this region!");
				return;
			}
		}
		if (e.getItem().getTypeId() == 383) {
			if (WorldSettings.worlds.contains(e.getPlayer().getWorld()
					.getName())) {
				switch (e.getItem().getDurability()) {
				case 9:
					spawnPainting(e);
					return;
				case 12:
					e.getClickedBlock()
							.getWorld()
							.spawn(e.getClickedBlock().getLocation()
									.add(0, 2, 0), Fireball.class);
					return;
				case 20:
					if (e.getPlayer().hasPermission(
							"entitymanager.spawn.primedtnt")) {
						e.getPlayer().getWorld().spawn(loc.add(0, 1, 0), TNTPrimed.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				case 17:
					spawnExp(e);
					return;
				case 40:
					if (e.getPlayer().hasPermission(
							"entitymanager.spawn.minecart")) {
						e.getPlayer().getWorld().spawn(loc, Minecart.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				case 41:
					if (e.getPlayer().hasPermission("entitymanager.spawn.boat")) {
						e.getPlayer().getWorld().spawn(loc, Boat.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				case 50:
					if (Settings.creep) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.creeper"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);					
						}
				case 51:
					if (Settings.skele) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.skeleton"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 52:
					if (Settings.spider) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.spider"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 53:
					if (e.getPlayer()
							.hasPermission("entitymanager.spawn.giant")) {
						e.getPlayer().getWorld().spawn(loc, Giant.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				case 54:
					if (Settings.zombie) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.zombie"))) {
							e.setCancelled(true);
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 55:
					if (Settings.slime) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.slime"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 56:
					if (Settings.ghast) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.ghast"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 57:
					if (Settings.pigman) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.pigman"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 58:
					if (Settings.ender) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.enderman"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 59:
					if (Settings.cave) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.cavespider"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 60:
					if (Settings.fish) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.silverfish"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 61:
					if (Settings.blaze) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.blaze"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 62:
					if (Settings.cube) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.magmacube"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.mons);		
					}
				case 63:
					if (e.getPlayer().hasPermission(
							"entitymanager.spawn.dragon")) {
						e.getPlayer().getWorld().spawn(loc, EnderDragon.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}

				case 90:
					if (Settings.pig) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.pig"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);		
					}
				case 91:
					if (Settings.sheep) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.sheep"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 92:
					if (Settings.cow) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.cow"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 93:
					if (Settings.chick) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.chicken"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 94:
					if (Settings.squid) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.squid"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 95:
					if (Settings.wolf) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.wolf"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 96:
					if (Settings.moosh) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.mooshroom"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 97:
					if (e.getPlayer().hasPermission(
							"entitymanager.spawn.snowgolem")) {
						e.getPlayer().getWorld().spawn(loc, Snowman.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				case 98:
					if (Settings.ocelot) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.ocelot"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.animal);	
					}
				case 99:
					if (e.getPlayer().hasPermission(
							"entitymanager.spawn.irongolem")) {
						e.getPlayer().getWorld().spawn(loc, IronGolem.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				case 120:
					if (Settings.villa) {
						if (!(e.getPlayer()
								.hasPermission("entitymanager.spawn.villager"))) {
							e.setCancelled(true);
							handle(e);
							return;
						}
						econ.withdrawPlayer(playa, (double) Settings.npc);	
					}
				case 200:
					if (e.getPlayer().hasPermission(
							"entitymanager.spawn.crystal")) {
						e.getPlayer().getWorld().spawn(loc, EnderCrystal.class);
						if (!PermissionHandler.has(e.getPlayer(),
								PermissionNode.BYPASS_CHARGE) && econ != null) {
							econ.withdrawPlayer(playa, (double) Settings.mons);
						}
						return;
					} else {
						handle(e);
					}
				default:
				}

			}
		}
	}

	private void spawnExp(PlayerInteractEvent e) {
		try {
			e.getPlayer()
					.getWorld()
					.spawn(e.getClickedBlock().getLocation().add(0, 2, 0),
							org.bukkit.entity.ThrownExpBottle.class);
		} catch (Exception ve) {

		}
	}

	private void spawnPainting(PlayerInteractEvent e) {
		try {
			e.getPlayer()
					.getWorld()
					.spawn(e.getClickedBlock().getRelative(BlockFace.SELF)
							.getLocation(), Painting.class);
		} catch (Exception ve) {

		}
	}

	public void handle(PlayerInteractEvent e) {
		e.getPlayer().sendMessage(
				ChatColor.RED + "You dont have permission to spawn that mob!");
	}

	public void alerter(PlayerInteractEvent ev) {
		if (Settings.logging) {
			writeLog("[EntityManager] "
					+ ev.getPlayer().getDisplayName().toLowerCase()
					+ " tried to use an "
					+ ev.getItem().getType().toString().toLowerCase() + " egg");
		}
		if (Settings.alertz) {
			for (Player p : ev.getPlayer().getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.DARK_RED
							+ ev.getPlayer().getDisplayName()
							+ " tried to use a " + ChatColor.GOLD
							+ ev.getItem().getType().toString().toLowerCase()
							+ " egg.");
				}
			}
		}
	}
}