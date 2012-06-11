package net.milkycraft.executors;

import net.milkycraft.api.EntityManagerAPI;
import net.milkycraft.configuration.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntityManagerCommandExecutor  implements CommandExecutor{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			sender.sendMessage(ChatColor.GOLD + "Logging: " + ChatColor.RED
					+ EntityManagerAPI.getManager().isMetricsEnabled()
					+ ChatColor.GOLD + "      Metrics: " + ChatColor.RED
					+ EntityManagerAPI.getManager().isMetricsEnabled());
			sender.sendMessage(ChatColor.GOLD + "Alerts: " + ChatColor.RED
					+ EntityManagerAPI.getManager().isSendingAlerts()
					+ ChatColor.GOLD + "      Motd: " + ChatColor.RED
					+ EntityManagerAPI.getManager().isMotdEnabled());
			sender.sendMessage(ChatColor.GREEN
					+ "Sumbit requests on bukkit dev to have them added!");
			sender.sendMessage(ChatColor.GREEN
					+ "If you find any issues or bugs, please report them!");
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("purge")) {
				sender.sendMessage(ChatColor.RED
						+ "Usage: /em purge <playername>");
				return false;
			}
		}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("purge")
						&& sender.hasPermission("entitymanager.admin")) {
					final Player target = sender.getServer().getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
						return false;
					}
					boolean fire = false;
					boolean egg = false;
					boolean exp = false;
					boolean end = false;
					boolean eye = false;
					boolean pot = false;
					if (target.getInventory().contains(Material.MONSTER_EGG)) {
						target.getInventory().remove(Material.MONSTER_EGG);
					} else if (target.getInventory()
							.contains(Material.FIREBALL) && Settings.fire) {
						target.getInventory().remove(Material.FIREBALL);
						fire = true;
					} else if (target.getInventory().contains(Material.EGG)
							&& Settings.egg) {
						target.getInventory().remove(Material.EGG);
						egg = true;
					} else if (target.getInventory().contains(
							Material.EXP_BOTTLE)
							&& Settings.xpbott) {
						target.getInventory().remove(Material.EXP_BOTTLE);
						exp = true;
					} else if (target.getInventory().contains(
							Material.ENDER_PEARL)
							&& Settings.pearl) {
						target.getInventory().remove(Material.ENDER_PEARL);
						end = true;
					} else if (target.getInventory().contains(
							Material.EYE_OF_ENDER)
							&& Settings.eye) {
						target.getInventory().remove(Material.EYE_OF_ENDER);
						eye = true;
					} else if (target.getInventory().contains(Material.POTION)
							&& Settings.potion) {
						target.getInventory().remove(Material.POTION);
						pot = true;
					}
					final ChatColor a = ChatColor.AQUA;
					final ChatColor y = ChatColor.GRAY;
					sender.sendMessage(ChatColor.GRAY + args[1]
							+ "'s inventory was purged of: ");
					sender.sendMessage(y + "Fireball: " + a + fire + y + "Egg: " + a + egg);
					sender.sendMessage(y + "Exp_Bottle: " + a + exp + y + "Ender_Pearl: " + a + end);
					sender.sendMessage(y + "Eye_of_Ender: " + a + eye + y + "Potion: " + a + pot);
					return true;
				}
			}
		
		if (cmd.getName().equalsIgnoreCase("entitymanager")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("canspawn")) {
					if (sender.hasPermission("entitymanager.admin")) {
						try {
							final String mob = args[1].toUpperCase();
							if (!EntityManagerAPI.getManager().canSpawn(
									EntityType.valueOf(mob))) {
								sender.sendMessage(ChatColor.GRAY + "The mob : " + mob.toLowerCase() + " can't spawn");
							} else {
								sender.sendMessage(ChatColor.GRAY + "The mob : " + mob.toLowerCase() + " can spawn");
							}
							return true;
						} catch (ArrayIndexOutOfBoundsException e) {
							sender.sendMessage(ChatColor.RED
									+ "Correct usage: /em canspawn EntityType");
						} catch (NullPointerException e) {
							sender.sendMessage(ChatColor.RED
									+ "Correct usage: /em canspawn EntityType");
						} catch (IllegalArgumentException e) {
							sender.sendMessage(ChatColor.RED
									+ "There is no entity named : " + ChatColor.YELLOW
									+ args[1].toLowerCase());
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You dont have permission to do that!");
						return false;
					}
				}
			}
		}
		if (args[0].equalsIgnoreCase("crystal")
				&& sender.hasPermission("entitymanager.crystal")) {
			if (((Player) sender).getItemInHand().getTypeId() == 383) {
				((Player) sender).getItemInHand().setAmount(1);
				((Player) sender).getItemInHand().setDurability((short) 200);
				sender.sendMessage(ChatColor.GREEN
						+ "You now have a EnderCrystal spawner egg inhand!");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED
						+ "You must have a spawn egg in hand!");
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("mobs")
				&& sender.hasPermission("entitymanager.admin")) {
			 boolean creeper = Settings.creeps;
			 boolean skeleton = Settings.skeles;
			 boolean spider = Settings.spiders;
			 boolean zombie = Settings.zombies;
			 boolean slime = Settings.slimes;
			 boolean ghast = Settings.ghasts;
			 boolean pigman = Settings.pigmans;
			 boolean enderman = Settings.enders;
			 boolean cavespider = Settings.caves;
			 boolean silverfish = Settings.fishs;
			 boolean blaze = Settings.blazes;
			 boolean magmacube = Settings.cubes;
			 boolean pig = Settings.pigs;
			 boolean sheep = Settings.sheeps;
			 boolean cow = Settings.cows;
			 boolean chicken = Settings.chicks;
			 boolean squid = Settings.squids;
			 boolean wolf = Settings.wolfs;
			 boolean mushroom = Settings.mooshs;
			 boolean snowman = Settings.snow;
			 boolean dragon = Settings.dragons;
			 boolean ocelot = Settings.ocelots;
			 boolean vill = Settings.villas;
			 boolean irongolem = Settings.iron;
			 ChatColor g = ChatColor.GREEN;
			 ChatColor w = ChatColor.YELLOW;
			sender.sendMessage(ChatColor.GRAY
					+ "------------Blocked mobs list------------");
			sender.sendMessage(w + "Pig = " + g + pig + w
					+ "             Creeper = " + g + creeper);
			sender.sendMessage(w + "Sheep = " + g + sheep + w
					+ "        Skeleton = " + g + skeleton);
			sender.sendMessage(w + "Cow = " + g + cow + w
					+ "            Spider = " + g + spider);
			sender.sendMessage(w + "Chicken = " + g + chicken + w
					+ "       Zombie = " + g + zombie);
			sender.sendMessage(w + "Squid = " + g + squid + w
					+ "          Slime = " + g + slime);
			sender.sendMessage(w + "Wolf = " + g + wolf + w
					+ "            Ghast = " + g + ghast);
			sender.sendMessage(w + "Mooshroom = " + g + mushroom + w
					+ "    Pigman = " + g + pigman);
			sender.sendMessage(w + "Snowman = " + g + snowman + w
					+ "       Enderman = " + g + enderman);
			sender.sendMessage(w + "Ocelot = " + g + ocelot + w
					+ "          CaveSpider = " + g + cavespider);
			sender.sendMessage(w + "Villager = " + g + vill + w
					+ "        Silverfish = " + g + silverfish);
			sender.sendMessage(w + "Blaze = " + g + blaze + w
					+ "           MagmaCube = " + g + magmacube);
			sender.sendMessage(w + "IronGolem = " + g + irongolem + w
					+ "    EnderDragon = " + g + dragon);
			sender.sendMessage(ChatColor.GRAY
					+ "-----------True = blocked mob------------");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			sender.sendMessage(ChatColor.GOLD + "Logging: " + ChatColor.RED
					+ EntityManagerAPI.getManager().isLogging()
					+ ChatColor.GOLD + "      Metrics: " + ChatColor.RED
					+ EntityManagerAPI.getManager().isMetricsEnabled());
			sender.sendMessage(ChatColor.GREEN
					+ "Sumbit requests on bukkit dev to have them added!");
			sender.sendMessage(ChatColor.GREEN
					+ "If you find any issues or bugs, please report them!");
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			return true;
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ " Could not complete that command!");
			return false;
		}
	}
}
