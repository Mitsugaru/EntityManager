package net.milkycraft.ASEConfiguration;

import java.util.List;

import net.milkycraft.Spawnegg;

public class Settings extends ConfigLoader {

	/*
	 * GENERAL SETTINGS
	 */

	/* General Settings */
	public static Boolean Motd, alertz, metrics, logging, kicking;

	/* World Settings */
	public static List<String> worlds;

	/* Disabled */
	public static Boolean totalexp, totalenchant;
	/* Actions */
	public static Boolean doorBreak, enderPickup, pvp, mobdmg, fishing, arrowz;
	/* Dispense */
	public static Boolean MonsEggs, ChickEggs, Fballs, xBottz, potionz;
	public static List<Integer> items;
	/* Throw */
	public static Boolean xpbott, fire, egg, pearl, eye, potion;
	/* Entities */
	public static Boolean boatz, cartz, paintz;
	/* ItemDrop */
	public static Boolean cDrop, sDrop, onDeath, bDestroy;
	public static List<Integer> bitems;
	/* creation of */
	public static Boolean portals;
	/* Economy */
	public static Integer mons, animal, npc;
	/* Disabled Eggs */
	public static Boolean creep, skele, spider, zombie, slime, ghast, pigman,
			ender, cave, fish, blaze, cube, pig, sheep, cow, chick, squid,
			wolf, moosh, ocelot, villa;
	/* Disabled mobs */
	public static Boolean creeps, skeles, spiders, zombies, slimes, ghasts, pigmans, enders,
			caves, fishs, blazes, cubes, pigs, sheeps, cows, chicks, squids, snow, dragons, iron,
			wolfs, mooshs, ocelots, villas;
	public static Boolean all;
	public static ConfigLoader cl = null;

	/*
	 * CONFIG LOADING
	 */

	public Settings(Spawnegg plugin) {
		super(plugin, "config.yml");
		config = plugin.getConfig();
	}

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
	@Override
	protected void loadKeys() {
		plugin.getLogger().info("Loading EntityManager config");

		Motd = config.getBoolean("EntityManager.Login-MOTD");
		alertz = config.getBoolean("EntityManager.Send-Alerts");
		metrics = config.getBoolean("EntityManager.Metrics");
		logging = config.getBoolean("EntityManager.Logging");
		kicking = config.getBoolean("EntityManager.TooManyDrops");
		/* Stop */
		worlds = config.getStringList("World.Worldname");
		/* Stop */
		totalexp = config.getBoolean("Disable.Experience");
		totalenchant = config.getBoolean("Disable.Enchanting");
		/* Stop */
		doorBreak = config.getBoolean("block.Actions.zombie-door-break");
		enderPickup = config.getBoolean("block.Actions.ender-pickup");
		pvp = config.getBoolean("block.Actions.pvp");
		mobdmg = config.getBoolean("block.Actions.mob-damage");
		fishing = config.getBoolean("block.Actions.fishing");
		arrowz = config.getBoolean("block.Actions.arrows");
		/* Stop */
		MonsEggs = config.getBoolean("block.Dispense.SpawnerEggs");
		ChickEggs = config.getBoolean("block.Dispense.ChickenEggs");
		Fballs = config.getBoolean("block.Dispense.Fireballs");
		xBottz = config.getBoolean("block.Dispense.XpBottles");
		potionz = config.getBoolean("block.Dispense.Potions");
		/* Stop */
		items = config.getIntegerList("block.Dispense.Items");
		/* Stop */
		xpbott = config.getBoolean("block.Throw.XpBottles");
		fire = config.getBoolean("block.Throw.FireCharges");
		egg = config.getBoolean("block.Throw.ChickenEggs");
		pearl = config.getBoolean("block.Throw.EnderPearls");
		eye = config.getBoolean("block.Throw.EnderEye");
		potion = config.getBoolean("block.Throw.Potions");
		/* Stop */
		boatz = config.getBoolean("block.Entities.Boats");
		cartz = config.getBoolean("block.Entities.Minecarts");
		paintz = config.getBoolean("block.Entities.Paintings");
		/* Stop */
		cDrop = config.getBoolean("block.ItemDrop.Creative-mode");
		sDrop = config.getBoolean("block.ItemDrop.Survival-mode");
		onDeath = config.getBoolean("block.ItemDrop.On-Death");
		bDestroy = config.getBoolean("block.ItemDrop.Block-destroy");
		bitems = config.getIntegerList("block.ItemDrop.Blacklisted-items");
		/* Stop */
		portals = config.getBoolean("block.Creation-of.portals");
		/* Stop */
		mons = config.getInt("Economy.charge.monster");
		animal = config.getInt("Economy.charge.animal");
		npc = config.getInt("Economy.charge.npc");
		/* Stop */
		creep = config.getBoolean("disabled.eggs.creeper");
		skele = config.getBoolean("disabled.eggs.skeleton");
		spider = config.getBoolean("disabled.eggs.spider");
		zombie = config.getBoolean("disabled.eggs.zombie");
		slime  = config.getBoolean("disabled.eggs.slime");
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
		/* Stop */
		creeps = config.getBoolean("disabled.mobs.creeper");
		skeles = config.getBoolean("disabled.mobs.skeleton");
		spiders = config.getBoolean("disabled.mobs.spider");
		zombies = config.getBoolean("disabled.mobs.zombie");
		slimes  = config.getBoolean("disabled.mobs.slime");
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
	}
}