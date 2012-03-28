package net.milkycraft;

import net.milkycraft.ASEConfiguration.EggConfiguration;

public class EggWrapper extends org.bukkit.plugin.java.JavaPlugin {
    private EggConfiguration config;
    
    @Override
	public EggConfiguration getConfig() {
        if(config == null) {
            config = new EggConfiguration(this);
        }
        return config;
    }
    @Override
	public void reloadConfig() {
        getConfig().load();
    }
    @Override
	public void saveConfig() {
        getConfig().save();
    }
    @Override
	public void saveDefaultConfig() {
        getConfig().saveDefaults();
    }
}