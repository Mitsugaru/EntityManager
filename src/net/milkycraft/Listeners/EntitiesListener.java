package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Fireball;
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
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class EntitiesListener implements Listener {
	Spawnegg plugin;
	public EntitiesListener(Spawnegg instance) {
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.LOW)
	public void DoorBreak(EntityBreakDoorEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("block.zombie-door-break")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void Enderblock(EntityChangeBlockEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getBlock().getWorld().getName().equals(worldname)) {
				if (plugin.getConfig().getBoolean("block.ender-pickup")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void Damage(EntityDamageByEntityEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
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
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void CreeperXplode(EntityExplodeEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity() instanceof Creeper) {
					if (plugin.getConfig()
							.getBoolean("block.creeper-explosion")) {
						if (e.getYield() > 0)
							;
						e.setYield(0);
						return;
					}
				}
			} else if (e.getEntity() instanceof Fireball) {
				if (plugin.getConfig().getBoolean("block.fireball-explosion")) {
					if (e.getYield() > 0) {
						e.setYield(0);
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void hunger(FoodLevelChangeEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				if (e.getEntity().hasPermission("entitymanager.nohunger")) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
				if(e.getReason() == CreateReason.FIRE) {
					if (plugin.getConfig().getBoolean(
							"block.Creation-of.portals")) {
						e.setCancelled(true);
						return;
					}
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
					} else if (e.getCause() == DamageCause.SUFFOCATION) {
						if (plugin.getConfig().getBoolean(
								"block.suffocation-damage")) {
							e.setDamage(0);
							return;
						}
					} else if (e.getCause() == DamageCause.ENTITY_EXPLOSION) {
						if (plugin.getConfig().getBoolean(
								"block.explosion-damage")) {
							e.setDamage(0);
							return;
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onarrowshoot(EntityShootBowEvent e) {	
		 List<String> worldz = plugin.getConfig().getStringList("World.Worldname");
		if(e.getEntity() instanceof Player) {
			for (String worldname : worldz) {
				if (e.getEntity().getWorld().getName().equals(worldname)) {
			if(plugin.getConfig().getBoolean("block.arrows")) {
				e.setCancelled(true);
				return;
			}
		}
			}
		}
	}
	}
