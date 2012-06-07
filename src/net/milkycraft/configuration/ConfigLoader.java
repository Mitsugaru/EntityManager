package net.milkycraft.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.milkycraft.EntityManager;

import org.bukkit.configuration.file.FileConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigLoader.
 */
public abstract class ConfigLoader {

	/** The config file. */
	protected static File configFile;
	
	/** The data folder. */
	protected static File dataFolder;
	
	/** The plugin. */
	protected final EntityManager plugin;
	
	/** The config. */
	protected static FileConfiguration config;
	
	/** The file name. */
	protected static String fileName;
	
	/** The resource. */
	protected static InputStream resource;

	/**
	 * Instantiates a new config loader.
	 *
	 * @param plugin the plugin
	 * @param fileName the file name
	 */
	public ConfigLoader(EntityManager plugin, String fileName) {
		this.plugin = plugin;
		ConfigLoader.fileName = fileName;
		dataFolder = plugin.getDataFolder();
		configFile = new File(dataFolder, File.separator + fileName);
		resource = plugin.getResource(fileName);
	}

	/**
	 * Load.
	 */
	protected abstract void load();

	/**
	 * Save config.
	 */
	public static void saveConfig() {
		try {
			config.save(configFile);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the defaults.
	 */
	protected void addDefaults() {

		// Load from included config.yml
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	/**
	 * Load keys.
	 */
	protected abstract void loadKeys();

}