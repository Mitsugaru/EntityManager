package net.milkycraft.Listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.Spawnegg;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ThrowListener implements Listener {
	Spawnegg plugin;	
	public ThrowListener(Spawnegg instance) {
		plugin = instance;
	}
	public final Logger log = Logger.getLogger("Minecraft");

	@EventHandler(priority = EventPriority.LOWEST)
	public void OnThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		if (e.getItem() == null) {
			return;
		}
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getPlayer().getWorld().getName().equals(worldname)) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK
						|| e.getAction() == Action.RIGHT_CLICK_AIR) {
					if (e.getItem().getTypeId() == 384) {
						if (plugin.getConfig().getBoolean(
								"block.Throw.XpBottles")
								&& !player
										.hasPermission("entitymanager.xpbottles")) {
							e.setCancelled(true);
							alerter(e);
							messager(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to throw a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 385) {
						if (plugin.getConfig().getBoolean(
								"block.Throw.FireCharges")
								&& !player
										.hasPermission("entitymanager.firecharges")) {
							e.setCancelled(true);
							alerter(e);
							messager(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to throw a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 344) {
						if (plugin.getConfig().getBoolean(
								"block.Throw.ChickenEggs")
								&& !player
										.hasPermission("entitymanager.chickeneggs")) {
							e.setCancelled(true);
							messager(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to throw a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 368) {
						if (plugin.getConfig().getBoolean(
								"block.Throw.EnderPearls")
								&& !player
										.hasPermission("entitymanager.enderpearls")) {
							e.setCancelled(true);
							messager(e);
							alerter(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to throw a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 381) {
						if (plugin.getConfig().getBoolean(
								"block.Throw.EnderEyes")
								&& !player
										.hasPermission("entitymanager.endereyes")) {
							e.setCancelled(true);
							messager(e);
							alerter(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to throw a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 333) {
						if (plugin.getConfig().getBoolean(
								"block.Entities.Boats")
								&& !player.hasPermission("entitymanager.boats")) {
							e.setCancelled(true);
							messager(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to use a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 328) {
						if (plugin.getConfig().getBoolean(
								"block.Entities.Minecarts")
								&& !player
										.hasPermission("entitymanager.minecarts")) {
							e.setCancelled(true);
							messager(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to use a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					} else if (e.getItem().getTypeId() == 373) {
						if (plugin.getConfig()
								.getBoolean("block.Throw.Potions")
								&& !player
										.hasPermission("entitymanager.potions")) {
							e.setCancelled(true);
							messager(e);
						}
						if (plugin.getConfig().getBoolean(
								"EntityManager.Logging")) {
							log.log(Level.WARNING, "[EntityManager] "
									+ e.getPlayer().getDisplayName()
											.toLowerCase()
									+ " tried to throw a "
									+ e.getItem().getType().toString()
											.toLowerCase());
							return;
						}
					}
					if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						Location loc = e.getClickedBlock().getLocation();
						if (e.getItem().getTypeId() == 383) {
							if (e.getItem().getDurability() == 200) {
								if (e.getPlayer().hasPermission(
										"entitymanager.crystal")) {
									e.getClickedBlock().getWorld()
											.spawn(loc, EnderCrystal.class);
									return;
								} else {
									e.getPlayer()
											.sendMessage(
													ChatColor.GREEN
															+ "[EM]"
															+ "You don't have permission to place ender crystals!");
								}
								if (plugin.getConfig().getBoolean(
										"EntityManager.Logging")) {
									log.log(Level.WARNING, "[EntityManager] "
											+ e.getPlayer().getDisplayName()
													.toLowerCase()
											+ " tried to place a Ender Crystal");
								}
							}
						}
					}
				}
			}
		}
	}
	public void alerter(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		if(plugin.getConfig().getBoolean("Send-Alerts")) {
			for(Player p : player.getServer().getOnlinePlayers()) {
				if(p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.DARK_RED
				+ e.getPlayer().getDisplayName() + " tryed to use a "
				+ ChatColor.GOLD + e.getItem().getType().toString().toLowerCase() + ".");
				}
			}
		}
	}
	public void messager(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		player.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED + "You dont have permission for " + e.getItem().getType().toString().toLowerCase() + "'s.");  
	}
}