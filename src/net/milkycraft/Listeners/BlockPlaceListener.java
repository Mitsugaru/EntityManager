package net.milkycraft.listeners;

import java.util.List;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving blockPlace events.
 * The class that is interested in processing a blockPlace
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addBlockPlaceListener<code> method. When
 * the blockPlace event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BlockPlaceEvent
 */
public class BlockPlaceListener extends EntityManager implements Listener {
	
	/** The worldz. */
	private static List<String> worldz = Settings.worlds;

	/**
	 * Block placement.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void blockPlacement(BlockPlaceEvent e) {
		final String block = e.getBlock().getType().toString().toLowerCase();
		for (String wn : worldz) {
			if (Settings.world || e.getPlayer().getWorld().getName().equals(wn)) {
				if (e.getBlock().getTypeId() == 130) {
					if (Settings.enderchest
							&& !e.getPlayer().hasPermission(
									"entitymanager.place.enderchest")) {
						e.setCancelled(true);
						e.getPlayer().sendMessage(
								ChatColor.GREEN + "[EM] " + ChatColor.RED
										+ "You don't have permission to place "
										+ ChatColor.GOLD + block
										+ ChatColor.RED + "'s!");
						alert(e);
						return;
					}
				} else if (e.getBlock().getTypeId() == 122) {
					if (Settings.dragegg
							&& !e.getPlayer().hasPermission(
									"entitymanager.place.dragonegg")) {
						e.setCancelled(true);
						e.getPlayer().sendMessage(
								ChatColor.GREEN + "[EM] " + ChatColor.RED
										+ "You don't have permission to place "
										+ ChatColor.GOLD + block
										+ ChatColor.RED + "'s!");
						alert(e);
						return;
					}
				} else if (e.getBlock().getTypeId() == 131) {
					if (Settings.tripwires
							&& !e.getPlayer().hasPermission(
									"entitymanager.place.tripwire")) {
						e.setCancelled(true);
						e.getPlayer().sendMessage(
								ChatColor.GREEN + "[EM] " + ChatColor.RED
										+ "You don't have permission to place "
										+ ChatColor.GOLD + block
										+ ChatColor.RED + "'s!");
						alert(e);
						return;
					}
				}
			}
		}
	}

	/**
	 * Alert.
	 *
	 * @param ev the ev
	 */
	public final void alert(BlockPlaceEvent ev) {
		final int xx = (int) ev.getBlock().getLocation().getX();
		final int yy = (int) ev.getBlock().getLocation().getY();
		final int zz = (int) ev.getBlock().getLocation().getZ();
		final String block = ev.getBlock().getType().toString();
		final Player name = ev.getPlayer();
		if (Settings.logging) {
			writeWarn("[EM] " + name + " tried to place a " + block + " at:"
					+ xx + "," + yy + "," + zz + ".");
		}
		if (Settings.alertz) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.hasPermission("entitymanager.admin")) {
					p.sendMessage(ChatColor.GREEN + "[EM]"
							+ name.getDisplayName() + " tried to place a "
							+ ChatColor.GOLD + block.toLowerCase()
							+ ChatColor.GREEN + " at:" + ChatColor.YELLOW + xx
							+ "," + yy + "," + zz + ".");
				}
			}
		}
	}
}
