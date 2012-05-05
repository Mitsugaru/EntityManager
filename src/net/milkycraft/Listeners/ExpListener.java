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
 * The listener interface for receiving exp events. The class that is interested
 * in processing a exp event implements this interface, and the object created
 * with that class is registered with a component using the component's
 * <code>addExpListener<code> method. When
 * the exp event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ExpEvent
 */

public class ExpListener implements Listener {
	
	/** The Constant clazz. */
	private static final ExpListener clazz = new ExpListener();
	
	/** On xp drop. */
	private int xp = 0;
	
	/**
	 * On xp drop.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		final List<String> worldz = Settings.worlds;
		final World world = e.getEntity().getWorld();
		for (final String worldname : worldz) {
			if (Settings.world || world.getName().equals(worldname)) {
				if (Settings.totalexp) {
					e.setDroppedExp(xp);
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
	
	/**
	 * Sets the exp dropped.
	 *
	 * @param exp the new exp dropped
	 */
	public void setExpDropped(int exp) {
		xp = exp;
	}
	
	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	public static ExpListener getThis() {
		return clazz;
	}
}
