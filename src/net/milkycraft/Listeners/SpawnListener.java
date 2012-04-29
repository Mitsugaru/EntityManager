/*
 * 
 */
package net.milkycraft.Listeners;

import java.util.List;
import java.util.logging.Level;

import net.milkycraft.Spawnegg;
import net.milkycraft.ASEConfiguration.Settings;

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

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving spawn events.
 * The class that is interested in processing a spawn
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSpawnListener<code> method. When
 * the spawn event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SpawnEvent
 */
public class SpawnListener implements Listener {

	/** The killed from targeting */
	private int killed = 0;
	
	/** The killed from recieving damage */
	private int dilled = 0;

	/**
	 * On spawn.
	 *
	 * @param e the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpawn(CreatureSpawnEvent e) {
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				e.setCancelled(Settings.getConfig().getBoolean("disabled.mobs." + e.getEntityType().toString().toLowerCase()));
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
	 * @param e the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetEvent e) {
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
				final String mob = e.getEntityType().toString().toLowerCase();
				if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
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
		}
	}

	/**
	 * On block damage.
	 *
	 * @param e the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockDamage(EntityDamageEvent e) {
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getEntity().getWorld().getName().equals(worldname)) {
		final String mob = e.getEntityType().toString().toLowerCase();
		if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
			e.getEntity().remove();
			++dilled;
			if (dilled > 10) {
				Spawnegg.log.log(Level.INFO, "[EM]Some " + mob
						+ "'s were forcibly removed because they are disabled");
				dilled -= 10;
			}
		}
			}
		}
	}

	/**
	 * On chunk load.
	 *
	 * @param e the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkLoad(ChunkLoadEvent e) {
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
				// Iterate through the entities in the chunk
				for (Entity en : e.getChunk().getEntities()) {
					final String mob = en.getType().toString().toLowerCase();
					if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
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
	
	/**
	 * On chunk un load.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkUnLoad(ChunkUnloadEvent e) {
		List<String> worldz = Settings.worlds;
		for (String worldname : worldz) {
			if (e.getWorld().getName().equals(worldname)) {
				// Iterate through the entities in the chunk
				for (Entity en : e.getChunk().getEntities()) {
					final String mob = en.getType().toString().toLowerCase();
					if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
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