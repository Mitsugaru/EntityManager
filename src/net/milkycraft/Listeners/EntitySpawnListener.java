package net.milkycraft.listeners;

import java.util.List;

import net.milkycraft.configuration.Settings;

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

public class EntitySpawnListener implements Listener {

	private final List<String> worldz = Settings.worlds;
	/**
	 * On spawn.
	 * 
	 * @param e
	 *            the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpawn(CreatureSpawnEvent e) {
		for (String worldname : worldz) {
			if (Settings.world
					|| e.getEntity().getWorld().getName().equals(worldname)) {
				e.setCancelled(Settings.getConfig().getBoolean(
						"disabled.mobs."
								+ e.getEntityType().toString().toLowerCase()));
				return;
			}
		}
	}

	/*
	 * active mob removal system Chiefly removes mobs that were spawned before
	 * they were blocked in config logs message to config every 5 mobs that get
	 * removed
	 */
	/**
	 * On target.
	 * 
	 * @param e
	 *            the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetEvent e) {
		if (!Settings.amrs) {
			return;
		}
		for (String worldname : worldz) {
			if (Settings.world
					|| e.getEntity().getWorld().getName().equals(worldname)) {
				final String mob = e.getEntityType().toString().toLowerCase();
				if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
					final Entity target = e.getTarget();
					if (target instanceof Player) {
						e.getEntity().remove();
					}
				}
			}
		}
	}

	/**
	 * On block damage.
	 * 
	 * @param e
	 *            the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockedMobDamage(EntityDamageEvent e) {
		if (!Settings.amrs) {
			return;
		}
		for (String worldname : worldz) {
			if (Settings.world
					|| e.getEntity().getWorld().getName().equals(worldname)) {
				final String mob = e.getEntityType().toString().toLowerCase();
				if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
					e.getEntity().remove();
				}
			}
		}
	}

	/**
	 * On chunk load.
	 * 
	 * @param e
	 *            the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkLoad(ChunkLoadEvent e) {
		if (!Settings.amrs) {
			return;
		}
		for (String worldname : worldz) {
			if (Settings.world || e.getWorld().getName().equals(worldname)) {
				// Iterate through the entities in the chunk
				for (Entity en : e.getChunk().getEntities()) {
					final String mob = en.getType().toString().toLowerCase();
					if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
						en.remove();
					}
				}
			}
		}
	}

	/**
	 * On chunk un load.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkUnLoad(ChunkUnloadEvent e) {
		if (!Settings.amrs) {
			return;
		}
		for (String worldname : worldz) {
			if (Settings.world || e.getWorld().getName().equals(worldname)) {
				// Iterate through the entities in the chunk
				for (Entity en : e.getChunk().getEntities()) {
					final String mob = en.getType().toString().toLowerCase();
					if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
						en.remove();
					}
				}
			}
		}
	}
}