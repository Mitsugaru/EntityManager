/*
 * 
 */
package net.milkycraft.Listeners;

import org.bukkit.entity.Entity;
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
public class TargetListener implements Listener {
	/*
	 * In 3.4.2, was stripped down so that: If you have permission no mobs will
	 * ever target you This solves the issue of: 1. If you use your last egg,
	 * the mob(s) will target (Possibly) 2. If you want to have like a pet
	 * creeper, it will target you eventrually Not to mention less useless code
	 * is better for resource starved servers
	 */

	/**
	 * On target.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetLivingEntityEvent e) {
		final Entity target = e.getTarget();
		if (target instanceof Player) { // Check if the target is a player
			Player player = (Player) target; 
			if (player.hasPermission("entitymanager.avoid.target")) {
				e.setCancelled(true); // Cancel the target
				return;
			}
		}
	}
}