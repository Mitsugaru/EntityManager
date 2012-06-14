package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;
import net.milkycraft.permissions.PermissionHandler;
import net.milkycraft.permissions.PermissionNode;

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

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving entities events. The class that is
 * interested in processing a entities event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addEntitiesListener<code> method. When
 * the entities event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see EntitiesEvent
 */
public class EntitiesListener extends EntityManager implements Listener {

	/**
	 * Door break.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void DoorBreak(EntityBreakDoorEvent e) {
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			if (Settings.doorBreak) {
				e.setCancelled(true);
				return;
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
			if (Settings.world
					|| WorldSettings.worlds.contains(e.getEntity().getWorld()
							.getName())) {
				if (e.getEntity() instanceof Player) {
					final Player p = (Player) e.getEntity();
					if (PermissionHandler.has(p, PermissionNode.NOFALL)) {
						e.setDamage(0);
						return;
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
		if (Settings.enderPickup) {
			if (e.getEntity() instanceof Enderman) {
				if (e.getTo().getId() == 0) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	/**
	 * <p>
	 * Event to regular pvp and attacking mobs.
	 * <p>
	 * <p>
	 * Is a rather non efficient method... rewrite needed
	 * <p>
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityRecievingDamage(EntityDamageByEntityEvent e) {
		final Entity Damaged = e.getEntity();
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			if (Damaged instanceof LivingEntity) {
				if (!(Damaged instanceof Player)) {
					if (e.getDamager() instanceof Player) {
						final Player p = (Player) e.getDamager();
						if (Settings.mobdmg
								&& !PermissionHandler.has(p,
										PermissionNode.MOB_DAMAGE)) {
							e.setCancelled(true);
							return;
						}
					} else if (e.getDamager() instanceof Player) {
						if (e.getEntity() instanceof Player) {
							if (Settings.pvp) {
								if (e.getDamager() instanceof Player) {
									final Player p = (Player) e.getDamager();
									if (!PermissionHandler.has(p,
											PermissionNode.ALLOW_PVP)) {
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
	public void onFoodChange(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			if (PermissionHandler.has((Player) e.getEntity(),
					PermissionNode.ADMIN)) {
				if (Settings.world
						|| WorldSettings.worlds.contains(e.getEntity()
								.getWorld().getName())) {
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getWorld().getName())) {
			if (e.getReason() == CreateReason.FIRE) {
				if (Settings.portals) {
					e.setCancelled(true);
					return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getBlock().getWorld()
						.getName())) {
			if (Settings.paintz
					&& !PermissionHandler.has(e.getPlayer(),
							PermissionNode.ALLOW_PAINTINGS)) {
				e.setCancelled(true);
				e.getPlayer()
						.sendMessage(
								ChatColor.GREEN
										+ "[EM] "
										+ ChatColor.RED
										+ "You dont have permission to place paintings");
				if (Settings.logging) {
					writeLog(e.getPlayer().getDisplayName()
							+ " tried to place a painting");
					return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			if (Settings.getConfig().getBoolean("disabled.mobs.pig_zombie")) {
				e.setCancelled(true);
				e.getEntity().remove();
				if (Settings.logging) {
					writeLog("[EM] pigmans disabled so zapped pig was removed");
					return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getPlayer().getWorld()
						.getName())) {
			if (Settings.fishing
					&& !PermissionHandler.has(e.getPlayer(),
							PermissionNode.ALLOW_FISHING)) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
						+ "You dont have permission to fish");
				if (Settings.logging) {
					writeLog(e.getPlayer().getDisplayName() + " tried to fish");
					return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			if (e.getEntity() instanceof Player) {
				final Player p = (Player) e.getEntity();
				if (Settings.arrowz
						&& !PermissionHandler.has(p,
								PermissionNode.ALLOW_ARROWS)) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.GREEN + "[EM] " + ChatColor.RED
							+ "You dont have permission to shoot arrows");
					return;
				}
			}
		}
	}

	/**
	 * On crop destroy.
	 * 
	 * @param e
	 *            the event
	 */
	public void onCropDestroy(EntityInteractEvent e) {
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			if (Settings.godcrops) {
				e.setCancelled(true);
				return;
			}
		}
	}

}
