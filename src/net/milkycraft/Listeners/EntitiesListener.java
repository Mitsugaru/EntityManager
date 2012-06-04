package net.milkycraft.listeners;

import java.util.List;
import java.util.logging.Logger;

import net.milkycraft.configuration.Settings;

import org.bukkit.ChatColor;
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
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class EntitiesListener implements Listener {
	/** The log. */
	private static Logger log = Logger.getLogger("Minecraft");
	private final List<String> worldz = Settings.worlds;
	/**
	 * Door break.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void DoorBreak(EntityBreakDoorEvent e) {
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)
					|| Settings.world) {
				if (Settings.doorBreak) {
					e.setCancelled(true);
				}
			}
		}
	}

	/**
	 * Player damage.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerDamage(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			for (String worldname : worldz) {
				if (e.getEntity().getWorld().getName().equals(worldname)
						|| Settings.world) {
					if (e.getEntity() instanceof Player) {
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

	/**
	 * Block enderman from picking up blocks.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onEndermanPickUpBlock(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof Enderman) {
			if (e.getTo().getId() == 0) {
				if (Settings.enderPickup) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	/**
	 * Damage.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityRecievingDamage(EntityDamageByEntityEvent e) {
		final Entity Damaged = e.getEntity();
		for (String worldname : worldz) {
			if (Damaged.getWorld().getName().equals(worldname)
					|| Settings.world) {
				if (Damaged instanceof LivingEntity) {
					if (!(Damaged instanceof Player)) {
						if (e.getDamager() instanceof Player) {
							final Player p = (Player) e.getDamager();
							if (Settings.mobdmg
									&& !p.hasPermission("entitymanager.mob-damage")) {
								e.setCancelled(true);
								return;
							}
						}
					} else if (e.getDamager() instanceof Player) {
						if (e.getEntity() instanceof Player) {
							if (Settings.pvp) {
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

	/**
	 * Hunger.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void hunger(FoodLevelChangeEvent e) {
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)
					|| Settings.world) {
				if (e.getEntity().hasPermission("entitymanager.nohunger")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	/**
	 * On portal create.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname) || Settings.world) {
				if (e.getReason() == CreateReason.FIRE) {
					if (Settings.portals) {
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	/**
	 * On painting place.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPaintingPlace(PaintingPlaceEvent e) {
		if (!Settings.world) {
			for (String worldname : worldz) {
				if (!e.getPlayer().getWorld().getName().equals(worldname)) {
					return;
				} else {
					if (Settings.paintz
							&& !e.getPlayer().hasPermission(
									"entitymanager.painting")) {
						e.setCancelled(true);
						e.getPlayer()
								.sendMessage(
										ChatColor.GREEN
												+ "[EM] "
												+ ChatColor.RED
												+ "You dont have permission to place paintings");
						if (Settings.logging) {
							log.info(e.getPlayer().getDisplayName()
									+ " tried to place a painting");
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * Retarded check, sort of a waste of space. 
	 * 
	 * @param e
	 *            the e
	 */
	public void onPigZap(PigZapEvent e) {
		for (String wname : worldz) {
			if (Settings.world
					|| e.getEntity().getWorld().getName().equals(wname)) {
				if (Settings.getConfig().getBoolean("disabled.mobs.pig_zombie")) {
					e.setCancelled(true);
					e.getEntity().remove();
					if (Settings.logging) {
						log.info("[EM] pigmans disabled so zapped pig was removed");
					}
				}
			}
		}
	}

	/**
	 * On fishing attempt.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onFishingAttempt(PlayerFishEvent e) {
		final Player player = e.getPlayer();
		if (!Settings.world) {
			for (String worldname : worldz) {
				if (!player.getWorld().getName().equals(worldname)) {
					return;
				} else {
					if (Settings.fishing
							&& !player.hasPermission("entitymanager.fishing")) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.GREEN + "[EM] "
								+ ChatColor.RED
								+ "You dont have permission to fish");
						if (Settings.logging) {
							log.info(e.getPlayer().getDisplayName()
									+ " tried to fish");
						}
					}
				}
			}
		}
	}

	/**
	 * Onarrowshoot.
	 * 
	 * @param e
	 *            the e
	 */
	public void onArrowShoot(EntityShootBowEvent e) {
		for (String worldname : worldz) {
			if (Settings.world
					|| e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Player) {
					final Player p = (Player) e.getEntity();
					if (Settings.arrowz
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

	public void onCropDestroy(EntityInteractEvent event) {
			for (String worldname : worldz) {
				if (Settings.world || event.getBlock().getWorld().getName().equals(worldname)) {
					if (Settings.godcrops) {
						event.setCancelled(true);
					}
			}
		}
	}

}

