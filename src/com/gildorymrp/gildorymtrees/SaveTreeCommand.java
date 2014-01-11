package com.gildorymrp.gildorymtrees;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveTreeCommand implements CommandExecutor {

	private GildorymTreeGenerator plugin;
	
	public SaveTreeCommand(GildorymTreeGenerator plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only a player can perform this command!");
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "You must specify the Group and Rarity!");
			sender.sendMessage(ChatColor.RED + "Ensure your command takes the form:");
			sender.sendMessage(ChatColor.RED + "/savetree Oak Common");
			return true;
		} else if (args.length > 2) {
			sender.sendMessage(ChatColor.RED + "Too many arguments!");
			sender.sendMessage(ChatColor.RED + "Ensure your command takes the form:");
			sender.sendMessage(ChatColor.RED + "/savetree Oak Common");
			return true;
		} else {
			TreeGroup treeGroup = null;
			TreeRarity treeRarity = null;
			
			for (TreeGroup group : TreeGroup.values()) {
				if (args[0].equalsIgnoreCase(group.toString())) {
					treeGroup = group;
				}
			}
			if (treeGroup == null) {
				sender.sendMessage(ChatColor.RED + "Invalid group name!");
				String validGroupNames = ChatColor.RED + "Valid group names: ";
				for (TreeGroup group : TreeGroup.values()) {
					validGroupNames += group.toString() + " ";
				}
				sender.sendMessage(validGroupNames);
				return true;
			}
			
			for (TreeRarity rarity : TreeRarity.values()) {
				if (args[1].equalsIgnoreCase(rarity.toString())) {
					treeRarity = rarity;
				}
			}
			if (treeRarity == null) {
				sender.sendMessage(ChatColor.RED + "Invalid rarity!");
				String validRarities = ChatColor.RED + "Valid rarities: ";
				for (TreeRarity rarity : TreeRarity.values()) {
					validRarities += rarity.toString() + " ";
				}
				sender.sendMessage(validRarities);
				return true;
			}
			
			TreeData treeData = new TreeData(treeGroup, treeRarity);
			if (plugin.commandTreeDataMap.containsKey(sender.getName())) {
				plugin.commandTreeDataMap.remove(sender.getName());
			}
			if (plugin.commandBlockMap.containsKey(sender.getName())) {
				plugin.commandBlockMap.remove(sender.getName());
			}
			plugin.commandTreeDataMap.put(sender.getName(), treeData);
			plugin.commandBlockMap.put(sender.getName(), new Block[3]);
			sender.sendMessage(ChatColor.GREEN + "Now saving data for a " + treeRarity.toString() + " " + treeGroup.toString() + " tree.");
			sender.sendMessage(ChatColor.GREEN + "Right-click the first corner block of the boundary with an empty hand.");
			sender.sendMessage(ChatColor.GREEN + "Note: The height of the corner blocks doesn't matter, only horizonal position.");
			sender.sendMessage(ChatColor.GREEN + "Note: Any free leaves within the area will get copied with the tree!");
			return true;
		}
	}

}
