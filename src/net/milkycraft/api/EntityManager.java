package net.milkycraft.api;

import net.milkycraft.ASEConfiguration.Settings;
import net.milkycraft.Enums.EntityType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EntityManager {
	private static volatile EntityManager instance;

	/**
	 * Get if that player can use fishing poles
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
	 * Get if that player can use paintings
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
	 * Get if that player can drop items in creative mode
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canCreativeDrop(Player player) {
		boolean result = player.hasPermission("entitymanager.creative.drop");
		if (Settings.cDrop) {
			return result;
		}
		return true;
	}

	/**
	 * Get if that player can drop items in survival mode
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
	 * Gets if metrics is enabled for the plugin
	 * 
	 * @return true for yes, false for no
	 */
	public boolean isMetricsEnabled() {
		return Settings.metrics;
	}

	/**
	 * Get if that entity can drop experience
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
	 * permission
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
	 * Get if that player can damage other players
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
	 * Get if that player can use a boat
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
	 * Get if that player can bypass the item blacklist
	 * 
	 * @param player
	 *            The target player
	 * @return true for yes, false for no
	 */
	public boolean canBypassBlacklist(Player player) {
		return player.hasPermission("entitymanager.bypass.blacklist");
	}

	/**
	 * Get if that player can use a minecart
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
	 * Get the state of logging within entitymanager
	 * 
	 * @return if logging is enabled
	 */
	public boolean isLogging() {
		return Settings.logging;
	}

	/**
	 * Get a list of worlds that the plugin is enabled in
	 * 
	 * @return list of applicable worlds
	 */
	public java.util.List<String> getWorlds() {
		return Settings.worlds;
	}

	/**
	 * Get a list of items blocked from dispensers
	 * 
	 * @return a list of items blocked from dispensers
	 */
	public java.util.List<Integer> getBlacklistedDispenserDrops() {
		return Settings.items;
	}

	/**
	 * Get a list of items blocked from player inventories
	 * 
	 * @return a list of items blocked from player inventories
	 */
	public java.util.List<Integer> getBlacklistedItemDrops() {
		return Settings.bitems;
	}

	/**
	 * Get monster egg price per use
	 * 
	 * @return price per use of monster eggs
	 */
	public int monsterEggCharge() {
		return Settings.mons;
	}

	/**
	 * Get animal egg price per use
	 * 
	 */
	public int animalEggCharge() {
		return Settings.animal;
	}

	/**
	 * Get npc egg price per use
	 * 
	 * @return price per use of npc eggs
	 */
	public int npcEggCharge() {
		return Settings.npc;
	}

	/**
	 * Get if player can throw a throwable
	 * 
	 * @author milkywayz
	 * @param player
	 *            The player your trying to see if they can throw
	 * @param material
	 *            EXP_BOTTLE, FIREBALL, EGG, ENDER_PEARL, ENDER_EYE, POTION
	 * @return true if they can, false if they cant
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
	 * Get if that entity can spawn based on config settings
	 * TODO: Find a way to canSpawn() without the param plugin for ease of use
	 * @param mob
	 *            The EntityType of the mob
	 * @param plugin
	 *            Reference the mainclass of EntityMangager
	 * @return if the mob is physically blocked from spawning
	 * @deprecated May be hard to reference plugin, therefore deprecated until
	 *             better solution is found
	 */
	@Deprecated
	public boolean canSpawn(EntityType mob, Plugin plugin) {
		return !plugin.getConfig().getBoolean(
				"disabled.mobs." + mob.toString().toLowerCase());
	}
	/**
	 * Get if player is a entitymanager "admin"
	 * 
	 * @param player
	 *            The player being used to see if is a EntityManager admin
	 * @return if player has permission
	 */
	public boolean isEntityManagerAdmin(Player player) {
		return player.hasPermission("entitymanager.admin");
	}

	/**
	 * Get if player has a permission dedicated to entitymanager Typical
	 * permission: entitymanager.fishing or entitymanager.throw.xpbottles
	 * 
	 * @param player
	 *            The player
	 * @param permission
	 *            The second half of a permission node (.admin, .throw.xpbottle,
	 *            etc)
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
	 */
	public static EntityManager getManager() {
		if (instance == null) {
			instance = new EntityManager();
		}
		return instance;
	}
}
