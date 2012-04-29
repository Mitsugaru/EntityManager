/*
 * 
 */
package net.milkycraft.Listeners;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.Spawnegg;
import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
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
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving entities events.
 * The class that is interested in processing a entities
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEntitiesListener<code> method. When
 * the entities event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EntitiesEvent
 */
public class EntitiesListener implements Listener {
	
	/** The alerters. */
	public HashMap<Integer, Player> alerters = new HashMap<Integer, Player>();
	
	/** The log. */
	private static Logger log = Logger.getLogger("Minecraft");

	/**
	 * Door break.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void DoorBreak(EntityBreakDoorEvent e) {
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (e.getBlock().getWorld().getDifficulty() == Difficulty.HARD) {
					if (Settings.doorBreak) {
						e.setCancelled(true);
					}
					if (Settings.logging) {
						log.info("A door somewhere was not broken by a zombie!");
						return;
					}
				}
			}
		}
	}

	/**
	 * Player damage.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerDamage(EntityDamageEvent e) {
		final List<String> worldz = Settings.worlds;
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

	/**
	 * Enderblock.
	 *
	 * @param e the e
	 */
	@EventHandler
	public void Enderblock(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof Enderman) {
			if (Settings.enderPickup) {
				e.setCancelled(true);
				return;
			}
		}
	}

	/**
	 * Damage.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void Damage(EntityDamageByEntityEvent e) {
		final Entity Damaged = e.getEntity();
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (Damaged.getWorld().getName().equals(worldname)) {
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
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void hunger(FoodLevelChangeEvent e) {
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
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
	 * @param e the e
	 */
	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
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
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPaintingPlace(PaintingPlaceEvent e) {
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
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

	/**
	 * On pig zap.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPigZap(PigZapEvent e) {
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getPigZombie().getWorld().getName().equals(worldname)) {
				if (Settings.getConfig().getBoolean("disabled.mobs.pig_zombie")) {
					e.setCancelled(true);
					e.getEntity().remove();
					if (Settings.logging) {
						Spawnegg.log
								.log(Level.WARNING,
										"[EM] A pig was zapped but pigmans are disabled! Removing pig!");
					}
				}
			}
		}
	}

	/**
	 * On fishing attempt.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onFishingAttempt(PlayerFishEvent e) {
		final Player player = e.getPlayer();
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (player.getWorld().getName().equals(worldname)) {
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

	/**
	 * Onarrowshoot.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onarrowshoot(EntityShootBowEvent e) {
		final List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
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
}
