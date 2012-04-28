package net.milkycraft.Enums;

public enum EntityType {

	CREEPER(50, EntityCategory.MONSTER), SKELETON(51, EntityCategory.MONSTER), SPIDER(
			52, EntityCategory.MONSTER), ZOMBIE(54, EntityCategory.MONSTER), SLIME(
			55, EntityCategory.MONSTER), GHAST(56, EntityCategory.MONSTER), PIGMAN(
			57, EntityCategory.MONSTER), ENDERMAN(58, EntityCategory.MONSTER), CAVESPIDER(
			59, EntityCategory.MONSTER), SILVERFISH(60, EntityCategory.MONSTER), BLAZE(
			61, EntityCategory.MONSTER), MAGMACUBE(62, EntityCategory.MONSTER), PIG(
			90, EntityCategory.ANIMAL), SHEEP(91, EntityCategory.ANIMAL), COW(
			92, EntityCategory.ANIMAL), CHICKEN(93, EntityCategory.ANIMAL), SQUID(
			94, EntityCategory.ANIMAL), WOLF(95, EntityCategory.ANIMAL), MOOSHROOM(
			96, EntityCategory.ANIMAL), OCELOT(98, EntityCategory.ANIMAL), VILLAGER(
			120, EntityCategory.NPC), UNKNOWN(0, EntityCategory.UNKNOWN), ENDERCRYSTAL(
			120, EntityCategory.SPECIAL);

	int Id = 0;
	EntityCategory cat = EntityCategory.UNKNOWN;

	EntityType(int eId, EntityCategory cat) {
		Id = eId;
		this.cat = cat;
	}

	public EntityCategory getCategory() {
		return this.cat;
	}

	public int getId() {
		return Id;
	}

	public String getName() {
		return name();
	}
}