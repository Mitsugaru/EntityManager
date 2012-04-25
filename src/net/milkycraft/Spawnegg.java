package net.milkycraft;

import java.io.IOException;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkycraft.ASEConfiguration.eConfiguration;
import net.milkycraft.Listeners.EnchantListener;
import net.milkycraft.Listeners.EntitiesListener;
import net.milkycraft.Listeners.ExpListener;
import net.milkycraft.Listeners.LoginListener;
import net.milkycraft.Listeners.MyDispenseListener;
import net.milkycraft.Listeners.MySpawnEggListener;
import net.milkycraft.Listeners.SpawnListener;
import net.milkycraft.Listeners.TargetListener;
import net.milkycraft.Listeners.ThrowListener;
import net.milkycraft.Metrics.Metrics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Spawnegg extends EggWrapper {

	private eConfiguration config;
	public static Logger log = Logger.getLogger("Minecraft");
	public static boolean Thrower = true;
	public static WorldGuardPlugin worldguardPlugin = null;
	public static Economy econ = null;

	@Override
	public void onEnable() {
		String ver = this.getDescription().getVersion();
		setupPluginDependencies();
		log.info(ChatColor.RED + " EntityManager " + ver + " enabled!");
		saveConfig();
		config = new eConfiguration(this);
		config.create();
		config.reload();
		this.getServer().getPluginManager()
				.registerEvents(new MyDispenseListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new LoginListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new MySpawnEggListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new SpawnListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new TargetListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new ThrowListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new ExpListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new EnchantListener(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new EntitiesListener(this), this);
		Metrics metrics;
		boolean metric = this.getConfig().getBoolean("EntityManager.Metrics");
		if (metric) {
			try {
				metrics = new Metrics(this);
				metrics.beginMeasuringPlugin(this);
			} catch (IOException e) {
				writeLog(e.getMessage());
			}
			writeLog("Entity Manager Metrics loaded!");
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		String ver = this.getDescription().getVersion();
		if (args.length == 0) {
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			sender.sendMessage(ChatColor.GREEN + "EntityManager "
					+ ChatColor.YELLOW + ver + ChatColor.GREEN
					+ " by milkywayz loaded!");
			sender.sendMessage(ChatColor.GREEN
					+ "Always check bukkit dev page for latest version");
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
				sender.sendMessage(ChatColor.GREEN + "[EM]" + ChatColor.RED
						+ "Usage: /em purge <playername>");
				return false;
			}
		}
		if (cmd.getName().equalsIgnoreCase("entitymanager")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("purge")
						&& sender.hasPermission("entitymanager.admin")) {
					Player target = sender.getServer().getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(ChatColor.GREEN + "[EM] "
								+ ChatColor.RED + args[1] + " is not online!");
						return false;
					}

					if (target.getInventory().contains(Material.MONSTER_EGG)) {
						target.getInventory().remove(Material.MONSTER_EGG);
					} else if (target.getInventory()
							.contains(Material.FIREBALL) && this.getConfig().getBoolean("block.Throw.FireCharges")) {
						target.getInventory().remove(Material.FIREBALL);
					} else if (target.getInventory().contains(Material.EGG) && this.getConfig().getBoolean("block.Throw.Chickeneggs")) {
						target.getInventory().remove(Material.EGG);
					} else if (target.getInventory().contains(
							Material.EXP_BOTTLE)&& this.getConfig().getBoolean("block.XpBottles")) {
						target.getInventory().remove(Material.EXP_BOTTLE);
					} else if (target.getInventory().contains(
							Material.ENDER_PEARL) && this.getConfig().getBoolean("block.Throw.FireCharges")) {
						target.getInventory().remove(Material.ENDER_PEARL);
					} else if (target.getInventory().contains(
							Material.EYE_OF_ENDER)) {
						target.getInventory().remove(Material.EYE_OF_ENDER);					
					} else if (target.getInventory().contains(
						Material.POTION)) {
					target.getInventory().remove(Material.POTION);
					}
					sender.sendMessage(ChatColor.GREEN + "[EM] "
							+ ChatColor.GOLD + args[1] + ChatColor.RED
							+ "'s inventory was purged of: ");
					sender.sendMessage(ChatColor.GREEN
							+ "[EM] "
							+ ChatColor.YELLOW
							+ "Fireballs, Ender eyes, ender pearls, xp bottles, and spawn eggs");
					return true;
				} else {
					return false;
				}
			}
		}
		if (args[0].equalsIgnoreCase("reload")
				&& sender.hasPermission("entitymanager.admin")) {
			this.reloadConfig();
			sender.sendMessage(ChatColor.AQUA + "[EntityManager] "
					+ ChatColor.GREEN + "Version " + ChatColor.YELLOW + ver
					+ ChatColor.GREEN + " Config reloaded from disk");
			return true;
		}
		if (args[0].equalsIgnoreCase("crystal")
				&& sender.hasPermission("entitymanager.crystal")) {
			if (((Player) sender).getItemInHand().getTypeId() == 383) {
				((Player) sender).getItemInHand().setAmount(1);
				((Player) sender).getItemInHand().setDurability((short) 200);
				sender.sendMessage(ChatColor.AQUA + "[EM] " + ChatColor.GREEN
						+ " Egg converted to usable EnderCrystal spawnegg");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED
						+ " You must have a spawn egg in hand!");
			}
		}
		if (args[0].equalsIgnoreCase("mobs")
				&& sender.hasPermission("entitymanager.admin")) {
			boolean creeper = this.getConfig().getBoolean(
					"disabled.mobs.creeper");
			boolean skeleton = this.getConfig().getBoolean(
					"disabled.mobs.skeleton");
			boolean spider = this.getConfig()
					.getBoolean("disabled.mobs.spider");
			boolean zombie = this.getConfig()
					.getBoolean("disabled.mobs.zombie");
			boolean slime = this.getConfig().getBoolean("disabled.mobs.slime");
			boolean ghast = this.getConfig().getBoolean("disabled.mobs.ghast");
			boolean pigman = this.getConfig().getBoolean(
					"disabled.mobs.pig_zombie");
			boolean enderman = this.getConfig().getBoolean(
					"disabled.mobs.enderman");
			boolean cavespider = this.getConfig().getBoolean(
					"disabled.mobs.cave_spider");
			boolean silverfish = this.getConfig().getBoolean(
					"disabled.mobs.silverfish");
			boolean blaze = this.getConfig().getBoolean("disabled.mobs.blaze");
			boolean magmacube = this.getConfig().getBoolean(
					"disabled.mobs.magma_cube");
			boolean pig = this.getConfig().getBoolean("disabled.mobs.pig");
			boolean sheep = this.getConfig().getBoolean("disabled.mobs.sheep");
			boolean cow = this.getConfig().getBoolean("disabled.mobs.cow");
			boolean chicken = this.getConfig().getBoolean(
					"disabled.mobs.chicken");
			boolean squid = this.getConfig().getBoolean("disabled.mobs.squid");
			boolean wolf = this.getConfig().getBoolean("disabled.mobs.wolf");
			boolean mushroom = this.getConfig().getBoolean(
					"disabled.mobs.mushroom_cow");
			boolean snowman = this.getConfig().getBoolean(
					"disabled.mobs.snowman");
			boolean dragon = this.getConfig().getBoolean(
					"disabled.mobs.ender_dragon");
			boolean ocelot = this.getConfig()
					.getBoolean("disabled.mobs.ocelot");
			boolean vill = this.getConfig()
					.getBoolean("disabled.mobs.villager");
			boolean irongolem = this.getConfig().getBoolean(
					"disabled.mobs.iron_golem");
			ChatColor g = ChatColor.GREEN;
			ChatColor w = ChatColor.WHITE;
			sender.sendMessage(ChatColor.AQUA
					+ "------------Blocked mobs list------------");
			sender.sendMessage(ChatColor.WHITE + "Pig = " + g + pig + w
					+ "             Creeper = " + g + creeper);
			sender.sendMessage(ChatColor.WHITE + "Sheep = " + g + sheep + w
					+ "         Skeleton = " + g + skeleton);
			sender.sendMessage(ChatColor.WHITE + "Cow = " + g + cow + w
					+ "            Spider = " + g + spider);
			sender.sendMessage(ChatColor.WHITE + "Chicken = " + g + chicken + w
					+ "        Zombie = " + g + zombie);
			sender.sendMessage(ChatColor.WHITE + "Squid = " + g + squid + w
					+ "          Slime = " + g + slime);
			sender.sendMessage(ChatColor.WHITE + "Wolf = " + g + wolf + w
					+ "            Ghast = " + g + ghast);
			sender.sendMessage(ChatColor.WHITE + "Mooshroom = " + g + mushroom
					+ w + "    Pigman = " + g + pigman);
			sender.sendMessage(ChatColor.WHITE + "Snowman = " + g + snowman + w
					+ "       Enderman = " + g + enderman);
			sender.sendMessage(ChatColor.WHITE + "Ocelot = " + g + ocelot + w
					+ "          CaveSpider = " + g + cavespider);
			sender.sendMessage(ChatColor.WHITE + "Villager = " + g + vill + w
					+ "        Silverfish = " + g + silverfish);
			sender.sendMessage(ChatColor.WHITE + "Blaze = " + g + blaze + w
					+ "           MagmaCube = " + g + magmacube);
			sender.sendMessage(ChatColor.WHITE + "IronGolem = " + g + irongolem
					+ w + "     EnderDragon = " + g + dragon);
			sender.sendMessage(ChatColor.AQUA
					+ "-----------True = blocked mob------------");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			sender.sendMessage(ChatColor.GREEN + "EntityManager "
					+ ChatColor.YELLOW + ver + ChatColor.GREEN
					+ " by milkywayz loaded!");
			sender.sendMessage(ChatColor.GREEN
					+ "Always check bukkit dev page for latest version");
			sender.sendMessage(ChatColor.GREEN
					+ "Sumbit requests on bukkit dev to have them added!");
			sender.sendMessage(ChatColor.GREEN
					+ "If you find any issues or bugs, please report them!");
			sender.sendMessage(ChatColor.WHITE
					+ "*************************************");
			return true;
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ " Not enough permission to use that command!");
			return false;
		}
	}

	@Override
	public void onDisable() {
		log.info(getDescription().getName() + " "
				+ getDescription().getVersion() + " unloaded.");
	}

	private void setupPluginDependencies() {
		try {
			setupWorldGuard();
		} catch (Exception e) {
			log.warning("[EntityManager] Failed to load WorldGuard");
			e.printStackTrace();
		}
		try {
			setupEconomy();
		} catch (Exception e) {
			log.warning("[EntityManager] Failed to load Vault");
			e.printStackTrace();
		}
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			econ = null;
			log.warning("[EntityManager] Vault not found, economy support disabled");
		} else {
			log.info("[EntityManager] Hooked into Vault!");
		}
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private void setupWorldGuard() {
		Plugin wg = this.getServer().getPluginManager().getPlugin("WorldGuard");
		if (wg == null) {
			log.info("[EM] Worldguard not found");
		} else {
			Spawnegg.worldguardPlugin = (WorldGuardPlugin) wg;
			log.info("[EntityManager] Hooked into WorldGuard!");
		}
	}

	public eConfiguration config() {
		return config;
	}

	public void writeLog(String text) {
		Spawnegg.log.info(text);
	}

}
