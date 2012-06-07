package net.milkycraft;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkycraft.api.DropManager;
import net.milkycraft.api.TimeManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.executors.EntityManagerCommandExecutor;
import net.milkycraft.listeners.BlockPlaceListener;
import net.milkycraft.listeners.DispenserListener;
import net.milkycraft.listeners.EnchantmentListener;
import net.milkycraft.listeners.EntitiesListener;
import net.milkycraft.listeners.EntitySpawnListener;
import net.milkycraft.listeners.ExpListener;
import net.milkycraft.listeners.ExplosionListener;
import net.milkycraft.listeners.LoginListener;
import net.milkycraft.listeners.SpawnEggListener;
import net.milkycraft.listeners.TargetListener;
import net.milkycraft.listeners.ThrowListener;
import net.milkycraft.metrics.Metrics;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * The Main Class of EntityManager.
 * 
 * @author milkywayz
 * @version 3.7
 */
public class EntityManager extends JavaPlugin {
	public static String maindirectory = "plugins" + File.separator
			+ "EntityManager";
	public static File file = new File(maindirectory + File.separator
			+ "config.yml");
	protected static final Logger log = Logger.getLogger("Minecraft");
	protected static WorldGuardPlugin worldguardPlugin = null;
	public static Economy econ = null;
	public static EntityManager main;
	public static File entitymanager;
	private EntityManagerCommandExecutor emce;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		main = this;
		entitymanager = this.getFile();
		new File(maindirectory).mkdir();
		Settings config = new Settings(this);
		config.load();
		setupPluginDependencies();
		loadWorlds();
		setUpListeners();
		setUpExecutors();
		schedule();
		verify();
		setUpMetrics();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(main);
		writeLog("[EntityManager] Scheduled tasks shutting down.");
		writeLog("[" + getDescription().getName() + "] "
				+ getDescription().getVersion() + " unloaded.");
	}

	/**
	 * Sets the up executors.
	 */
	private void setUpExecutors() {
		emce = new EntityManagerCommandExecutor();
		getCommand("entitymanager").setExecutor(emce);
	}

	/**
	 * Sets the up listeners.
	 */
	private void setUpListeners() {
		getServer().getPluginManager().registerEvents(new DispenserListener(),
				this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(),
				this);
		getServer().getPluginManager()
				.registerEvents(new LoginListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnEggListener(),
				this);
		getServer().getPluginManager().registerEvents(
				new EntitySpawnListener(), this);
		getServer().getPluginManager().registerEvents(new TargetListener(),
				this);
		getServer().getPluginManager().registerEvents(new ExplosionListener(),
				this);
		getServer().getPluginManager()
				.registerEvents(new ThrowListener(), this);
		getServer().getPluginManager().registerEvents(new ExpListener(), this);
		getServer().getPluginManager().registerEvents(
				new EnchantmentListener(), this);
		getServer().getPluginManager().registerEvents(new EntitiesListener(),
				this);
		getServer().getPluginManager().registerEvents(new DropManager(), this);
	}

	/**
	 * Setup plugin dependencies.
	 */
	private void setupPluginDependencies() {
		try {
			setupWorldGuard();
		} catch (final Exception e) {
			writeWarn("[EntityManager] Failed to load WorldGuard.");
			e.printStackTrace();
		}
		try {
			setupEconomy();
		} catch (final Exception e) {
			writeWarn("[EntityManager] Failed to load Vault.");
			e.printStackTrace();
		}
	}

	/**
	 * Setup economy.
	 * 
	 * @return true, if successful
	 * @category Setting up dependencies
	 */
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			econ = null;
			writeLog("[EntityManager] Couldn't hook into Vault.");
		} else {
			writeLog("[EntityManager] Hooked into Vault!");
		}
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		final RegisteredServiceProvider<Economy> rsp = getServer()
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
		final Plugin wg = this.getServer().getPluginManager()
				.getPlugin("WorldGuard");
		if (wg == null) {
			writeLog("[EntityManager] Didn't find WorldGuard, Ignoring Regions.");
		} else {
			EntityManager.worldguardPlugin = (WorldGuardPlugin) wg;
			log.info("[EntityManager] Hooked into WorldGuard, Respecting Regions.");
		}

	}

	/**
	 * Write a message to console.
	 * 
	 * @param text
	 *            log text
	 * @author milkywayz
	 * @category Logging
	 * @since 3.7
	 */
	public void writeLog(String text) {
		log.info(text);
	}

	/**
	 * Write a warning to console.
	 * 
	 * @param warning
	 *            the warning
	 * @author milkywayz
	 * @category Logging
	 * @since 3.7
	 */
	public void writeWarn(String warning) {
		log.warning(warning);
	}

	/**
	 * Sets up metrics.
	 */
	public final void setUpMetrics() {
		if (Settings.metrics) {
			try {
				Metrics metrics = new Metrics(this);
				metrics.beginMeasuringPlugin(this);
			} catch (IOException e) {
				writeLog("Metrics failed to load!");
			}
		}
	}

	private void loadWorlds() {
		int x = 0;
			for (String s : Settings.worlds) {
				for(World w : Bukkit.getWorlds()) {
				if (s.equalsIgnoreCase(w.getName())) {
					writeLog("[EntityManager] EntityManager will work in the world: (" + s + ")");
					x++;
				}
			}
			}
		writeLog("[EntityManager] A total of " + x + " world(s) were found of " + Bukkit.getWorlds().size() + " total worlds on server");
	}

	/**
	 * Schedule the world time changer if needed
	 */
	public final void schedule() {
		if (factors()) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this,
					new Runnable() {
						public void run() {
							TimeManager.getTimeManager().adjustTime();
						}
					}, 100L, 1200L);
		}
	}

	/**
	 * Determines wether to start the repeating task or not We dont want to
	 * waste resources if not needed.
	 * 
	 * @return true if time in config is not normal/regular
	 * @author milkywayz
	 * @since 3.7
	 */
	public boolean factors() {
		String st = Settings.time;
		for (String ws : Settings.worldz) {
			if (ws.equalsIgnoreCase("add_a_world_u_always_want_to_be_night_or_whatever_you_pick")) {
				return false;
			}
		}
		if (st.equalsIgnoreCase("normal") || st.equalsIgnoreCase("regular")) {
			return false;
		}
		return true;
	}

	/**
	 * Verify the server's online mode.
	 * 
	 * @author milkywayz
	 * @category Verification
	 * @since 3.6.1
	 */
	public final void verify() {
		if (!Bukkit.getServer().getOnlineMode()) {
			log.log(Level.SEVERE,
					"EntityManager does not condone of cracked servers!");
			log.log(Level.SEVERE,
					"Cracked servers are breeding ground for hackers!");
		}
	}

	/**
	 * Get the main class for EntityManager.
	 * 
	 * @return the main class
	 * @author milkywayz
	 * @since 3.7
	 */
	public static final EntityManager getMainClass() {
		return main;
	}

}
