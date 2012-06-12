package net.milkycraft.listeners;

import java.util.ArrayList;
import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;

/**
 * The listener interface for receiving exp related events.
 *
 * @see EntityDeathEvent
 */
public class ExpListener extends EntityManager implements Listener {
	
	protected static ArrayList<Entity> tagged;

	/**
	 * On xp drop.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		final World world = e.getEntity().getWorld();
		if (Settings.totalexp) {
			for (String worldname : WorldSettings.worlds) {
				if (Settings.world || world.getName().equals(worldname)) {
					e.setDroppedExp(0);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onTaggedMobDeath(EntityDeathEvent e) {
		final World world = e.getEntity().getWorld();
		if(e.getEntity() == null) {
			return;
		}
		if (Settings.msxp || Settings.msdrops) {
			for (String worldname : WorldSettings.worlds) {
				if (Settings.world || world.getName().equals(worldname)) {
					if(ExpListener.tagged.contains(e.getEntity())) {						
						if(Settings.msdrops) {
							e.getDrops().clear();
							try {
							ExpListener.tagged.remove(e.getEntity());
							} catch(NullPointerException ex) {
								
							}
						}
						if(Settings.msxp) {
							e.setDroppedExp(0);
							try {
								ExpListener.tagged.remove(e.getEntity());
								} catch(NullPointerException ev) {
									
								}
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void tagMob(CreatureSpawnEvent e) {
		if(ExpListener.tagged == null) {
			tagged = new ArrayList<Entity>();
		}
		if(e.getSpawnReason() == SpawnReason.SPAWNER) {
			ExpListener.tagged.add(e.getEntity());
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
		final World world = e.getEntity().getWorld();
		for (final String worldname : WorldSettings.worlds) {
			if (Settings.world || world.getName().equals(worldname)) {
				if (Settings.totalexp) {
					e.setExperience(0);
					e.setShowEffect(false);
					writeLog("[EM] An EXP bottle was thrown and dropped 0!");
				}
			}
		}
	}
}
