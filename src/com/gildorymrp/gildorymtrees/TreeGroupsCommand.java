package com.gildorymrp.gildorymtrees;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TreeGroupsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		sender.sendMessage(ChatColor.GREEN + "Gildorym Tree Generator Info");
		String treeGroups = ChatColor.GREEN + "Tree Groups: ";
		for (TreeGroup group : TreeGroup.values()) {
			treeGroups += group.toString() + " ";
		}
		sender.sendMessage(ChatColor.GREEN + treeGroups);
		String treeRarities = ChatColor.GREEN + "Tree Rarities: ";
		for (TreeRarity rarity : TreeRarity.values()) {
			treeRarities += rarity.toString() + " ";
		}
		sender.sendMessage(ChatColor.GREEN + treeRarities);
		return true;
	}

}
