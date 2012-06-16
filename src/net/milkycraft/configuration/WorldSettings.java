package net.milkycraft.configuration;

import java.io.InputStream;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.milkycraft.EntityManager;

// TODO: Auto-generated Javadoc
/**
 * The Class WorldSettings.
 */
public class WorldSettings extends ConfigLoader {

	/** The instance. */
	private static WorldSettings instance;

	/** The worldz. */
	public static List<String> worlds;

	/** The allworlds. */
	public static boolean allworlds;

	/** The new conf. */
	private FileConfiguration newConf = null;

	/**
	 * Instantiates a new world settings.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public WorldSettings(EntityManager plugin) {
		super(plugin, "worlds.yml");
		saveIfNotExist();
	}

	/**
	 * Gets the single instance of WorldSettings.
	 * 
	 * @return single instance of WorldSettings
	 */
	public static WorldSettings getInstance() {
		if (instance == null) {
			instance = new WorldSettings(EntityManager.main);
			instance.load();
		}

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.milkycraft.configuration.ConfigLoader#load()
	 */
	@Override
	public void load() {
		if (plugin.getResource("worlds.yml") != null) {
			loadKeys();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.milkycraft.configuration.ConfigLoader#loadKeys()
	 */
	@Override
	protected void loadKeys() {
		plugin.writeLog("[EntityManager] Loading worlds file");
		worlds = config.getStringList("World.Worlds");		
		allworlds = config.getBoolean("World.All");
	}

	/**
	 * Reload config.
	 */
	public void reloadConfig() {
		newConf = YamlConfiguration.loadConfiguration(configFile);
		InputStream defConfigStream = EntityManager.getMainClass().getResource(
				"config.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			newConf.setDefaults(defConfig);
		}
	}
}