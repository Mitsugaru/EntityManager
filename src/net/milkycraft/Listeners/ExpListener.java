package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ExpListener implements Listener {
	/*
	 * Class tested and working
	 */
	Spawnegg plugin;
	public ExpListener(Spawnegg instance) {
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onXpDrop(EntityDeathEvent e) {
		if(plugin.getConfig().getBoolean("Disable.Experience")) {
			e.setDroppedExp(0);
			return;
		}
	}
}
