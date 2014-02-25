package com.gildorymrp.gildorymtrees;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class StructureGrowListener implements Listener {

	@EventHandler
	public void onStructureGrow(StructureGrowEvent event) {
		
		if (event.getSpecies() == TreeType.JUNGLE_BUSH || event.getSpecies() == TreeType.RED_MUSHROOM || event.getSpecies() == TreeType.BROWN_MUSHROOM) {
			// Ignore Mushroom/Bush growth
			return;
		}
		
		if (event.isCancelled() == false) {
			event.setCancelled(true);
			Location location = event.getLocation();
			TreeType treeType = event.getSpecies();
			Biome biome = event.getWorld().getBiome(location.getBlockX(), location.getBlockZ());
			double temp = event.getWorld().getTemperature(location.getBlockX(), location.getBlockZ());
			TreeGroup treeGroup = GildorymTreeGenerator.getTreeGroup(biome, temp, treeType);
		}
		
	}
	
}
