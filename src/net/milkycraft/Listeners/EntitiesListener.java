package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

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
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class EntitiesListener implements Listener {
	Spawnegg plugin;

	public EntitiesListener(Spawnegg instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void DoorBreak(EntityBreakDoorEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("block.zombie-door-break")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerDamage(EntityDamageEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Player) {
					if (e.getCause() == DamageCause.FALL) {
						if (plugin.getConfig().getBoolean("block.fall-damage")) {
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
				if(e.getEntity() instanceof Enderman) {
					if (plugin.getConfig().getBoolean("block.ender-pickup")) {
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void Damage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (plugin.getConfig().getBoolean("block.player-damage")) {
				e.setCancelled(true);
				return;
			}
		} else if (e.getEntity() instanceof LivingEntity) {
			if (!(e.getEntity() instanceof Player)) {
				if (plugin.getConfig().getBoolean("block.mob-damage")) {
					e.setCancelled(true);
					return;
				}
			}
		} else if (e.getDamager() instanceof Player) {
			if (e.getEntity() instanceof Player) {
				if (plugin.getConfig().getBoolean("block.pvp")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
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
	public void onPotionSplash(PotionSplashEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("block.splash-potions")) {
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
					 ep = plugin.getConfig().getInt("extra.explosivearrows.power");
					 float p;
					 p = (float)ep;
					Entity shooter = e.getEntity().getShooter();
					Entity player = (Entity) shooter;
					if (shooter instanceof Player) {
						if (plugin.getConfig().getBoolean(
								"extras.enable.explosive-arrows")) {
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

	@EventHandler(priority = EventPriority.NORMAL)
	public void onarrowshoot(EntityShootBowEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		if (e.getEntity() instanceof Player) {
			for (String worldname : worldz) {
				if (e.getEntity().getWorld().getName().equals(worldname)) {
					if (plugin.getConfig().getBoolean("block.arrows")) {
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}
}
