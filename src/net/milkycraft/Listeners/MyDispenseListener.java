package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

public class MyDispenseListener implements Listener {
	Spawnegg plugin;

	public MyDispenseListener(Spawnegg instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDispense(BlockDispenseEvent event) {
		ItemStack item = event.getItem();
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (event.getBlock().getWorld().getName().equals(worldname)) {
				if (event.getItem().getTypeId() == 383) {
					if (plugin.getConfig().getBoolean(
							"block.Dispense.SpawnerEggs")) {
						alert(event);
						event.setCancelled(true);
						return;
					}
				} else if (item.getTypeId() == 344) {
					if (plugin.getConfig().getBoolean(
							"block.Dispense.ChickenEggs")) {
						alert(event);
						event.setCancelled(true);
						return;
					}
				} else if (item.getTypeId() == 373) {
					if (plugin.getConfig().getBoolean("block.Dispense.Potions")) {
						alert(event);
						event.setCancelled(true);
						return;
					}
				} else if (item.getTypeId() == 385) {
					if (plugin.getConfig().getBoolean(
							"block.Dispense.FireBalls")) {
						alert(event);
						event.setCancelled(true);
						return;
					}
				} else if (item.getTypeId() == 384) {
					if (plugin.getConfig().getBoolean(
							"block.Dispense.XpBottles")) {
						alert(event);
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	public void alert(BlockDispenseEvent event) {
		double x = event.getBlock().getLocation().getX();
		double y = event.getBlock().getLocation().getY();
		double z = event.getBlock().getLocation().getZ();
		int xx = (int) x;
		int yy = (int) y;
		int zz = (int) z;
		if (plugin.getConfig().getBoolean("Send-Alerts")) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN
							+ "[EM] "
							+ ChatColor.DARK_RED
							+ "Failed dispense of "
							+ ChatColor.GOLD
							+ event.getItem().getType().toString()
									.toLowerCase() + " at: " + xx + "," + yy
							+ "," + zz + ".");
				}
			}
		}
	}
}
