package net.milkycraft.api;

import org.bukkit.Bukkit;
import org.bukkit.World;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

public class TimeManager extends EntityManager {
	private String st = Settings.time;
	private static final TimeManager instance = new TimeManager();

	/**
	 * Changes time of world based on config
	 * 
	 * @author milkywayz
	 * @since 3.7
	 */
	public void adjustTime() {
		for(String s : Settings.worldz) {
			try {
			World world = Bukkit.getServer().getWorld(s);
			world.setFullTime(getTime());
			} catch (NullPointerException x) {
				writeWarn("The world name : " + s +" is not a valid world!");
			}
		}
	}

	/**
	 * Parses the config value into a long usable by the setFullTime() method
	 * 
	 * @author milkywayz
	 * @see Settings
	 * @since 3.7
	 * @throws NullPointerException
	 *             if time is null
	 * @return the Long time based on String from config
	 */
	public Long getTime() {
		if (st == null) {
			throw new NullPointerException("Time cannot be null!");
		}
		if (st.equalsIgnoreCase("noon") || st.equalsIgnoreCase("day")
				|| st.equalsIgnoreCase("sunny")) {
			return 0L;
		}
		if (st.equalsIgnoreCase("night") || st.equalsIgnoreCase("midnight")
				|| st.equalsIgnoreCase("dark")) {
			return 14000L;
		}
		if (st.equalsIgnoreCase("dawn") || st.equalsIgnoreCase("morning")
				|| st.equalsIgnoreCase("5am")) {
			return 23000L;
		}
		/*
		 * Just set to day if they made a mistake
		 */
		writeWarn("Always: " + st + " ,is not a valid time!");
		return 0L;
	}

	public static TimeManager getTimeManager() {
		return instance;
	}
}