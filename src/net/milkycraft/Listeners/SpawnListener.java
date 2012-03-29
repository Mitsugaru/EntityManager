package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

import org.bukkit.entity.EntityType;
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
	@EventHandler(priority = EventPriority.HIGH)
public void onNaturalSpawn(CreatureSpawnEvent e) {
	List<String> worldz = plugin.getConfig().getStringList(
			"World.Worldname");
	for (String worldname : worldz) {
		if (e.getEntity().getWorld().getName().equals(worldname)) {
				if(e.getEntityType() == EntityType.CREEPER) {
					if(plugin.getConfig().getBoolean("disabled.mobs.creeper")) {
						e.getEntity().remove();
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SKELETON) {
					if(plugin.getConfig().getBoolean("disabled.mobs.skeleton")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SPIDER) {
					if(plugin.getConfig().getBoolean("disabled.mobs.spider")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.ZOMBIE) {
					if(plugin.getConfig().getBoolean("disabled.mobs.zombie")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SLIME) {
					if(plugin.getConfig().getBoolean("disabled.mobs.slime")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.GHAST) {
					if(plugin.getConfig().getBoolean("disabled.mobs.ghast")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.PIG_ZOMBIE) {
					if(plugin.getConfig().getBoolean("disabled.mobs.pigman")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.ENDERMAN) {
					if(plugin.getConfig().getBoolean("disabled.mobs.enderman")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.CAVE_SPIDER) {
					if(plugin.getConfig().getBoolean("disabled.mobs.cavespider")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SILVERFISH) {
					if(plugin.getConfig().getBoolean("disabled.mobs.silverfish")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.BLAZE) {
					if(plugin.getConfig().getBoolean("disabled.mobs.blaze")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.MAGMA_CUBE) {
					if(plugin.getConfig().getBoolean("disabled.mobs.magmacube")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.PIG) {
					if(plugin.getConfig().getBoolean("disabled.mobs.pig")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SHEEP) {
					if(plugin.getConfig().getBoolean("disabled.mobs.sheep")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.COW) {
					if(plugin.getConfig().getBoolean("disabled.mobs.cow")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.CHICKEN) {
					if(plugin.getConfig().getBoolean("disabled.mobs.chicken")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SQUID) {
					if(plugin.getConfig().getBoolean("disabled.mobs.squid")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.WOLF) {
					if(plugin.getConfig().getBoolean("disabled.mobs.wolf")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.MUSHROOM_COW) {
					if(plugin.getConfig().getBoolean("disabled.mobs.mooshroom")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.SNOWMAN) {
					if(plugin.getConfig().getBoolean("disabled.mobs.snowgolem")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.OCELOT) {
					if(plugin.getConfig().getBoolean("disabled.mobs.ocelot")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.IRON_GOLEM) {
					if(plugin.getConfig().getBoolean("disabled.mobs.irongolem")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.VILLAGER) {
					if(plugin.getConfig().getBoolean("disabled.mobs.villager")) {
						e.setCancelled(true);
						blocked(e);
						return;
					}
				}
			}
		}
		}
	@EventHandler(priority = EventPriority.HIGH)
public void onTarget(EntityTargetEvent ev, CreatureSpawnEvent e) {
	if(this.blocked(e)) {
		ev.getEntity().remove();
		return;
	}
}
public boolean blocked(CreatureSpawnEvent e) {
		return cancelled;
	}
}