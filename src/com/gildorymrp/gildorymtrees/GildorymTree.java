package com.gildorymrp.gildorymtrees;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;

public class GildorymTree {

	static GildorymTree reflectTree(GildorymTree tree, boolean flipZInsteadOfX) {
		Set<GildorymTreeBlock> oldBlockSet = tree.getBlockSet();
			Set<GildorymTreeBlock> newBlockSet = new HashSet<GildorymTreeBlock>();
			for (GildorymTreeBlock treeBlock : oldBlockSet) { 
				if (!flipZInsteadOfX) {
					newBlockSet.add(new GildorymTreeBlock(-1 * treeBlock.getXOffset(),
						treeBlock.getYOffset(), treeBlock.getZOffset(),
						treeBlock.getBlockType()));
				} else {
					newBlockSet.add(new GildorymTreeBlock(treeBlock.getXOffset(),
							treeBlock.getYOffset(), -1 * treeBlock.getZOffset(),
							treeBlock.getBlockType()));
				}
			}
			GildorymTree newTree = new GildorymTree(tree);
			newTree.setBlockSet(newBlockSet);
			return newTree;
	}
	
	static GildorymTree rotateTree(GildorymTree tree, int turns) {
		Set<GildorymTreeBlock> oldBlockSet = tree.getBlockSet();
		if (turns >= 0 && turns <= 3) {
			Set<GildorymTreeBlock> newBlockSet = new HashSet<GildorymTreeBlock>();
			double angle = Math.toRadians(90 * turns);
			for (GildorymTreeBlock treeBlock : oldBlockSet) { 
				int old_z = treeBlock.getZOffset();
				int old_x = treeBlock.getXOffset();
				int new_z = (int) Math.round(old_z * Math.cos(angle) - old_x * Math.sin(angle));
				int new_x = (int) Math.round(old_z * Math.sin(angle) + old_x * Math.cos(angle));
				newBlockSet.add(new GildorymTreeBlock(new_x, treeBlock.getYOffset(),
						new_z, treeBlock.getBlockType()));
			}
			GildorymTree newTree = new GildorymTree(tree);
			newTree.setBlockSet(newBlockSet);
			return newTree;
		} else {
			return tree;
		}
	}
	
	static Set<GildorymTreeBlock> scanTree(Block[] selectedBlocks, World world) {
		//selectedBlocks[0]/[1]: Corner Blocks of Bounds
		//selectedBlocks[2]: Sapling Block
		int xmin = (int) Math.min(selectedBlocks[0].getX(), selectedBlocks[1].getX());
		int xmax = (int) Math.max(selectedBlocks[0].getX(), selectedBlocks[1].getX());
		int zmin = (int) Math.min(selectedBlocks[0].getZ(), selectedBlocks[1].getZ());
		int zmax = (int) Math.max(selectedBlocks[0].getZ(), selectedBlocks[1].getZ());
		int c_x = selectedBlocks[2].getX();
		int c_y = selectedBlocks[2].getY();
		int c_z = selectedBlocks[2].getZ();
		int y = c_y;
		
		Set<GildorymTreeBlock> blockSet = new HashSet<GildorymTreeBlock>();
		boolean scanComplete = false;
		
		while (!scanComplete) {
			boolean foundTreeBlock = false;
			for (int x = xmin; x <= xmax; x++) {
				for (int z = zmin; z <= zmax; z++) {
					Block block = world.getBlockAt(x, y, z);
					if (block.getType() == Material.LOG || block.getType() == Material.LEAVES) {
						if (!(y == c_y && block.getType() == Material.LEAVES)) {
							foundTreeBlock = true;
							GildorymTreeBlock treeBlock = new GildorymTreeBlock(x - c_x, y - c_y, z - c_z, block.getType());
							blockSet.add(treeBlock);
						}
					}
				}
			}
			if (foundTreeBlock == true) {
				y++;
			} else {
				scanComplete = true;
			}
		}
		
		return blockSet;
	}
	
	static int getHeight(Set<GildorymTreeBlock> blockSet) {
		int max_y_offset = 0;
		for (GildorymTreeBlock treeBlock : blockSet) {
			if (treeBlock.getBlockType() == Material.LOG && treeBlock.getYOffset() > max_y_offset) {
				max_y_offset = treeBlock.getYOffset();
			}
		}
		return (max_y_offset + 1);
	}
	
	TreeGroup treeGroup;
	TreeRarity treeRarity;
	TreeSpecies treeSpecies;
	Set<GildorymTreeBlock> blockSet;
	int height;
	boolean valid;
	
	public GildorymTree(TreeGroup treeGroup, TreeRarity treeRarity, TreeSpecies treeSpecies, Set<GildorymTreeBlock> blockSet, int height) {
		this.treeGroup = treeGroup;
		this.treeRarity = treeRarity;
		this.treeSpecies = treeSpecies;
		this.blockSet = blockSet;
		this.height = height;
		this.valid = true;
	}
	
	public GildorymTree(File file) {
		
	}
	
	public GildorymTree(GildorymTree tree) {
		this.treeGroup = tree.getTreeGroup();
		this.treeRarity = tree.getTreeRarity();
		this.treeSpecies = tree.getTreeSpecies();
		this.blockSet = tree.getBlockSet();
		this.height = tree.getHeight();
		this.valid = tree.getValid();
	}
	
	public Set<GildorymTreeBlock> getBlockSet() {
		return blockSet;
	}
	
	public int getHeight() {
		return height;
	}
	
	public TreeGroup getTreeGroup() {
		return treeGroup;
	}
	
	public TreeRarity getTreeRarity() {
		return treeRarity;
	}
	
	public TreeSpecies getTreeSpecies() {
		return treeSpecies;
	}
	
	public boolean getValid() {
		return valid;
	}
	
	public void setBlockSet(Set<GildorymTreeBlock> blockSet) {
		this.blockSet = blockSet;
	}
}
