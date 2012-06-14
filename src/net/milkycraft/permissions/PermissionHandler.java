package net.milkycraft.permissions;

import net.milkbowl.vault.permission.Permission;
import net.milkycraft.EntityManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

// TODO: Auto-generated Javadoc
/**
 * Class to handle permission node checks. Mostly only to support PEX natively,
 * due to SuperPerm compatibility with PEX issues.
 * 
 * @author Mitsugaru
 * 
 */
public class PermissionHandler {

	/** The perm. */
	private static Permission perm;

	/** The has vault. */
	private static boolean hasVault;

	/**
	 * Inits the.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public static void init(EntityManager plugin) {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
			hasVault = true;
			RegisteredServiceProvider<Permission> permissionProvider = plugin
					.getServer().getServicesManager()
					.getRegistration(Permission.class);
			if (permissionProvider != null) {
				perm = permissionProvider.getProvider();
			}
		} else {
			hasVault = false;
		}
	}

	/**
	 * Checks for.
	 * 
	 * @param sender
	 *            the sender
	 * @param node
	 *            the node
	 * @return true, if successful
	 */
	public static boolean has(CommandSender sender, PermissionNode node) {
		return has(sender, node.getNode());
	}
	
	public static boolean has(Player player, PermissionNode node) {
		return has(player, node.getNode());
	}

	/**
	 * Checks for.
	 * 
	 * @param sender
	 *            the sender
	 * @param node
	 *            the node
	 * @return true if sender has the node, else false
	 */
	public static boolean has(CommandSender sender, String node) {
		// Use vault if we have it
		if (hasVault && perm != null) {
			return perm.has(sender, node);
		}
		// Attempt to use SuperPerms or op
		if (sender.isOp() || sender.hasPermission(node)) {
			return true;
		}
		// Else, they don't have permission
		return false;
	}
}
