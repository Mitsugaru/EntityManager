package net.milkycraft.listeners;

import java.util.List;
import net.milkycraft.configuration.Settings;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;

public class ExpListener implements Listener {

	private final List<String> worldz = Settings.worlds;
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onXpDrop(EntityDeathEvent e) {
		final World world = e.getEntity().getWorld();
		if (Settings.totalexp) {
			for (String worldname : worldz) {
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
