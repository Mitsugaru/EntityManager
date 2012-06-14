package net.milkycraft.permissions;

// TODO: Auto-generated Javadoc
/**
 * The Enum PermissionNode.
 */
public enum PermissionNode {

	/** The ALL. */
	ALL(".*"),
	/** The ADMIN. */
	ADMIN(".admin"),
	/** The CRYSTAL. */
	CRYSTAL(".crystal"),
	/** The PLAC e_ enderchest. */
	PLACE_ENDERCHEST(".place.enderchest"),
	/** The PLAC e_ tripwire. */
	PLACE_TRIPWIRE(".place.tripwire"),
	/** The PLAC e_ dragonegg. */
	PLACE_DRAGONEGG(".place.dragonegg"),
	/** The ALLO w_ enchanting. */
	ALLOW_ENCHANTING(".allow.enchanting"),
	/** The NOFALL. */
	NOFALL(".nofall"),
	/** The MO b_ damage. */
	MOB_DAMAGE(".mob-damage"),
	/** The ALLO w_ pvp. */
	ALLOW_PVP(".allow.pvp"),
	/** The NOHUNGER. */
	NOHUNGER(".nohunger"),
	/** The ALLO w_ paintings. */
	ALLOW_PAINTINGS(".allow.paintings"),
	/** The ALLO w_ fishing. */
	ALLOW_FISHING(".allow.fishing"),
	/** The ALLO w_ arrows. */
	ALLOW_ARROWS(".allow.arrows"),
	/** The BYPAS s_ charge. */
	BYPASS_CHARGE(".bypass.charge"),
	/** The AVOI d_ target. */
	AVOID_TARGET(".avoid.target"),
	/** The THRO w_ all. */
	THROW_ALL(".throw.*"),
	/** The THRO w_ xpbottles. */
	THROW_XPBOTTLES(".throw.xpbottles"),
	/** The THRO w_ firecharges. */
	THROW_FIRECHARGES(".throw.firecharges"),
	/** The THRO w_ chickeneggs. */
	THROW_CHICKENEGGS(".throw.chickeneggs"),
	/** The THRO w_ enderpearls. */
	THROW_ENDERPEARLS(".throw.enderpearls"),
	/** The THRO w_ endereyes. */
	THROW_ENDEREYES(".throw.endereyes"),
	/** The THRO w_ boats. */
	THROW_BOATS(".throw.boats"),
	/** The THRO w_ minecarts. */
	THROW_MINECARTS(".throw.minecarts"),
	/** The THRO w_ potions. */
	THROW_POTIONS(".throw.potions"),
	/** The CREATIV e_ drop. */
	CREATIVE_DROP(".creative.drop"),
	/** The SURVIVA l_ drop. */
	SURVIVAL_DROP(".survival.drop"),
	/** The BYPAS s_ blacklist. */
	BYPASS_BLACKLIST(".bypass.blacklist"),
	/** The FISHING. */
	FISHING(".fishing"),
	/** The PAINTING. */
	PAINTING(".painting"),
	/** The ENCHANTING. */
	ENCHANTING(".enchanting"),
	/** The PVP. */
	PVP(".pvp"),
	/** The BOATS. */
	BOATS(".boats"),
	/** The MINECARTS. */
	MINECARTS(".minecarts"),
	/** The SE t_ durability. */
	SET_DURABILITY(".set.durability"),
	/** The SPAW n_ minecart. */
	SPAWN_MINECART(".spawn.minecart"),
	/** The SPAW n_ boat. */
	SPAWN_BOAT(".spawn.boat"),
	/** The SPAW n_ snowgolem. */
	SPAWN_SNOWGOLEM(".spawn.snowgolem"),
	/** The SPAW n_ irongolem. */
	SPAWN_IRONGOLEM(".spawn.irongolem"),
	/** The SPAW n_ crystal. */
	SPAWN_CRYSTAL(".spawn.crystal");

	/** The Constant prefix. */
	private static final String prefix = "entitymanager";

	/** The node. */
	private String node;

	/**
	 * Instantiates a new permission node.
	 * 
	 * @param node
	 *            the node
	 */
	private PermissionNode(String node) {
		this.node = prefix + node;
	}

	/**
	 * Gets the node.
	 * 
	 * @return the node
	 */
	public String getNode() {
		return node;
	}
}
