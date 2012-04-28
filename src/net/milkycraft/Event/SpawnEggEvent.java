package net.milkycraft.Event;


import net.milkycraft.Enums.EntityCategory;
import net.milkycraft.Enums.EntityType;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnEggEvent {
	private Player player = null;
	private PlayerInteractEvent event = null;
	private int eggMeta;
	private EntityType breed = null;

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

	public Player getPlayer() {
		return player;
	}

	public PlayerInteractEvent getInteractEvent() {
		return event;
	}

	public int getEntityId() {
		return eggMeta;
	}

	public EntityType getEntityBreed() {
		if (breed != null) {
			return breed;
		} else {
			return EntityType.UNKNOWN;
		}
	}

	int Id = 0;
	EntityCategory cat = EntityCategory.UNKNOWN;

	SpawnEggEvent(int eId, EntityCategory cat) {
		Id = eId;
		this.cat = cat;
	}

	public EntityCategory getCategory() {
		return this.cat;
	}

	public int getId() {
		return Id;
	}
}
