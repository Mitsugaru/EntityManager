/*
 * 
 */
package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;


public class ExpListener implements Listener {


	/** On xp drop. */

	/**
	 * On xp drop.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		final List<String> worldz = Settings.worlds;
		final World world = e.getEntity().getWorld();
		for (String worldname : worldz) {
			if (Settings.world || world.getName().equals(worldname)) {
				if (Settings.totalexp) {
					e.setDroppedExp(0);
				}
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
		final List<String> worldz = Settings.worlds;
		final World world = e.getEntity().getWorld();
		for (final String worldname : worldz) {
			if (Settings.world || world.getName().equals(worldname)) {
				if (Settings.totalexp) {
					e.setExperience(0);
					e.setShowEffect(false);
				}
			}
		}
	}

}
