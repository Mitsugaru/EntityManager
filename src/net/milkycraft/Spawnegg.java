/*
 * 
 */
package net.milkycraft;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkycraft.ASEConfiguration.Settings;
import net.milkycraft.Enums.EntityType;
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
import net.milkycraft.api.DropManager;
import net.milkycraft.api.EntityManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * The Mainclass for EntityManager
 */
public class Spawnegg extends JavaPlugin {

	/** The maindirectory. */
	public static String maindirectory = "plugins" + File.separator
			+ "EntityManager";

	/** The file. */
	public static File file = new File(maindirectory + File.separator
			+ "config.yml");

	/** The log. */
	public static Logger log = Logger.getLogger("Minecraft");

	/** The worldguard plugin. */
	public static WorldGuardPlugin worldguardPlugin = null;

	/** The econ. */
	public static Economy econ = null;

	/** The config. */
	public static Settings config;

	/** How to reference main class in other classes in a static manner */
	public static Spawnegg p;

	/** The entitymanager. */
	public static File entitymanager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {		
		p = this;
		entitymanager = this.getFile();
		new File(maindirectory).mkdir();
		setupPluginDependencies();
		Spawnegg.config = new Settings(this);
		Spawnegg.config.load();
		this.getServer().getPluginManager()
				.registerEvents(new MyDispenseListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new LoginListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new MySpawnEggListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new SpawnListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new TargetListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new ThrowListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new ExpListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new EnchantListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new EntitiesListener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new DropManager(), this);
		if (Settings.metrics) {
			try {
				final Metrics metrics = new Metrics(this);
				metrics.beginMeasuringPlugin(this);
			} catch (IOException e) {
				writeLog(e.getMessage());
			}
			writeLog("[EntityManager] Metrics loaded!");
			writeLog("[EntityManager] Successfully loaded!");
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
			sender.sendMessage(ChatColor.GOLD + "Logging: " + ChatColor.RED
					+ EntityManager.getManager().isMetricsEnabled()
					+ ChatColor.GOLD + "      Metrics: " + ChatColor.RED
					+ EntityManager.getManager().isMetricsEnabled());
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
					ChatColor a = ChatColor.AQUA;
					ChatColor y = ChatColor.YELLOW;
					sender.sendMessage(ChatColor.GREEN + "[EM]"
							+ ChatColor.GOLD + args[1] + ChatColor.RED
							+ "'s inventory was purged of: ");
					sender.sendMessage(ChatColor.GREEN + "[EM]"
							+ ChatColor.YELLOW + "Potions: " + a + pot + y
							+ "           FireCharges: " + a + fire);
					sender.sendMessage(ChatColor.GREEN + "[EM]"
							+ ChatColor.YELLOW + "ChickenEggs: " + a + egg + y
							+ "           XpBottles: " + a + exp);
					sender.sendMessage(ChatColor.GREEN + "[EM]"
							+ ChatColor.YELLOW + "EnderPearl: " + a + end + y
							+ "           EnderEye: " + a + eye);
					return true;
				}
			}
		}
		if (args[0].equalsIgnoreCase("reload")
				&& sender.hasPermission("entitymanager.admin")) {
			this.reloadConfig();
			Settings.getInstance().reload();
			sender.sendMessage(ChatColor.DARK_AQUA + "[EM]" + ChatColor.GREEN
					+ "Configuration reloaded");
			return true;
		}
		if (args[0].equalsIgnoreCase("test")) {
			/*
			 * Test command for methods and config stuff Isn't advertised on
			 * bukkitdev since it does nothing in releases
			 */
		}
		if (cmd.getName().equalsIgnoreCase("entitymanager")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("canspawn")) {
					if (sender.hasPermission("entitymanager.admin")) {
						try {
							String mob = args[1].toUpperCase();
							if (!EntityManager.getManager().canSpawn(
									EntityType.valueOf(args[1].toUpperCase()))) {
								sender.sendMessage(ChatColor.GREEN + "[EM]"
										+ "The mob: " + ChatColor.YELLOW
										+ mob.toLowerCase() + "'s "
										+ ChatColor.RED + "can't spawn.");
							} else {
								sender.sendMessage(ChatColor.GREEN + "[EM]"
										+ "The mob: " + ChatColor.YELLOW
										+ mob.toLowerCase() + "'s "
										+ ChatColor.GREEN + "can spawn.");
							}
							return true;
						} catch (ArrayIndexOutOfBoundsException e) {
							sender.sendMessage(ChatColor.RED
									+ "Correct usage: /em canspawn EntityType");
							log.log(Level.WARNING,
									e.getMessage() + " : " + e.getCause());
						} catch (NullPointerException e) {
							sender.sendMessage(ChatColor.RED
									+ "Correct usage: /em canspawn EntityType");
							log.log(Level.WARNING,
									e.getMessage() + " : " + e.getCause());
						} catch (IllegalArgumentException e) {
							sender.sendMessage(ChatColor.RED
									+ "There is no Entity: " + ChatColor.YELLOW
									+ args[1].toLowerCase() + ChatColor.WHITE
									+ ".");
							log.log(Level.WARNING,
									e.getMessage() + " : " + e.getCause());
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
			ChatColor w = ChatColor.YELLOW;
			sender.sendMessage(ChatColor.AQUA
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
			sender.sendMessage(ChatColor.GOLD + "Logging: " + ChatColor.RED
					+ EntityManager.getManager().isLogging() + ChatColor.GOLD
					+ "      Metrics: " + ChatColor.RED
					+ EntityManager.getManager().isMetricsEnabled());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		log.info(getDescription().getName() + " "
				+ getDescription().getVersion() + " unloaded.");
	}

	/**
	 * Setup plugin dependencies.
	 */
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

	/**
	 * Setup economy.
	 * 
	 * @return
	 */
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

	/**
	 * Setup world guard.
	 */
	private void setupWorldGuard() {
		Plugin wg = this.getServer().getPluginManager().getPlugin("WorldGuard");
		if (wg == null) {
			log.info("[EM] Worldguard not found");
		} else {
			Spawnegg.worldguardPlugin = (WorldGuardPlugin) wg;
			log.info("[EntityManager] Hooked into WorldGuard!");
		}
	}

	/**
	 * Write log.
	 * 
	 * @param text
	 *            the text
	 */
	public void writeLog(String text) {
		Spawnegg.log.info(text);
	}

	/**
	 * Gets the api.
	 * 
	 * @return The EntityManager developers API class
	 */
	public EntityManager getApi() {
		return EntityManager.getManager();
	}

	/**
	 * Gets the drop api.
	 * 
	 * @return The EntityManager Drop API.. It isnt much yet
	 */
	public DropManager getDropApi() {
		return DropManager.getDropManager();
	}
}
