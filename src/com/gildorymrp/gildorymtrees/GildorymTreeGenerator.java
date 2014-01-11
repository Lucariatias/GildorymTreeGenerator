package com.gildorymrp.gildorymtrees;

import java.util.HashMap;
import java.util.Map;

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
		registerListeners(new Listener[] { new PlayerInteractListener(this) });
	}
	
	public Map<String, GildorymTree> getTreeMap() {
		return treeMap;
	}
		
	private void registerListeners(Listener[] listeners) {
		for (Listener listener : listeners)
			getServer().getPluginManager().registerEvents(listener, this);
	}
	
}
