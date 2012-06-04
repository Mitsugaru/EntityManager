package net.milkycraft.listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkycraft.configuration.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class DispenserListener implements Listener {
	private final List<Integer> itemz = Settings.items;
	private final List<String> worldz = Settings.worlds;
	/** The red. */
	private final ChatColor red = ChatColor.DARK_RED;

	/** The gold. */
	private final ChatColor gold = ChatColor.GOLD;

	/** The green. */
	private final ChatColor green = ChatColor.GREEN;

	/** The Constant log. */
	private final static Logger log = Logger.getLogger("Minecraft");

	/**
	 * On dispense.
	 * 
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDispense(BlockDispenseEvent event) {
		final Integer item = event.getItem().getTypeId();	
		for (String worldname : worldz) {
			if (Settings.world
					|| event.getBlock().getWorld().getName().equals(worldname)) {
				/* Monster egg */
				if (item == 383) {
					if (Settings.MonsEggs) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Chicken egg */
				else if (item == 344) {
					if (Settings.ChickEggs) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Potion */
				else if (item == 373) {
					if (Settings.potionz) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Fireball */
				else if (item == 385) {
					if (Settings.Fballs) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Xp bottle */
				else if (item == 384) {
					if (Settings.xBottz) {
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Dispense blacklist check */
				for (Integer itemx : itemz) {
					if (event.getItem().getTypeId() == itemx) {
						event.setCancelled(true);
						alert(event);
					}
				}
			}
		}
	}

	/**
	 * Alert.
	 * 
	 * @param event
	 *            the event
	 */
	public final void alert(BlockDispenseEvent event) {
		final int xx = (int) event.getBlock().getLocation().getX();
		final int yy = (int) event.getBlock().getLocation().getY();
		final int zz = (int) event.getBlock().getLocation().getZ();
		final String item = event.getItem().getType().toString().toLowerCase();
		if (Settings.logging) {
			log.log(Level.WARNING, "[EM]" + "Failed dispense of " + item
					+ " at: " + xx + "," + yy + "," + zz + ".");
		}
		if (Settings.alertz) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(green + "[EM] " + red + "Failed dispense of "
							+ gold + item + red + " at: " + green + xx + ","
							+ yy + "," + zz + ".");
				}
			}
		}
	}
}

