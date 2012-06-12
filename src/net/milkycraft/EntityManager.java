package net.milkycraft;

import java.io.File;
import java.io.IOException;

import net.milkbowl.vault.economy.Economy;
import net.milkycraft.api.DropManager;
import net.milkycraft.api.TimeManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;
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
import net.milkycraft.permissions.PermissionHandler;

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
 * @version 3.7.2
 */
public class EntityManager extends JavaPlugin {
	public static String maindirectory;
	public static File file = new File(maindirectory + File.separator
			+ "config.yml");
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
		setUpPaths();
		Settings config = new Settings(this);
		config.load();
		PermissionHandler.init(this);
		WorldSettings.getInstance();
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
		writeLog("Scheduled tasks shutting down.");
	}
	private void setUpPaths() {
		entitymanager = getFile();
		maindirectory = getDataFolder().getPath() + File.separator;
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
		} catch (Exception e) {
			writeWarn("Failed to load WorldGuard.");
			e.printStackTrace();
		}
		try {
			setupEconomy();
		} catch (Exception e) {
			writeWarn("Failed to load Vault.");
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
			writeLog("Failed to hook into Vault");
		} else {
			writeLog("Sucessfully hooked into Vault");
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
			writeLog("Didn't find WorldGuard, Ignoring Regions.");
		} else {
			EntityManager.worldguardPlugin = (WorldGuardPlugin) wg;	
			getLogger().info("Sucessfully hooked into WorldGuard");
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
		getLogger().info(text);
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
		getLogger().warning(warning);
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
		for (String s : WorldSettings.worlds) {
			for (World w : Bukkit.getWorlds()) {
				if (s.equalsIgnoreCase(w.getName())) {
					writeLog("EntityManager is enabled in the world: ("
							+ s + ")");
					x++;
				}
			}
		}
		writeLog("A total of " + x + " world(s) were found of "
				+ Bukkit.getWorlds().size() + " total worlds on server");
	}

	/**
	 * Schedule the world time changer if needed
	 */
	public final void schedule() {
		if (factors()) {	
			for(String s : WorldSettings.worldz) {
				writeLog("(" + s + ") is scheduled to always stay " + Settings.time);
			}
			getServer().getScheduler().scheduleSyncRepeatingTask(this,
					new Runnable() {
						public void run() {
							TimeManager.getTimeManager().adjustTime();
						}
					}, 95L, 1200L);
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
		if(Settings.wmanager) {
			return false;
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
			writeWarn("EntityManager does not condone of cracked servers!");
			writeWarn("Cracked servers are breeding ground for hackers!");
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
