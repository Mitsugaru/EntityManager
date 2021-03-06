package net.milkycraft.api;

import org.bukkit.Bukkit;
import org.bukkit.World;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeManager.
 */
public class TimeManager extends EntityManager {

	/** The st. */
	private String st = Settings.time;

	/** The Constant instance. */
	private static final TimeManager instance = new TimeManager();

	/**
	 * Changes time of world based on config.
	 * <p>
	 * Keep for loop because we adjust all worlds specified at the same time
	 * <p>
	 * 
	 * @author milkywayz
	 * @since 3.7
	 */
	public void adjustTime() {
		for (String s : Settings.worldz) {
			try {
				World world = Bukkit.getServer().getWorld(s);
				world.setFullTime(getTime());
			} catch (NullPointerException x) {
				writeWarn("The world: (" + s + ") is not a valid world!");
			}
		}
	}

	/**
	 * Parses the config value into a long usable by the setFullTime() method.
	 * 
	 * @return the Long time based on String from config
	 * @author milkywayz
	 * @see Settings
	 * @since 3.7
	 */
	public Long getTime() {
		if (st == null) {
			throw new NullPointerException("Time cannot be null!");
		}
		if (st.equalsIgnoreCase("6am") || st.equalsIgnoreCase("day")
				|| st.equalsIgnoreCase("sunny")) {
			return 0L;
		}
		if (st.equalsIgnoreCase("noon") || st.equalsIgnoreCase("midday")
				|| st.equalsIgnoreCase("fullbright")) {
			return 6000L;
		}

		if (st.equalsIgnoreCase("night") || st.equalsIgnoreCase("midnight")
				|| st.equalsIgnoreCase("dark")) {
			return 14000L;
		}
		if (st.equalsIgnoreCase("evening") || st.equalsIgnoreCase("5pm")
				|| st.equalsIgnoreCase("17:30")) {
			return 11500L;
		}
		if (st.equalsIgnoreCase("dawn") || st.equalsIgnoreCase("morning")
				|| st.equalsIgnoreCase("5am")) {
			return 23000L;
		}
		/*
		 * Just set to 0 if they made a mistake
		 */
		writeWarn("Always: " + st + " ,is not a valid time!");
		return 0L;
	}

	/**
	 * Gets the time manager.
	 * 
	 * @return the time manager
	 */
	public static TimeManager getTimeManager() {
		return instance;
	}
}