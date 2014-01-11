package com.gildorymrp.gildorymtrees;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Tree;

public class PlayerInteractListener implements Listener {

	private GildorymTreeGenerator plugin;

	public PlayerInteractListener(GildorymTreeGenerator plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!plugin.commandTreeDataMap.containsKey(event.getPlayer().getName())) {
			return;
		} else if (event.getItem().getType() != Material.AIR) {
			return;
		} else if (event.getClickedBlock().getType() != Material.AIR) {
			Player player = event.getPlayer();
			Block[] blocks;
			if (plugin.commandBlockMap.containsKey(player.getName())) {
				blocks = plugin.commandBlockMap.get(player.getName());
			} else {
				player.sendMessage(ChatColor.RED + "An unexpected error has occured!");
				return;
			}
			if (blocks[0] == null) {
				blocks[0] = event.getClickedBlock();
				player.sendMessage(ChatColor.GREEN + "Right-click the second corner block of the boundary with an empty hand.");
				player.sendMessage(ChatColor.GREEN + "Note: The height of the corner blocks doesn't matter, only horizonal position.");
				player.sendMessage(ChatColor.GREEN + "Note: Any free leaves within the area will get copied with the tree!");
			} else if (blocks[1] == null) {
				blocks[1] = event.getClickedBlock();
				player.sendMessage(ChatColor.GREEN + "Right-click the base block of the tree with an empty hand.");
				player.sendMessage(ChatColor.GREEN + "Choose the block that would be occupied by a sapling before tree growth.");
			} else if (blocks[2] == null) {
				if (event.getClickedBlock().getType() == Material.LOG) {
					blocks[2] = event.getClickedBlock();
					player.sendMessage(ChatColor.GREEN + "All blocks selected!");
					player.sendMessage(ChatColor.GREEN + "Processing tree...");
					Set<GildorymTreeBlock> blockSet = GildorymTree.scanTree(blocks, event.getClickedBlock().getWorld());
					if (blockSet.isEmpty()) {
						player.sendMessage(ChatColor.RED + "No tree found!");
					} else {
						TreeData treeData = plugin.commandTreeDataMap.get(player.getName());
						TreeSpecies treeSpecies = ((Tree) blocks[2].getState().getData()).getSpecies();
						int height = GildorymTree.getHeight(blockSet);
						GildorymTree tree = new GildorymTree(treeData.treeGroup, treeData.treeRarity, treeSpecies, blockSet, height);
						String fileName = TreeDataManager.saveTree(plugin, tree);
						if (fileName == null) {
							player.sendMessage(ChatColor.RED + "Error! Tree save failed! See console for details.");
						} else { 
							player.sendMessage(ChatColor.GREEN + "Success! Tree saved as " + fileName);
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "The base block of the tree must be a log!");
				}
			}
		}
	}
	
}
