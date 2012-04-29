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

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving exp events.
 * The class that is interested in processing a exp
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addExpListener<code> method. When
 * the exp event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ExpEvent
 */
public class ExpListener implements Listener {
	/*
	 * Class tested and working
	 */
	/**
	 * On xp drop.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		final List<String> worldz = Settings.worlds;
		final World world = e.getEntity().getWorld();
		for (String worldname : worldz) {
			if (world.getName().equals(worldname)) {
				if (Settings.totalexp) {
					e.setDroppedExp(0);
				}
			}
		}
	}

	/**
	 * On exp explode.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onExpExplode(ExpBottleEvent e) {
		final List<String> worldz = Settings.worlds;
		final World world = e.getEntity().getWorld();
		for (String worldname : worldz) {
			if (world.getName().equals(worldname)) {
				if (Settings.totalexp) {
					e.setExperience(0);
					e.setShowEffect(false);
				}
			}
		}
	}
}
