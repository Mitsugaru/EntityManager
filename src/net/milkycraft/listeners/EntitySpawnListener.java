package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;

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
 * The listener interface for receiving entitySpawn events. The class that is
 * interested in processing a entitySpawn event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addEntitySpawnListener<code> method. When
 * the entitySpawn event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see EntitySpawnEvent
 */
public class EntitySpawnListener extends EntityManager implements Listener {

	/** The counter. */
	private int counter = 0;

	/**
	 * On spawn.
	 * 
	 * @param e
	 *            the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpawn(CreatureSpawnEvent e) {
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			e.setCancelled(Settings.getConfig().getBoolean(
					"disabled.mobs."
							+ e.getEntityType().toString().toLowerCase()));
			return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			final String mob = e.getEntityType().toString().toLowerCase();
			if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
				final Entity target = e.getTarget();
				if (target instanceof Player) {
					e.getEntity().remove();
					return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			final String mob = e.getEntityType().toString().toLowerCase();
			if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
				e.getEntity().remove();
				return;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getWorld().getName())) {
			// Iterate through the entities in the chunk
			for (Entity en : e.getChunk().getEntities()) {
				final String mob = en.getType().toString().toLowerCase();
				if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
					en.remove();
					counter++;
					if (Settings.logging && counter >= 10) {
						writeWarn("[EM] 10 entities were removed using Advanced Mob Removal System!");
						writeWarn("[EM] Remember to turn off AMRS shortly after config changes");
						writeWarn("[EM] AMRS can cause lag because it scans through chunks!");
						counter -= 10;
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
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getWorld().getName())) {
			// Iterate through the entities in the chunk
			for (Entity en : e.getChunk().getEntities()) {
				final String mob = en.getType().toString().toLowerCase();
				if (Settings.getConfig().getBoolean("disabled.mobs." + mob)) {
					en.remove();
					counter++;
					if (Settings.logging && counter == 10) {
						writeWarn("[EM] 10 entities were removed using Advanced Mob Removal System!");
						writeWarn("[EM] Remember to turn off AMRS shortly after config changes");
						writeWarn("[EM] AMRS can cause lag because it scans through chunks!");
						counter -= 10;
					}
				}
			}
		}
	}
}