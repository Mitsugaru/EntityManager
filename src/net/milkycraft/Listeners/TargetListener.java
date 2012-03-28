package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class TargetListener implements Listener{
	Spawnegg plugin;

	public TargetListener(Spawnegg instance) {
		plugin = instance;
	}		
	@EventHandler(priority = EventPriority.HIGH)
	public void onTarget(EntityTargetEvent e) {
		Entity target = e.getTarget();
	     if(target instanceof Player){
	          Player player = (Player)target;
			if(e.getReason() == TargetReason.CLOSEST_PLAYER 
					|| e.getReason() ==  TargetReason.TARGET_ATTACKED_ENTITY) {
				if(player.getItemInHand().getTypeId() == 383) {
					if (player.hasPermission("antispawnegg.avoid.target")){
						e.setCancelled(true);
						return;
				}
				}
			}
			}
		}	
}