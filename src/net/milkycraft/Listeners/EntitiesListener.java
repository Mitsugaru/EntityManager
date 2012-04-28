package net.milkycraft.Listeners;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.Spawnegg;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class EntitiesListener implements Listener {
	Spawnegg plugin;
	public HashMap<Integer, Player> alerters = new HashMap<Integer, Player>();

	public EntitiesListener(Spawnegg instance) {
		plugin = instance;
	}

	private static Logger log = Logger.getLogger("Minecraft");

	@EventHandler(priority = EventPriority.LOW)
	public void DoorBreak(EntityBreakDoorEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (e.getBlock().getWorld().getDifficulty() == Difficulty.HARD) {
					if (plugin.getConfig().getBoolean(
							"block.Actions.zombie-door-break")) {
						e.setCancelled(true);
					}
					if (plugin.getConfig().getBoolean("EntityManager.Logging")) {
						log.info("A door somewhere was not broken by a zombie!");
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerDamage(EntityDamageEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Player) {
					if (e.getCause() == DamageCause.FALL) {
						final Player p = (Player) e.getEntity();
						if (p.hasPermission("entitymanager.nofall")) {
							e.setDamage(0);
							return;
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void Enderblock(EntityChangeBlockEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Enderman) {
					if (plugin.getConfig().getBoolean(
							"block.Actions.ender-pickup")) {
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void Damage(EntityDamageByEntityEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof LivingEntity) {
					if (!(e.getEntity() instanceof Player)) {
						if (e.getDamager() instanceof Player) {
							final Player p = (Player) e.getDamager();
							if (plugin.getConfig().getBoolean(
									"block.Actions.mob-damage")
									&& !p.hasPermission("entitymanager.mob-damage")) {
								e.setCancelled(true);
								return;
							}
						}
					} else if (e.getDamager() instanceof Player) {
						if (e.getEntity() instanceof Player) {
							if (plugin.getConfig().getBoolean(
									"block.Actions.pvp")) {
								if (e.getDamager() instanceof Player) {
									final Player p = (Player) e.getDamager();
									if (!p.hasPermission("entitymanager.pvp")) {
										e.setCancelled(true);
										p.sendMessage(ChatColor.GREEN
												+ "[EM]"
												+ ChatColor.RED
												+ "PVP is disabled in the world: "
												+ ChatColor.YELLOW
												+ e.getEntity().getWorld()
														.getName() + ".");
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void hunger(FoodLevelChangeEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity().hasPermission("entitymanager.nohunger")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onArrowHit(ProjectileHitEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Arrow) {
					Location loc = e.getEntity().getLocation();
					int ep;
					ep = plugin.getConfig().getInt(
							"extra.explosivearrows.power");
					float p;
					p = (float) ep;
					Entity shooter = e.getEntity().getShooter();
					Entity player = (Entity) shooter;
					if (shooter instanceof Player) {
						Player playa = (Player) e.getEntity().getShooter();
						if (playa
								.hasPermission("entitymanager.explosivearrows")) {
							player.getWorld().createExplosion(loc, p);
							return;
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
				if (e.getReason() == CreateReason.FIRE) {
					if (plugin.getConfig().getBoolean(
							"block.Creation-of.portals")) {
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPaintingPlace(PaintingPlaceEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("block.Entities.Paintings")
						&& !e.getPlayer().hasPermission(
								"entitymanager.painting")) {
					e.setCancelled(true);
					e.getPlayer()
							.sendMessage(
									ChatColor.GREEN
											+ "[EM] "
											+ ChatColor.RED
											+ "You dont have permission to place paintings");
					if (plugin.getConfig().getBoolean("EntityManager.Logging")) {
						log.info(e.getPlayer().getDisplayName()
								+ " tried to place a painting");
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPigZap(PigZapEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getPigZombie().getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("disabled.mobs.pig_zombie")) {
					e.setCancelled(true);
					e.getEntity().remove();
					if (plugin.getConfig().getBoolean("EntityManager.Logging")) {
						Spawnegg.log
								.log(Level.WARNING,
										"[EM] A pig was zapped but pigmans are disabled! Removing pig!");
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFishingAttempt(PlayerFishEvent e) {
		final Player player = e.getPlayer();
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (player.getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("block.Actions.fishing")
						&& !player.hasPermission("entitymanager.fishing")) {
					e.setCancelled(true);
					player.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.RED
							+ "You dont have permission to fish");
					if (plugin.getConfig().getBoolean("EntityManager.Logging")) {
						log.info(e.getPlayer().getDisplayName()
								+ " tried to fish");
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onarrowshoot(EntityShootBowEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Player) {
					final Player p = (Player) e.getEntity();
					if (plugin.getConfig().getBoolean("block.Actions.arrows")
							&& !p.hasPermission("entitymanager.arrows")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
								+ "You dont have permission to shoot arrows");
						return;
					}
				}
			}
		}
	}
}
