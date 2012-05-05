/*
 * 
 */
package net.milkycraft.Enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum EntityType.
 */
public enum EntityType {

	/** The CREEPER. */
	CREEPER(50, EntityCategory.MONSTER), /** The SKELETON. */
	SKELETON(51, EntityCategory.MONSTER), /** The SPIDER. */
	SPIDER(52, EntityCategory.MONSTER),
	/** The ZOMBIE. */
	ZOMBIE(54, EntityCategory.MONSTER),
	/** The SLIME. */
	SLIME(55, EntityCategory.MONSTER),
	/** The GHAST. */
	GHAST(56, EntityCategory.MONSTER),
	/** The PIGMAN. */
	PIGMAN(57, EntityCategory.MONSTER),
	/** The ENDERMAN. */
	ENDERMAN(58, EntityCategory.MONSTER),
	/** The CAVESPIDER. */
	CAVESPIDER(59, EntityCategory.MONSTER),
	/** The SILVERFISH. */
	SILVERFISH(60, EntityCategory.MONSTER),
	/** The BLAZE. */
	BLAZE(61, EntityCategory.MONSTER),
	/** The MAGMACUBE. */
	MAGMACUBE(62, EntityCategory.MONSTER),
	/** The PIG. */
	PIG(90, EntityCategory.ANIMAL),
	/** The SHEEP. */
	SHEEP(91, EntityCategory.ANIMAL),
	/** The COW. */
	COW(92, EntityCategory.ANIMAL),
	/** The CHICKEN. */
	CHICKEN(93, EntityCategory.ANIMAL),
	/** The SQUID. */
	SQUID(94, EntityCategory.ANIMAL),
	/** The WOLF. */
	WOLF(95, EntityCategory.ANIMAL),
	/** The MOOSHROOM. */
	MOOSHROOM(96, EntityCategory.ANIMAL),
	/** The OCELOT. */
	OCELOT(98, EntityCategory.ANIMAL),
	/** The VILLAGER. */
	VILLAGER(120, EntityCategory.NPC),
	/** The UNKNOWN. */
	UNKNOWN(0, EntityCategory.UNKNOWN),
	/** The ENDERCRYSTAL. */
	ENDERCRYSTAL(200, EntityCategory.SPECIAL);

	/** The Id. */
	int Id = 0;

	/** The cat. */
	EntityCategory cat = EntityCategory.UNKNOWN;

	/**
	 * Instantiates a new entity type.
	 * 
	 * @param eId
	 *            the e id
	 * @param cat
	 *            the cat
	 */
	EntityType(int eId, EntityCategory cat) {
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

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return Id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name();
	}
}