package net.milkycraft.api;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.configuration.WorldSettings;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * The Class EntityManagerAPI.
 */
public class EntityManagerAPI extends EntityManager {
	/** The Constant instance. */
	private final static EntityManagerAPI instance = new EntityManagerAPI();

	/**
	 * Get if that player can use fishing poles.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canFish(Player player) {
		if (Settings.fishing) {
			return player.hasPermission("entitymanager.fishing");
		} else {
			return true;
		}
	}

	/**
	 * Get if that player can use paintings.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canPaint(Player player) {
		if (Settings.paintz) {
			return player.hasPermission("entitymanager.painting");
		} else {
			return true;
		}
	}

	/**
	 * Get if that player can drop items in creative mode.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canCreativeDrop(Player player) {
		if (Settings.cDrop) {
			return player.hasPermission("entitymanager.creative.drop");
		} else {
			return true;
		}
	}

	/**
	 * See if a player has permission to hurt mobs when its blocked in config.
	 * 
	 * @param player
	 *            the player
	 * @return If a player can hurt a mob when mobs cant take damage from
	 *         entities
	 */
	public boolean canHurtGodMobs(Player player) {
		if (Settings.mobdmg) {
			return player.hasPermission("entitymanager.mob-damage");
		} else {
			return true;
		}
	}

	/**
	 * Check if mobs dont recieve damage from players.
	 * 
	 * @return true for yes, false for no
	 */
	public boolean areMobsGod() {
		return Settings.mobdmg;
	}

	/**
	 * Get if that player can drop items in survival mode.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canSurvivalDrop(Player player) {
		if (Settings.sDrop) {
			return player.hasPermission("entitymanager.survival.drop");
		} else {
			return true;
		}
	}

	/**
	 * Gets if metrics is enabled for the plugin.
	 * 
	 * @return true for yes, false for no
	 */
	public boolean isMetricsEnabled() {
		return Settings.metrics;
	}

	/**
	 * Get if that entity can drop experience.
	 * 
	 * @param entity
	 *            Any entity, can be used on Listeners very well :P
	 * @return true if experience isnt blocking in config
	 */
	public boolean canDropXp(Entity entity) {
		return !Settings.totalexp;
	}

	/**
	 * Get if that player can use enchanting tables Based on: Config and
	 * permission.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canEnchant(Player player) {
		if (Settings.totalenchant) {
			return player.hasPermission("entitymanager.enchanting");
		} else {
			return true;
		}
	}

	/**
	 * Get if that player can damage other players.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canPVP(Player player) {
		if (Settings.pvp) {
			return player.hasPermission("entitymanager.pvp");
		} else {
			return true;
		}
	}

	/**
	 * Get if that player can use a boat.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canUseBoats(Player player) {
		if (Settings.boatz) {
			return player.hasPermission("entitymanager.boats");
		} else {
			return true;
		}
	}

	/**
	 * Get if that player can bypass the item blacklist.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canBypassBlacklist(Player player) {
		return player.hasPermission("entitymanager.bypass.blacklist");
	}

	/**
	 * Get if that player can use a minecart.
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canUseMinecarts(Player player) {
		if (Settings.cartz) {
			return player.hasPermission("entitymanager.minecarts");
		} else {
			return true;
		}
	}

	/**
	 * Get the state of logging within entitymanager.
	 * 
	 * @return if logging is enabled
	 */
	public boolean isLogging() {
		return Settings.logging;
	}

	/**
	 * Get a list of worlds that the plugin is enabled in.
	 * 
	 * @return list of applicable worlds
	 */
	public java.util.List<String> getWorlds() {
		return WorldSettings.worlds;
	}

	/**
	 * Get a list of items blocked from dispensers.
	 * 
	 * @return a list of items blocked from dispensers
	 */
	public java.util.List<Integer> getBlacklistedDispenserItems() {
		return Settings.items;
	}

	/**
	 * Get a list of items blocked from player inventories.
	 * 
	 * @return a list of items blocked from player inventories
	 */
	public java.util.List<Integer> getBlacklistedItemDrops() {
		return Settings.bitems;
	}

	/**
	 * Get monster egg price per use.
	 * 
	 * @return price per use of monster eggs
	 */
	public int monsterEggCharge() {
		return Settings.mons;
	}

	/**
	 * Get animal egg price per use.
	 * 
	 * @return the int
	 */
	public int animalEggCharge() {
		return Settings.animal;
	}

	/**
	 * Get npc egg price per use.
	 * 
	 * @return price per use of npc eggs
	 */
	public int npcEggCharge() {
		return Settings.npc;
	}

	/**
	 * Get if player can throw a throwable.
	 * 
	 * @param player
	 *            The player your trying to see if they can throw
	 * @param material
	 *            EXP_BOTTLE, FIREBALL, EGG, ENDER_PEARL, ENDER_EYE, POTION
	 * @return true if they can, false if they cant
	 * @author milkywayz
	 */
	public boolean canThrow(Player player, Material material) {
		if (material == Material.EXP_BOTTLE) {
			if (Settings.xpbott) {
				return player.hasPermission("entitymanager.throw.xpbottles");
			} else {
				return true;
			}
		} else if (material == Material.FIREBALL) {
			if (Settings.fire) {
				return player.hasPermission("entitymanager.throw.firecharges");
			} else {
				return true;
			}
		} else if (material == Material.EGG) {
			if (Settings.egg) {
				return player.hasPermission("entitymanager.throw.chickeneggs");
			} else {
				return true;
			}
		} else if (material == Material.ENDER_PEARL) {
			if (Settings.pearl) {
				return player.hasPermission("entitymanager.throw.enderpearls");
			} else {
				return true;
			}
		} else if (material == Material.EYE_OF_ENDER) {
			if (Settings.eye) {
				return player.hasPermission("entitymanager.throw.endereyes");
			} else {
				return true;
			}
		} else if (material == Material.POTION) {
			if (Settings.potion) {
				return player.hasPermission("entitymanager.throw.potions");
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Get if that entity can spawn based on config settings.
	 * 
	 * @param mob
	 *            The EntityType of the mob
	 * @return if the mob is physically blocked from spawning
	 */
	public boolean canSpawn(EntityType mob) {
		return !Settings.getConfig().getBoolean(
				"disabled.mobs." + mob.name().toLowerCase());
	}

	/**
	 * Checks if is sending alerts.
	 * 
	 * @return true, if is sending alerts
	 */
	public boolean isSendingAlerts() {
		return Settings.alertz;
	}

	/**
	 * Checks if is motd enabled.
	 * 
	 * @return true, if is motd enabled
	 */
	public boolean isMotdEnabled() {
		return Settings.Motd;
	}

	/**
	 * Get if player is a entitymanager "admin".
	 * 
	 * @param player
	 *            The player being used to see if is a EntityManager admin
	 * @return if player has permission
	 */
	public boolean isEntityManagerAdmin(Player player) {
		return player.hasPermission("entitymanager.admin");
	}

	/**
	 * See if player can spawn a mob with an egg.
	 * 
	 * @param player
	 *            the player
	 * @param mob
	 *            Name of mob use net.milkycraft.Enums.EntityType for reference
	 * @return True if player has permission or if egg is just not blocked
	 */
	public boolean canPlayerSpawn(Player player, EntityType mob) {
		if (Settings.getConfig().getBoolean(
				"disabled.eggs." + mob.name().toLowerCase())) {
			return player.hasPermission("entitymanager."
					+ mob.name().toLowerCase());
		} else {
			return true;
		}
	}

	/**
	 * Get if player has a permission dedicated to entitymanager Typical
	 * permission: entitymanager.fishing or entitymanager.throw.xpbottles
	 * @author milkywayz
	 * @throws IllgalArgumentException if permission is null
	 * @since 3.4
	 * @param player
	 *            The player
	 * @param permission
	 *            The second half of a permission node (eg: .throw.xpbottle)
	 * @return wether player has permission or not for the specified permission
	 */
	public boolean hasEmPerms(Player player, String permission) {
		if (permission == null) {
			throw new IllegalArgumentException("Permission cant be null!");
		}
		return player.hasPermission("entitymanager." + permission);
	}
	

	/**
	 * Get this class so you can use the public values Proper use of API:
	 * EntityManager.getManager().canSpawn() - For example...
	 * 
	 * @return The EntityManager Official API
	 * @author milkywayz
	 * @category API
	 * @since 3.4
	 * @see EntityManagerAPI
	 */
	public static EntityManagerAPI getManager() {
		return instance;
	}
}
