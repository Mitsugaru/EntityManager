package net.milkycraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

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
import net.milkycraft.metrics.MetricsStarter;
import net.milkycraft.permissions.PermissionHandler;
import net.milkycraft.permissions.PermissionNode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Main Class of EntityManager.
 * 
 * @author milkywayz
 * @version 3.8.1
 */
@SuppressWarnings("unused")
public class EntityManager extends JavaPlugin {

	/** The maindirectory. */
	public static String maindirectory;

	/** The latest version. */
	private static String latestVersion = null;

	/** The version diff. */
	private static boolean versionDiff = false;

	/** The file. */
	public static File file = new File(maindirectory + File.separator
			+ "config.yml");

	/** The worldguard plugin. */
	protected static WorldGuardPlugin worldguardPlugin = null;

	/** The econ. */
	public static Economy econ = null;

	/** The main. */
	public static EntityManager main;

	/** The entitymanager. */
	public static File entitymanager;

	/** The emce. */
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

	/**
	 * Sets the up paths.
	 */
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
			writeLog("[EntityManager] Failed to hook into Vault");
		} else {
			writeLog("[EntityManager] Sucessfully hooked into Vault");
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
			writeLog("[EntityManager] Sucessfully hooked into WorldGuard");

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
		Logger.getLogger("Minecraft").info(text);
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
		Logger.getLogger("Minecraft").warning(warning);
	}

	/**
	 * Sets up metrics.
	 */
	public final void setUpMetrics() {
		if(Settings.metrics) {
		final MetricsStarter metricsStarter = new MetricsStarter();	
		getServer().getScheduler().scheduleAsyncDelayedTask(this, metricsStarter, 100);
		writeLog("[EntityManager] Metrics loaded!");
		}
	}

	/**
	 * Load worlds.
	 */
	private void loadWorlds() {
		int x = 0;
		for (String s : WorldSettings.worlds) {
			for (World w : Bukkit.getWorlds()) {
				if (s.equalsIgnoreCase(w.getName())) {
					writeLog("EntityManager is enabled in the world: (" + s
							+ ")");
					x++;
				}
			}
		}
		writeLog("A total of " + x + " world(s) were found of "
				+ Bukkit.getWorlds().size() + " total worlds on server");
	}

	/**
	 * Schedule the world time changer if needed.
	 */
	public final void schedule() {
		if (factors() && Settings.wmanager) {
			for (String s : Settings.worldz) {
				writeLog("(" + s + ") will always stay: "
						+ Settings.time);
			}
			getServer().getScheduler().scheduleSyncRepeatingTask(this,
					new Runnable() {
						@Override
						public void run() {
							TimeManager.getTimeManager().adjustTime();
						}
					}, 95L, 1200L);
		}
			if (Settings.update) {
				getServer().getScheduler().scheduleAsyncRepeatingTask(this,
						new UpdateCheck(), 40, 432000);
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
		/*
		 * In the event of a bad reload/forced restart, etc... st can sometimes
		 * be null which frucks everything up
		 */
		if (st == null) {
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
	 * Tell admins if theres an update available. Doesnt check for settings
	 * because this cant get called unless update checking is on
	 * 
	 * @param message
	 *            the message
	 */
	public void tell(String message) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (PermissionHandler.has(p, PermissionNode.ADMIN)) {
				p.sendMessage(ChatColor.YELLOW
						+ "EntityManager has an update available!");
				p.sendMessage(ChatColor.GRAY + message);
				p.sendMessage(ChatColor.YELLOW
						+ "http://dev.bukkit.org/server-mods/entitymanager/");
			}
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

	/**
	 * The Class UpdateCheck.
	 */
	public class UpdateCheck implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				final String address = "http://updates.milkycraft.net/";
				final URL url = new URL(address.replace(" ", "%20"));
				final URLConnection connection = url.openConnection();
				connection.setConnectTimeout(8000);
				connection.setReadTimeout(15000);
				connection.setRequestProperty("User-agent", "EntityManager "
						+ EntityManager.getMainClass().getDescription()
								.getVersion());
				final BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String version;
				if ((version = bufferedReader.readLine()) != null) {
					EntityManager.latestVersion = version;
					if (!EntityManager.getMainClass().getDescription()
							.getVersion().equals(version)) {
						writeLog("Found a new version available: "
								+ version);
						writeLog("Check http://dev.bukkit.org/server-mods/entitymanager/");
						derpTell();
						EntityManager.versionDiff = true;
					}
					bufferedReader.close();
					connection.getInputStream().close();
					return;
				} else {
					bufferedReader.close();
					connection.getInputStream().close();
				}
			} catch (final Exception e) {
			}
			writeWarn("Error: Could not check if plugin was up to date. Will try later");
		}
	}

	/**
	 * Derp tell.
	 */
	public void derpTell() {
		try {
			final String address = "http://updates.milkycraft.net/messages";
			final URL url = new URL(address.replace(" ", "%20"));
			final URLConnection connection = url.openConnection();
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(15000);
			connection.setRequestProperty("User-agent", "EntityManager ");
			final BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String message;
			if ((message = bufferedReader.readLine()) != null) {
				tell(message);
				bufferedReader.close();
				connection.getInputStream().close();
				return;
			} else {
				bufferedReader.close();
				connection.getInputStream().close();
				tell("Update message is null");
			}
		} catch (final Exception e) {
			writeWarn("Error: Could not check if plugin was up to date. Will try later");
		}
	}
}
