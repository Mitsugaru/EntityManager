/*
 * 
 */
package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving explosion events.
 * The class that is interested in processing a explosion
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addExplosionListener<code> method. When
 * the explosion event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ExplosionEvent
 */
public class ExplosionListener extends EntityManager implements Listener {

	/**
	 * On explode.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onExplode(ExplosionPrimeEvent e) {
		for (String world : WorldSettings.worlds) {
			if (e.getEntity().getWorld().getName().equals(world)) {
				if (Settings.expwr == 3) {
					e.getEntity().remove();
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	/**
	 * On entity explode.
	 *
	 * @param e the e
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent e) {
		for (String world : WorldSettings.worlds) {
			if (e.getEntity().getWorld().getName().equals(world)) {
				if(Settings.expwr > 3 || Settings.expwr < 0) {
					writeWarn("[EM] Explosion level is invalid!");
					return;
				}
				/*
				 * Cancelled explosion
				 */
				if(Settings.expwr == 2) {
					e.setCancelled(true);				
				}
				/*
				 * Explosion with damage
				 * No blocks destroyed
				 */
				if(Settings.expwr == 1) {
					e.blockList().clear();
				}
				/*
				 * Normal Explosion
				 */
				if(Settings.expwr == 0) {
					return;
				}
			}
		}
	}
}
