package net.milkycraft.configuration;

import java.io.InputStream;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.milkycraft.EntityManager;

public class WorldSettings extends ConfigLoader {
    private static WorldSettings instance;
    public static List<String> worlds, worldz;
    public static boolean allworlds;
    private FileConfiguration newConf = null;

    public WorldSettings(EntityManager plugin) {
        super(plugin, "worlds.yml");
        saveIfNotExist();
    }


    public static WorldSettings getInstance() {
        if (instance == null) {
            instance = new WorldSettings(EntityManager.main);
            instance.load();
        }

        return instance;
    }

    @Override
    public void load() {
        if (plugin.getResource("worlds.yml") != null) {
            loadKeys();
        }
    }

    @Override
    protected void loadKeys() {
    	plugin.writeLog("[EntityManager] Loading worlds file");
        worlds = config.getStringList("World.Worlds");
   
        worldz = config.getStringList("WorldManager.Worlds");
        allworlds = config.getBoolean("World.All");
    }
    public void reloadConfig() {
        newConf = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = EntityManager.getMainClass().getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            newConf.setDefaults(defConfig);
        }
    }
}