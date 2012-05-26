package net.milkycraft.Listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.ASEConfiguration.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	private static Logger log = Logger.getLogger("Minecraft");

	@EventHandler(priority = EventPriority.LOWEST)
	public void blockPlacement(BlockPlaceEvent e) {
		final List<String> worldz = Settings.worlds;
		if (e.getBlock().getType() == Material.DRAGON_EGG) {
			if (Settings.world) {
				if (!e.getPlayer().hasPermission("entitymanager.dragonegg")
						&& Settings.dragegg) {
					e.setCancelled(true);
					alert(e);
				}
			} else {
				for (String wn : worldz) {
					if (e.getPlayer().getWorld().getName().equals(wn)) {
						if (!e.getPlayer().hasPermission(
								"entitymanager.dragonegg")
								&& Settings.dragegg) {
							e.setCancelled(true);
							alert(e);
						}
					}
				}
			}
		}

	}

	public final void alert(BlockPlaceEvent event) {
		final int xx = (int) event.getBlock().getLocation().getX();
		final int yy = (int) event.getBlock().getLocation().getY();
		final int zz = (int) event.getBlock().getLocation().getZ();
		final String block = event.getBlock().getType().toString()
				.toLowerCase();
		final String name = event.getPlayer().getDisplayName();
		if (Settings.logging) {
			log.log(Level.WARNING, "[EM] " + name + " tried to place a "
					+ block + " at:" + xx + "," + yy + "," + zz + ".");
		}
		if (Settings.alertz) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM]" + name
							+ " tried to place a " + block + " at:" + xx + ","
							+ yy + "," + zz + ".");
				}
			}
		}
	}
}
