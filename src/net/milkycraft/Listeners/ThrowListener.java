package net.milkycraft.Listeners;

import java.util.List;

import net.milkycraft.Spawnegg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ThrowListener implements Listener {
	Spawnegg plugin;
	public ThrowListener(Spawnegg instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void OnThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		ChatColor green = ChatColor.GREEN;
		ChatColor red = ChatColor.RED;
		if (e.getItem() == null) {
			return;
		}
		List<String> worldz = plugin.getConfig().getStringList(
				"World.Worldname");
		for (String worldname : worldz) {
			if (e.getPlayer().getWorld().getName().equals(worldname)) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (e.getItem().getTypeId() == 383) {
						if (e.getItem().getDurability() == 200) {
							Location loc = e.getClickedBlock().getLocation();
							e.getClickedBlock().getWorld()
									.spawn(loc, EnderCrystal.class);
							if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
								e.getItem().setType(null);
								return;
							}
						}
						if (e.getItem().getTypeId() == 384) {
							if (plugin.getConfig().getBoolean(
									"block.Throw.XpBottles")
									&& !player
											.hasPermission("antispawnegg.throw.xpbottles")) {
								e.setCancelled(true);
								alert(player, e);
								player.sendMessage(green
										+ "[ASE]"
										+ red
										+ " You dont have permission to throw xp bottles");
								return;
							}
						}
						if (e.getItem().getTypeId() == 385) {
							if (plugin.getConfig().getBoolean(
									"block.Throw.FireCharges")
									&& !player
											.hasPermission("antispawnegg.throw.firecharges")) {
								e.setCancelled(true);
								alert(player, e);
								player.sendMessage(green
										+ "[ASE]"
										+ red
										+ " You dont have permission to throw fire charges");
								return;
							}
						}
						if (e.getItem().getTypeId() == 344) {
							if (plugin.getConfig().getBoolean(
									"block.Throw.ChickenEggs")
									&& !player
											.hasPermission("antispawnegg.throw.chickeneggs")) {
								e.setCancelled(true);
								alert(player, e);
								player.sendMessage(green
										+ "[ASE]"
										+ red
										+ " You dont have permission to throw eggs");
								return;
							}
						}
						if (e.getItem().getTypeId() == 373) {
							if (e.getItem().getDurability() == 16385
									|| e.getItem().getDurability() == 16481) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Regeneration")
										&& !player
												.hasPermission("antispawnegg.regeneration")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Regen. Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16386) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Swiftness")
										&& !player
												.hasPermission("antispawnegg.swiftness")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Swiftness Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16387
									|| e.getItem().getDurability() == 16483) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.FireResistance")
										&& !player
												.hasPermission("antispawnegg.fireresistance")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Fire resist Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16388
									|| e.getItem().getDurability() == 16484) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Poison")
										&& !player
												.hasPermission("antispawnegg.poison")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Poison Potions");
									return;
								}
							}

							else if (e.getItem().getDurability() == 16389) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.InstantHealth")
										&& !player
												.hasPermission("antispawnegg.health")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use InstaHealth Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16392) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Weakness")
										&& !player
												.hasPermission("antispawnegg.weakness")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Weakness Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16393) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Strength")
										&& !player
												.hasPermission("antispawnegg.strength")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Strength Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16394) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Slowness")
										&& !player
												.hasPermission("antispawnegg.slowness")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Slowness Potions");
									return;
								}
							} else if (e.getItem().getDurability() == 16396) {
								if (plugin.getConfig().getBoolean(
										"disabled.Potions.Harming")
										&& !player
												.hasPermission("antispawnegg.harming")) {
									e.setCancelled(true);
									player.sendMessage(green
											+ "[ASE]"
											+ red
											+ " You dont have permission to use Harming Potions");
									return;
								}
							}
						}
						if (e.getItem().getTypeId() == 368) {
							if (plugin.getConfig().getBoolean(
									"block.Throw.EnderPearls")
									&& !player
											.hasPermission("antispawnegg.enderpearl")) {
								e.setCancelled(true);
								player.sendMessage(green
										+ "[ASE]"
										+ red
										+ " You dont have permission to use Ender Pearls");
								return;
							}
						}
						if (e.getItem().getTypeId() == 381) {
							if (plugin.getConfig().getBoolean(
									"block.Throw.EnderEyes")
									&& !player
											.hasPermission("antispawnegg.endereye")) {
								e.setCancelled(true);
								player.sendMessage(green
										+ "[ASE]"
										+ red
										+ " You dont have permission to use Ender Eyes");
								return;
							}
						}
					}
				}
			}
		}
	}

	public void alert(Player player, PlayerInteractEvent e) {
		boolean alertr = plugin.getConfig().getBoolean("send.alerts");
		if (alertr) {
			Bukkit.broadcast(ChatColor.GREEN + "[ASE] " + ChatColor.DARK_RED
					+ e.getPlayer().getDisplayName() + " Tryed to throw an "
					+ ChatColor.GOLD + e.getItem().getType() + ".",
					"antispawnegg.alert");
			return;
		}
	}
}