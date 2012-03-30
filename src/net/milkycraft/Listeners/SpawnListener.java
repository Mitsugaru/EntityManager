package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class SpawnListener implements Listener{
	private boolean cancelled = true;
	Spawnegg plugin;
	public SpawnListener(Spawnegg instance) {
		plugin = instance;
	}	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
public void onSpawn(CreatureSpawnEvent e) {
		 if(e.isCancelled()) {
			 return;
	}
	List<String> worldz = plugin.getConfig().getStringList(
			"World.Worldname");
	for (String worldname : worldz) {
		if (e.getEntity().getWorld().getName().equals(worldname)) {
			if(e.getEntity() instanceof LivingEntity) {
				if(e.getEntity() instanceof Creeper) {
					if(plugin.getConfig().getBoolean("disabled.mobs.creeper")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity()instanceof Skeleton) {
					if(plugin.getConfig().getBoolean("disabled.mobs.skeleton")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Spider) {
					if(plugin.getConfig().getBoolean("disabled.mobs.spider")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Zombie) {
					if(plugin.getConfig().getBoolean("disabled.mobs.zombie")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Slime) {
					if(plugin.getConfig().getBoolean("disabled.mobs.slime")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Ghast) {
					if(plugin.getConfig().getBoolean("disabled.mobs.ghast")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof PigZombie) {
					if(plugin.getConfig().getBoolean("disabled.mobs.pigman")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Enderman) {
					if(plugin.getConfig().getBoolean("disabled.mobs.enderman")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof CaveSpider) {
					if(plugin.getConfig().getBoolean("disabled.mobs.cavespider")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Silverfish) {
					if(plugin.getConfig().getBoolean("disabled.mobs.silverfish")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Ghast) {
					if(plugin.getConfig().getBoolean("disabled.mobs.blaze")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof MagmaCube) {
					if(plugin.getConfig().getBoolean("disabled.mobs.magmacube")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Pig) {
					if(plugin.getConfig().getBoolean("disabled.mobs.pig")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Sheep) {
					if(plugin.getConfig().getBoolean("disabled.mobs.sheep")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Cow) {
					if(plugin.getConfig().getBoolean("disabled.mobs.cow")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Chicken) {
					if(plugin.getConfig().getBoolean("disabled.mobs.chicken")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Ghast) {
					if(plugin.getConfig().getBoolean("disabled.mobs.squid")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Wolf) {
					if(plugin.getConfig().getBoolean("disabled.mobs.wolf")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof MushroomCow) {
					if(plugin.getConfig().getBoolean("disabled.mobs.mooshroom")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Snowman) {
					if(plugin.getConfig().getBoolean("disabled.mobs.snowgolem")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Ocelot) {
					if(plugin.getConfig().getBoolean("disabled.mobs.ocelot")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof IronGolem) {
					if(plugin.getConfig().getBoolean("disabled.mobs.irongolem")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Villager) {
					if(plugin.getConfig().getBoolean("disabled.mobs.villager")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntity() instanceof Villager) {
					if(plugin.getConfig().getBoolean("disabled.mobs.villager")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
			}
		}
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
public void onTarget(EntityTargetEvent ev, CreatureSpawnEvent e) {
	if(this.blocked(e)) {
		ev.getEntity().remove();
		Spawnegg.log.warning("Blocked Mob was forcibly removed on target");
		return;
	}
}
public boolean blocked(CreatureSpawnEvent e) {
		return cancelled;
	}
}