package net.milkycraft.Listeners;

import java.util.List;
import java.util.logging.Level;

import net.milkycraft.Spawnegg;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class SpawnListener implements Listener {
	Spawnegg plugin;
	public SpawnListener(Spawnegg instance) {
		plugin = instance;
	}

	private int killed = 0;
	private int dilled = 0;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSpawn(CreatureSpawnEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				e.setCancelled(plugin.getConfig().getBoolean(
						"disabled.mobs." +e.getEntityType().toString().toLowerCase()));
				return;
			}
		}
	}

	/*
	 * active mob removal system Chiefly removes mobs that were spawned before
	 * they were blocked in config logs message to config every 5 mobs that get
	 * removed
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetEvent e) {
		final String mob = e.getEntityType().toString().toLowerCase();
		if (plugin.getConfig().getBoolean("disabled.mobs." + mob)) {
			Entity target = e.getTarget();
			if (target instanceof Player) {
				e.getEntity().remove();
				++killed;
				if (killed >= 10) {
					Spawnegg.log
							.log(Level.INFO,
									"[EM]Some "
											+ mob
											+ "'s were forcibly removed because they are disabled");
					killed -= 10;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockDamage(EntityDamageEvent e) {
		final String mob = e.getEntityType().toString().toLowerCase();
		if (plugin.getConfig().getBoolean("disabled.mobs." + mob)) {
			e.getEntity().remove();
			++dilled;
			if (dilled > 10) {
				Spawnegg.log.log(Level.INFO, "[EM]Some " + mob
						+ "'s were forcibly removed because they are disabled");
				dilled -= 10;
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkLoad(ChunkLoadEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
				// Iterate through the entities in the chunk
				for (Entity en : e.getChunk().getEntities()) {
					final String mob = en.getType().toString().toLowerCase();
					if (plugin.getConfig().getBoolean("disabled.mobs." + mob)) {
						try {
							en.remove();
						} catch (NullPointerException ex) {
							Spawnegg.log.log(Level.WARNING, ex.getMessage());
						}
					}
				}
			}
		}
	}	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkUnLoad(ChunkUnloadEvent e) {
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
				// Iterate through the entities in the chunk
				for (Entity en : e.getChunk().getEntities()) {
					final String mob = en.getType().toString().toLowerCase();
					if (plugin.getConfig().getBoolean("disabled.mobs." + mob)) {
						try {
							en.remove();
						} catch (NullPointerException ex) {
							Spawnegg.log.log(Level.WARNING, ex.getMessage());
						}
					}
				}
			}
		}
	}
}