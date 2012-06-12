package net.milkycraft.configuration;

import java.util.List;

import net.milkycraft.EntityManager;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * The Class Settings.
 */
public class Settings extends ConfigLoader {
	/** The totalenchant. */
	public static Boolean Motd, alertz, metrics, logging, amrs, world,
			totalexp, totalenchant, update;
	public static String time;
	/** The arrowz. */
	public static Boolean doorBreak, enderPickup, pvp, mobdmg, fishing, arrowz, wmanager;
	
	/** The lava. */
	public static Boolean MonsEggs, ChickEggs, Fballs, xBottz, potionz, water,
			lava;
	
	/** The bitems. */
	public static List<Integer> items, bitems;
	
	/** The potion. */
	public static Boolean xpbott, fire, egg, pearl, eye, potion;
	
	/** The paintz. */
	public static Boolean boatz, cartz, paintz;
	
	/** The on death. */
	public static Boolean cDrop, sDrop, onDeath;
	
	/** The tripwires. */
	public static Boolean portals, tripwires, msdrops, msxp;
	
	/** The npc. */
	public static Integer mons, animal, npc, expwr;
	/** Disabled eggs. */
	public static Boolean creep, skele, spider, zombie, slime, ghast, pigman,
			ender, cave, fish, blaze, cube, pig, sheep, cow, chick, squid,
			wolf, moosh, ocelot, villa;
	/** Disabled mobs. */
	public static Boolean creeps, skeles, spiders, zombies, slimes, ghasts,
			pigmans, enders, caves, fishs, blazes, cubes, pigs, sheeps, cows,
			chicks, squids, snow, dragons, iron, wolfs, mooshs, ocelots,
			villas;
	
	/** The enderchest. */
	public static Boolean all, godcrops, dragegg, enderchest;

	/**
	 * Instantiates a new settings.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public Settings(EntityManager plugin) {
		super(plugin, "config.yml");
		config = plugin.getConfig();
		saveIfNotExist();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.milkycraft.configuration.ConfigLoader#load()
	 */
	@Override
	public void load() {
		// If it doesn't exist, copy it from the .jar
		if (!configFile.exists()) {
			dataFolder.mkdir();
			plugin.saveDefaultConfig();
		}
		addDefaults();
		loadKeys();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.milkycraft.configuration.ConfigLoader#loadKeys()
	 */
	@Override
	public void loadKeys() {
		plugin.writeLog("[EntityManager] Loading EntityManager config");
		Motd = config.getBoolean("EntityManager.Login-MOTD");
		alertz = config.getBoolean("EntityManager.Send-Alerts");
		metrics = config.getBoolean("EntityManager.Metrics");
		logging = config.getBoolean("EntityManager.Logging");
		update = config.getBoolean("EntityManager.Update-Checker");
		amrs = config.getBoolean("EntityManager.Advanced-Mob-Remover");
		world = WorldSettings.allworlds;
		totalexp = config.getBoolean("block.Actions.exp-drops");
		totalenchant = config.getBoolean("block.Actions.enchantmenet");
		doorBreak = config.getBoolean("block.Actions.zombie-door-break");
		enderPickup = config.getBoolean("block.Actions.ender-pickup");
		pvp = config.getBoolean("block.Actions.pvp");
		mobdmg = config.getBoolean("block.Actions.mob-damage");
		fishing = config.getBoolean("block.Actions.fishing");
		arrowz = config.getBoolean("block.Actions.arrows");
		MonsEggs = config.getBoolean("block.Dispense.SpawnerEggs");
		ChickEggs = config.getBoolean("block.Dispense.ChickenEggs");
		Fballs = config.getBoolean("block.Dispense.Fireballs");
		xBottz = config.getBoolean("block.Dispense.XpBottles");
		potionz = config.getBoolean("block.Dispense.Potions");
		water = config.getBoolean("block.Dispense.Water-Blocks");
		lava = config.getBoolean("block.Dispense.Lava-Blocks");
		items = config.getIntegerList("block.Dispense.Items");
		xpbott = config.getBoolean("block.Throw.XpBottles");
		fire = config.getBoolean("block.Throw.FireCharges");
		egg = config.getBoolean("block.Throw.ChickenEggs");
		pearl = config.getBoolean("block.Throw.EnderPearls");
		eye = config.getBoolean("block.Throw.EnderEyes");
		potion = config.getBoolean("block.Throw.Potions");
		boatz = config.getBoolean("block.Entities.Boats");
		cartz = config.getBoolean("block.Entities.Minecarts");
		paintz = config.getBoolean("block.Entities.Paintings");
		cDrop = config.getBoolean("block.ItemDrop.Creative-mode");
		sDrop = config.getBoolean("block.ItemDrop.Survival-mode");
		onDeath = config.getBoolean("block.ItemDrop.On-Death");
		bitems = config.getIntegerList("block.ItemDrop.Blacklisted-items");
		portals = config.getBoolean("block.Creation-of.portals");
		mons = config.getInt("Economy.charge.monster");
		animal = config.getInt("Economy.charge.animal");
		npc = config.getInt("Economy.charge.npc");
		creep = config.getBoolean("disabled.eggs.creeper");
		skele = config.getBoolean("disabled.eggs.skeleton");
		spider = config.getBoolean("disabled.eggs.spider");
		zombie = config.getBoolean("disabled.eggs.zombie");
		slime = config.getBoolean("disabled.eggs.slime");
		ghast = config.getBoolean("disabled.eggs.ghast");
		pigman = config.getBoolean("disabled.eggs.pigman");
		ender = config.getBoolean("disabled.eggs.enderman");
		cave = config.getBoolean("disabled.eggs.cavespider");
		fish = config.getBoolean("disabled.eggs.silverfish");
		blaze = config.getBoolean("disabled.eggs.blaze");
		cube = config.getBoolean("disabled.eggs.magmacube");
		pig = config.getBoolean("disabled.eggs.pig");
		sheep = config.getBoolean("disabled.eggs.sheep");
		cow = config.getBoolean("disabled.eggs.cow");
		chick = config.getBoolean("disabled.eggs.chicken");
		squid = config.getBoolean("disabled.eggs.squid");
		wolf = config.getBoolean("disabled.eggs.wolf");
		moosh = config.getBoolean("disabled.eggs.mooshroom");
		ocelot = config.getBoolean("disabled.eggs.ocelot");
		villa = config.getBoolean("disabled.eggs.villager");
		creeps = config.getBoolean("disabled.mobs.creeper");
		skeles = config.getBoolean("disabled.mobs.skeleton");
		spiders = config.getBoolean("disabled.mobs.spider");
		zombies = config.getBoolean("disabled.mobs.zombie");
		slimes = config.getBoolean("disabled.mobs.slime");
		ghasts = config.getBoolean("disabled.mobs.ghast");
		pigmans = config.getBoolean("disabled.mobs.pig_zombie");
		enders = config.getBoolean("disabled.mobs.enderman");
		caves = config.getBoolean("disabled.mobs.cavespider");
		fishs = config.getBoolean("disabled.mobs.silverfish");
		blazes = config.getBoolean("disabled.mobs.blaze");
		cubes = config.getBoolean("disabled.mobs.magma_cube");
		pigs = config.getBoolean("disabled.mobs.pig");
		sheeps = config.getBoolean("disabled.mobs.sheep");
		cows = config.getBoolean("disabled.mobs.cow");
		chicks = config.getBoolean("disabled.mobs.chicken");
		squids = config.getBoolean("disabled.mobs.squid");
		wolfs = config.getBoolean("disabled.mobs.wolf");
		mooshs = config.getBoolean("disabled.mobs.mushroom_cow");
		ocelots = config.getBoolean("disabled.mobs.ocelot");
		villas = config.getBoolean("disabled.mobs.villager");
		dragons = config.getBoolean("disabled.mobs.ender_dragon");
		iron = config.getBoolean("disabled.mobs.iron_golen");
		snow = config.getBoolean("disabled.mobs.snowman");
		godcrops = config.getBoolean("block.Actions.crop-damage");
		dragegg = config.getBoolean("block.Place.Dragon-Eggs");
		enderchest = config.getBoolean("block.Place.Ender-Chests");
		tripwires = config.getBoolean("block.Place.Trip-Wires");
		expwr = config.getInt("Explosions.Level");
		time = config.getString("WorldManager.Always");
		msxp = config.getBoolean("block.Monster-Spawner.Xp-Drops");
		msdrops = config.getBoolean("block.Monster-Spawner.Mob-Drops");
		wmanager = config.getBoolean("WorldManager.Enabled");
	}

	/**
	 * Used for config booleanes like ("disabled.mobs." +
	 * ev.getEntityType().toLowerCase())
	 * 
	 * @return Return the config
	 */
	public static FileConfiguration getConfig() {
		return config;
	}
	
}
