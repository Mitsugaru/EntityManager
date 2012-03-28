package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;

public class ExpListener implements Listener {
	Spawnegg plugin;
	public ExpListener(Spawnegg instance) {
		plugin = instance;
	}
	public void onXpDrop(EntityDeathEvent e) {
		if(plugin.getConfig().getBoolean("Disable.Experience")) {
			e.setDroppedExp(0);
			return;
		}
	}
	public void XpBottle(ExpBottleEvent ev) {
		if(plugin.getConfig().getBoolean("Disable.Experience")) {
			ev.getEntity().remove();
			return;
		}
	}
}
