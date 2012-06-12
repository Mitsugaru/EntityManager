package net.milkycraft.permissions;

public enum PermissionNode
{
	ALL(".*"), ADMIN(".admin"), CRYSTAL(".crystal"), PLACE_ENDERCHEST(
			".place.enderchest"), PLACE_TRIPWIRE(".place.tripwire"), PLACE_DRAGONEGG(
			".place.dragonegg"), ALLOW_ENCHANTING(".allow.enchanting"), NOFALL(
			".nofall"), MOB_DAMAGE(".mob-damage"), ALLOW_PVP(".allow.pvp"), NOHUNGER(
			".nohunger"), ALLOW_PAINTINGS(".allow.paintings"), ALLOW_FISHING(
			".allow.fishing"), ALLOW_ARROWS(".allow.arrows"), BYPASS_CHARGE(
			".bypass.charge"), AVOID_TARGET(".avoid.target"), THROW_ALL(
			".throw.*"), THROW_XPBOTTLES(".throw.xpbottles"), THROW_FIRECHARGES(
			".throw.firecharges"), THROW_CHICKENEGGS(".throw.chickeneggs"), THROW_ENDERPEARLS(
			".throw.enderpearls"), THROW_ENDEREYES(".throw.endereyes"), THROW_BOATS(
			".throw.boats"), THROW_MINECARTS(".throw.minecarts"), THROW_POTIONS(
			".throw.potions"), CREATIVE_DROP(".creative.drop"), SURVIVAL_DROP(
			".survival.drop"), BYPASS_BLACKLIST(".bypass.blacklist"), FISHING(
			".fishing"), PAINTING(".painting"), ENCHANTING(".enchanting"), PVP(
			".pvp"), BOATS(".boats"), MINECARTS(".minecarts");

	private static final String prefix = "entitymanager";
	private String node;

	private PermissionNode(String node)
	{
		this.node = prefix + node;
	}

	public String getNode()
	{
		return node;
	}
}
