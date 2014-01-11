package com.gildorymrp.gildorymtrees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;

public class TreeDataManager {

	public static String saveTree(GildorymTreeGenerator plugin, GildorymTree tree) {
		try {
			if (!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			String treeGroup = tree.getTreeGroup().name();
			String treeRarity = tree.getTreeRarity().name();
			int i = 1;
			String baseFileName = treeGroup + "-" + treeRarity + "-";
			String fileName = null;
			File file;
			do {
				fileName = baseFileName + i + ".tree";
				file = new File(plugin.getDataFolder().getAbsolutePath()
						+ File.separator + fileName);
				i++;
			} while (file.exists());
			file.createNewFile();
			PrintWriter out = new PrintWriter(new FileWriter(file));
			String[] fileData = convertTreeToText(tree);
			for (int n = 0; n < fileData.length; n++) {
				out.println(fileData[n]);
			}
			out.close();
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void LoadTrees(GildorymTreeGenerator plugin) {
		
	}
	
	public static String[] convertTreeToText(GildorymTree tree) {
		String treeGroup = tree.getTreeGroup().name();
		String treeRarity = tree.getTreeRarity().name();
		String treeSpecies = tree.getTreeSpecies().name();
		String height = "" + tree.getHeight();
		Set<GildorymTreeBlock> blockSet = tree.getBlockSet();
		int blockCount = blockSet.size();
		
		String[] fileData = new String[4 + blockCount];
		fileData[0] = treeGroup;
		fileData[1] = treeRarity;
		fileData[2] = treeSpecies;
		fileData[3] = height;
		int i = 4;
		for (GildorymTreeBlock treeBlock : blockSet) {
			fileData[i] = treeBlock.getXOffset() + " " + treeBlock.getYOffset() + " " + treeBlock.getZOffset() + " ";
			if (treeBlock.getBlockType() == Material.LOG) {
				fileData[i] = fileData[i] + "LOG";
			} else {
				fileData[i] = fileData[i] + "LEAVES";
			}
		}
		return fileData;
	}
	
	public static GildorymTree convertTextToTree(String[] fileData) {
		try {
			TreeGroup treeGroup = TreeGroup.valueOf(fileData[0]);
			TreeRarity treeRarity = TreeRarity.valueOf(fileData[1]);
			TreeSpecies treeSpecies = TreeSpecies.valueOf(fileData[2]);
			int height = Integer.parseInt(fileData[3]);
			Set<GildorymTreeBlock> blockSet = new HashSet<GildorymTreeBlock>();
			for (int i = 4; i < fileData.length; i++) {
				String[] blockData = fileData[i].split(" ");
				int x_offset = Integer.parseInt(blockData[0]);
				int y_offset = Integer.parseInt(blockData[1]);
				int z_offset = Integer.parseInt(blockData[2]);
				Material blockType = null;
				if (blockData[3].equalsIgnoreCase("LOG")) {
					blockType = Material.LOG;
				} else if (blockData[3].equalsIgnoreCase("LEAVES")) {
					blockType = Material.LEAVES;
				} else {
					throw new IllegalArgumentException("Invalid block type on line " + i);
				}
				GildorymTreeBlock treeBlock = new GildorymTreeBlock(x_offset, y_offset, z_offset, blockType);
				blockSet.add(treeBlock);
			}
			GildorymTree tree = new GildorymTree(treeGroup, treeRarity, treeSpecies, blockSet, height);
			return tree;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public void loadTrees(GildorymTreeGenerator plugin) {
		try {
			if (!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			if (plugin.getDataFolder().listFiles() == null || plugin.getDataFolder().listFiles().length == 0) {
				plugin.getLogger().info("No trees found!");
			}
			for (File file : plugin.getDataFolder().listFiles()) {
				if (file.isFile()) {
					String fileName = file.getName();
					int sindex = fileName.lastIndexOf('.');
					if (sindex > 0) {
						String extension = fileName.substring(sindex + 1);
						if (extension.equalsIgnoreCase("tree")) {
							BufferedReader br = new BufferedReader(new FileReader(file));
							StringBuilder sb = new StringBuilder();
							String line = br.readLine();
							while (line != null) {
								sb.append(line);
								sb.append(",");
							}
							if (sb.length() == 0) {
								continue;
							}
							sb.setLength(sb.length() - 1);
							String[] fileData = sb.toString().split(",");
							GildorymTree tree;
							tree = convertTextToTree(fileData);
							if (tree != null) {
								String treeName = fileName.split("\\.")[0];
								plugin.getTreeMap().put(treeName, tree);
							}
							br.close();
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

}
