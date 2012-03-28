package net.milkycraft.Listeners;

import net.milkycraft.Spawnegg;

import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantListener implements Listener {
	Spawnegg plugin;
	public EnchantListener(Spawnegg instance) {
		plugin = instance;
	}
	public void onEnchantAttempt(PrepareItemEnchantEvent e) {
		if(plugin.getConfig().getBoolean("Disable.Enchanting") &&
				!e.getEnchanter().hasPermission("entitymanager.enchanting")) {
				e.setCancelled(true);
		}
	}
}
