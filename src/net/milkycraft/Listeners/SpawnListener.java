package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent;

public class SpawnListener implements Listener{
	private boolean cancelled = true;
	Spawnegg plugin;
	public SpawnListener(Spawnegg instance) {
		plugin = instance;
	}	
public void onNaturalSpawn(CreatureSpawnEvent e) {
	List<String> worldz = plugin.getConfig().getStringList(
			"World.Worldname");
	for (String worldname : worldz) {
		if (e.getEntity().getWorld().getName().equals(worldname)) {
			if(e.getSpawnReason() == SpawnReason.NATURAL
					|| e.getSpawnReason() == SpawnReason.CUSTOM
							|| e.getSpawnReason() == SpawnReason.SPAWNER_EGG
					|| e.getSpawnReason() == SpawnReason.SPAWNER
					|| e.getSpawnReason() == SpawnReason.BUILD_IRONGOLEM
					|| e.getSpawnReason() == SpawnReason.BUILD_SNOWMAN
					|| e.getSpawnReason() == SpawnReason.CHUNK_GEN) {
				if(e.getEntityType() == EntityType.CREEPER) {
					if(plugin.getConfig().getBoolean("disabled.mobs.creeper")) {
						e.setCancelled(true);
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
				else if(e.getEntityType() == EntityType.MINECART) {
					if(plugin.getConfig().getBoolean("block.Entities.Minecarts")) {
						e.getEntity().remove();
						alert(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.BOAT) {
					if(plugin.getConfig().getBoolean("block.Entities.Boats")) {
						e.getEntity().remove();
						alert(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.PRIMED_TNT) {
					if(plugin.getConfig().getBoolean("block.Entities.PrimedTNT")) {
						e.getEntity().remove();						
						alert(e);
						return;
					}
				}
				else if(e.getEntityType() == EntityType.FALLING_BLOCK) {
					if(plugin.getConfig().getBoolean("block.Entities.FallingBlocks")) {
						e.getEntity().remove();						
						alert(e);
						return;
					}
				}
			}
		}
		}
	}
public void onTarget(EntityTargetEvent ev, CreatureSpawnEvent e) {
	if(this.blocked(e)) {
		ev.getEntity().remove();
		return;
	}
}
public boolean blocked(CreatureSpawnEvent e) {
		return cancelled;
	}
public void alert(CreatureSpawnEvent e) {
	boolean alertr = plugin.getConfig().getBoolean("send.alerts");
	double x = e.getEntity().getLocation().getX();
	double y = e.getEntity().getLocation().getY();
	double z = e.getEntity().getLocation().getZ();
	int xx = (int) x;
	int yy = (int) y;
	int zz = (int) z;
	if (alertr) {
		Bukkit.broadcast(ChatColor.GREEN + "[EM] " + ChatColor.GOLD + " A " 
				+ e.getEntityType() + " Was removed"+ ChatColor.DARK_RED + " in: "
				+ ChatColor.YELLOW + e.getEntity().getWorld().getName()
				+ " at: " + xx + " , " + yy + " , " + zz + ".",
				"entitymanager.admin");
		return;
	}
}
}