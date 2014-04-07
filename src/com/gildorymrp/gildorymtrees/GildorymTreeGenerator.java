package com.gildorymrp.gildorymtrees;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GildorymTreeGenerator extends JavaPlugin {

	public Map<String, GildorymTree> treeMap = new HashMap<String, GildorymTree>();
	public Map<String, TreeData> commandTreeDataMap = new HashMap<String, TreeData>();
	public Map<String, Block[]> commandBlockMap = new HashMap<String, Block[]>();
	
	public void onEnable() {
		getCommand("savetree").setExecutor(new SaveTreeCommand(this));
		getCommand("treegroups").setExecutor(new TreeGroupsCommand());
		registerListeners(new PlayerInteractListener(this));
	}
	
	public Map<String, GildorymTree> getTreeMap() {
		return treeMap;
	}
		
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners)
			getServer().getPluginManager().registerEvents(listener, this);
	}
	
	public static TreeGroup getTreeGroup(Biome biome, double temp, TreeType treeType) {
		TreeGroup treeGroup = null;
		Random random = new Random(System.currentTimeMillis());
		switch (biome) {
		case ICE_MOUNTAINS:
		case ICE_PLAINS:
			// Snowy Biomes: 100% Regular Spruce
			treeGroup = TreeGroup.SPRUCE;
			break;
		case TAIGA:
		case TAIGA_HILLS:
			if (temp <= 0.15) {
				// Cold Taiga: 100% Regular Spruce
				treeGroup = TreeGroup.SPRUCE;
			} else if (temp < 0.3) {
				// Taiga: 70% Regular Spruce, 30% Special Oak
				if (random.nextInt(10) <= 6) {
					treeGroup = TreeGroup.SPRUCE;
				} else {
					treeGroup = TreeGroup.OAK_SPECIAL;
				}
			} else {
				// Mega Taiga: 60% Regular Spruce, 35% Special Spruce, 5% Big Spruce
				int r = random.nextInt(20);
				if (r <= 11) {
					treeGroup = TreeGroup.SPRUCE;
				} else if (r <= 18) {
					treeGroup = TreeGroup.SPRUCE_SPECIAL;
				} else {
					treeGroup = TreeGroup.SPRUCE_LARGE;
				}
			}
			break;
		case EXTREME_HILLS:
			// 50% Regular Spruce, 50% Regular Oak
			if (random.nextBoolean()) {
				treeGroup = TreeGroup.SPRUCE;
			} else {
				treeGroup = TreeGroup.OAK;
			}
			break;
		case PLAINS:
			// 
			break;
		default:
			treeGroup = null;
			break;
		}
		return treeGroup;
	}
}
