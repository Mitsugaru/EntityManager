package net.milkycraft.listeners;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;
import net.milkycraft.permissions.PermissionHandler;
import net.milkycraft.permissions.PermissionNode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving dispenser events. The class that is
 * interested in processing a dispenser event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addDispenserListener<code> method. When
 * the dispenser event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see DispenserEvent
 */
public class DispenserListener extends EntityManager implements Listener
{
	/** The red. */
	private static final ChatColor red = ChatColor.DARK_RED;

	/** The gold. */
	private static final ChatColor gold = ChatColor.GOLD;

	/** The green. */
	private static final ChatColor green = ChatColor.GREEN;

	/**
	 * On dispense.
	 * 
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDispense(BlockDispenseEvent event)
	{
		final Integer item = event.getItem().getTypeId();
		for (String worldname : WorldSettings.worlds)
		{
			if (Settings.world
					|| event.getBlock().getWorld().getName().equals(worldname))
			{
				/* Monster egg */
				if (item == 383)
				{
					if (Settings.MonsEggs)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Chicken egg */
				else if (item == 344)
				{
					if (Settings.ChickEggs)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Potion */
				else if (item == 373)
				{
					if (Settings.potionz)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Fireball */
				else if (item == 385)
				{
					if (Settings.Fballs)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Xp bottle */
				else if (item == 384)
				{
					if (Settings.xBottz)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Water */
				else if (item == 8 || item == 9)
				{
					if (Settings.water)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Lava */
				else if (item == 10 || item == 11)
				{
					if (Settings.lava)
					{
						event.setCancelled(true);
						alert(event);
						return;
					}
				}
				/* Dispense blacklist check */
				for (Integer itemx : Settings.items)
				{
					if (event.getItem().getTypeId() == itemx)
					{
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
	public final void alert(BlockDispenseEvent event)
	{
		final int xx = (int) event.getBlock().getLocation().getX();
		final int yy = (int) event.getBlock().getLocation().getY();
		final int zz = (int) event.getBlock().getLocation().getZ();
		final String item = event.getItem().getType().toString().toLowerCase();
		if (Settings.logging)
		{
			writeLog("[EM]" + "Failed dispense of " + item + " at: " + xx + ","
					+ yy + "," + zz + ".");
		}
		if (Settings.alertz)
		{
			for (Player p : Bukkit.getServer().getOnlinePlayers())
			{
				if (PermissionHandler.has(p, PermissionNode.ADMIN))
				{
					p.sendMessage(green + "[EM] " + red + "Failed dispense of "
							+ gold + item + red + " at: " + green + xx + ","
							+ yy + "," + zz + ".");
				}
			}
		}
	}
}
