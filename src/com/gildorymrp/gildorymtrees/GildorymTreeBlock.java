package com.gildorymrp.gildorymtrees;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class GildorymTreeBlock {

	private int x_offset;
	private int y_offset;
	private int z_offset;
	private Material blockType;
	
	public GildorymTreeBlock (Block treeBlock, Block centralBlock) {
		this.blockType = treeBlock.getType();
		Location blockLocation = treeBlock.getLocation();
		Location centralLocation = centralBlock.getLocation();
		this.x_offset = blockLocation.getBlockX() - centralLocation.getBlockX();
		this.y_offset = blockLocation.getBlockY() - centralLocation.getBlockY();
		this.z_offset = blockLocation.getBlockZ() - centralLocation.getBlockZ();
	}
	
	public GildorymTreeBlock (int x_offset, int y_offset, int z_offset, Material blockType) {
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.z_offset = z_offset;
		this.blockType = blockType;
	}
	
	public int getXOffset() {
		return x_offset;
	}
	
	public int getYOffset() {
		return y_offset;
	}
	
	public int getZOffset() {
		return z_offset;
	}
	
	public Material getBlockType() {
		return this.blockType;
	}
	
}
