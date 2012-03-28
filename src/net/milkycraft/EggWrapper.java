package net.milkycraft;

import net.milkycraft.ASEConfiguration.EggConfiguration;

public class EggWrapper extends org.bukkit.plugin.java.JavaPlugin {
    private EggConfiguration config;
    
    public EggConfiguration getConfig() {
        if(config == null) {
            config = new EggConfiguration(this);
        }
        return config;
    }
    public void reloadConfig() {
        getConfig().load();
    }
    public void saveConfig() {
        getConfig().save();
    }
    public void saveDefaultConfig() {
        getConfig().saveDefaults();
    }
}