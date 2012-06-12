/*
 * 
 */
package net.milkycraft.listeners;

import net.milkycraft.EntityManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving target events.
 * The class that is interested in processing a target
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTargetListener<code> method. When
 * the target event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TargetEvent
 */
public class TargetListener extends EntityManager implements Listener {

	/**
	 * On target.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetLivingEntityEvent e) {
		if (e.getTarget() instanceof Player) {
			final Player player = (Player) e.getTarget();
			if (player.hasPermission("entitymanager.avoid.target")) {
				e.setCancelled(true); // Cancel the target
				return;
			}
		}
	}
}
