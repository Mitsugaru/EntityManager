/*
 * SpawnEggEvent was based on the work of GooseMonkey97
 * https://github.com/GooseMonkey/NoSpawnEggsv1/blob/master/src/com/goosemonkey/NoSpawnEggs/listeners/PlayerEggThrowListener.java
 * Was modified for use in EntityManager, all credit where credit is due.
 */
package net.milkycraft.events;

import net.milkycraft.enums.EntityCategory;
import net.milkycraft.enums.EntityType;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SpawnEggEvent.
 */
public class SpawnEggEvent {

	/** The player. */
	private Player player = null;

	/** The event. */
	private PlayerInteractEvent event = null;

	/** The egg meta. */
	private int eggMeta;

	/** The breed. */
	private EntityType breed = null;

	/**
	 * Instantiates a new spawn egg event.
	 * 
	 * @param event
	 *            the event
	 */
	public SpawnEggEvent(PlayerInteractEvent event) {
		this.player = event.getPlayer();
		this.event = event;

		this.eggMeta = event.getPlayer().getItemInHand().getDurability();

		switch (eggMeta) {
		case 50:
			breed = EntityType.CREEPER;
			break;
		case 51:
			breed = EntityType.SKELETON;
			break;
		case 52:
			breed = EntityType.SPIDER;
			break;
		case 54:
			breed = EntityType.ZOMBIE;
			break;
		case 55:
			breed = EntityType.SLIME;
			break;
		case 56:
			breed = EntityType.GHAST;
			break;
		case 57:
			breed = EntityType.PIGMAN;
			break;
		case 58:
			breed = EntityType.ENDERMAN;
			break;
		case 59:
			breed = EntityType.CAVESPIDER;
			break;
		case 60:
			breed = EntityType.SILVERFISH;
			break;
		case 61:
			breed = EntityType.BLAZE;
			break;
		case 62:
			breed = EntityType.MAGMACUBE;
			break;
		case 90:
			breed = EntityType.PIG;
			break;
		case 91:
			breed = EntityType.SHEEP;
			break;
		case 92:
			breed = EntityType.COW;
			break;
		case 93:
			breed = EntityType.CHICKEN;
			break;
		case 94:
			breed = EntityType.SQUID;
			break;
		case 95:
			breed = EntityType.WOLF;
			break;
		case 96:
			breed = EntityType.MOOSHROOM;
			break;
		case 98:
			breed = EntityType.OCELOT;
			break;
		case 120:
			breed = EntityType.VILLAGER;
			break;
		case 200:
			breed = EntityType.ENDERCRYSTAL;
			break;

		default:
			breed = null;
		}
	}

	/**
	 * Gets the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the interact event.
	 * 
	 * @return the interact event
	 */
	public PlayerInteractEvent getInteractEvent() {
		return event;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public int getEntityId() {
		return eggMeta;
	}

	/**
	 * Gets the entity breed.
	 * 
	 * @return the entity breed
	 */
	public EntityType getEntityBreed() {
		if (breed != null) {
			return breed;
		} else {
			return EntityType.UNKNOWN;
		}
	}

	/** The Id. */
	int Id = 0;

	/** The cat. */
	EntityCategory cat = EntityCategory.UNKNOWN;

	/**
	 * Instantiates a new spawn egg event.
	 * 
	 * @param eId
	 *            the e id
	 * @param cat
	 *            the cat
	 */
	SpawnEggEvent(int eId, EntityCategory cat) {
		Id = eId;
		this.cat = cat;
	}

	/**
	 * Gets the category.
	 * 
	 * @return the category
	 */
	public EntityCategory getCategory() {
		return this.cat;
	}

}
