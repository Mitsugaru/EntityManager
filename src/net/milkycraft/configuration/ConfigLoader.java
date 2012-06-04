package net.milkycraft.configuration;

import java.io.File;
import java.io.IOException;

import net.milkycraft.EntityManager;

import org.bukkit.configuration.file.FileConfiguration;

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
	protected String fileName;

	/**
	 * Instantiates a new config loader.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param fileName
	 *            the file name
	 */
	public ConfigLoader(EntityManager plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		dataFolder = plugin.getDataFolder();
		configFile = new File(dataFolder, File.separator + fileName);
	}

	/**
	 * Load the config file.
	 */
	protected abstract void load();

	/**
	 * Save the config file.
	 */
	private static void saveConfig() {
		try {
			config.save(configFile);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add the defaults to the config file.
	 */
	protected void addDefaults() {

		// Load from included config.yml
		config.options().copyDefaults(true);
		saveConfig();
	}

	/**
	 * Load the keys from this config file.
	 */
	protected abstract void loadKeys();

}