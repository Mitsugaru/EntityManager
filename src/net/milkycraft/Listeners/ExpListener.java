package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

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
public class ExpListener extends EntityManager implements Listener {


	/**
	 * On xp drop.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		final World world = e.getEntity().getWorld();
		if (Settings.totalexp) {
			for (String worldname : Settings.worlds) {
				if (Settings.world || world.getName().equals(worldname)) {
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
		final World world = e.getEntity().getWorld();
		for (final String worldname : Settings.worlds) {
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
