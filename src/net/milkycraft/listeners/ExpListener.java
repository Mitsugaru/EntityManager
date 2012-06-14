package net.milkycraft.listeners;

import java.util.ArrayList;
import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving exp related events.
 * 
 * @see EntityDeathEvent
 */
public class ExpListener extends EntityManager implements Listener {

	/** The tagged. */
	protected static ArrayList<Entity> tagged;

	/**
	 * On xp drop.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onXpDrop(EntityDeathEvent e) {
		if (Settings.totalexp) {
			if (Settings.world
					|| WorldSettings.worlds.contains(e.getEntity().getWorld()
							.getName())) {
				e.setDroppedExp(0);
			}
		}
	}

	/**
	 * On tagged mob death.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onTaggedMobDeath(EntityDeathEvent e) {
		if (e.getEntity() == null) {
			return;
		}
		if (Settings.msxp || Settings.msdrops) {
			if (Settings.world
					|| WorldSettings.worlds.contains(e.getEntity().getWorld()
							.getName())) {
				if (ExpListener.tagged.contains(e.getEntity())) {
					if (Settings.msdrops) {
						e.getDrops().clear();
						try {
							ExpListener.tagged.remove(e.getEntity());
						} catch (NullPointerException ex) {

						}
					}
					if (Settings.msxp) {
						e.setDroppedExp(0);
						try {
							ExpListener.tagged.remove(e.getEntity());
						} catch (NullPointerException ev) {

						}
					}
				}
			}
		}
	}

	/**
	 * Tag mob.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void tagMob(CreatureSpawnEvent e) {
		if (ExpListener.tagged == null) {
			tagged = new ArrayList<Entity>();
		}
		/*
		 * Fixed a arraylist bug sortof If both were off the mobs were tagged
		 * but werent removed on death because if settings are false they dont
		 * get removed
		 */
		if (e.getSpawnReason() == SpawnReason.SPAWNER) {
			if (Settings.msdrops || Settings.msxp) {
				ExpListener.tagged.add(e.getEntity());
			}
		}
	}

	/**
	 * On exp explode.
	 * 
	 * @param e
	 *            the ExpBottleEvent
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onExpExplode(ExpBottleEvent e) {
		if (Settings.world
				|| WorldSettings.worlds.contains(e.getEntity().getWorld()
						.getName())) {
			if (Settings.totalexp) {
				e.setExperience(0);
				e.setShowEffect(false);
			}
		}
	}
}
