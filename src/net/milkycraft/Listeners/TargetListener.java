package net.milkycraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class TargetListener implements Listener {

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
