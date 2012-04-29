/*
 * 
 */
package net.milkycraft.ASEConfiguration;

import java.io.File;
import java.io.IOException;

import net.milkycraft.Spawnegg;

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
    protected final Spawnegg plugin;
    
    /** The config. */
    protected static FileConfiguration config;

    /**
     * Instantiates a new config loader.
     *
     * @param plugin the plugin
     * @param fileName the file name
     */
    public ConfigLoader(Spawnegg plugin, String fileName){
        this.plugin = plugin;
        dataFolder = plugin.getDataFolder();
        configFile = new File(dataFolder, File.separator + fileName);
    }

    /**
     * Load this config file.
     */
    protected abstract void load();
    /**
     * Save this config file.
     */
    private static void saveConfig() {
        try {
            config.save(configFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the defaults to this config file.
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

    /**
     * Reload.
     */
    protected abstract void reload();
}