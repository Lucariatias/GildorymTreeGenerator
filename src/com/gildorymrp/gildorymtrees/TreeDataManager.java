package com.gildorymrp.gildorymtrees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
			FileOutputStream fos = new FileOutputStream(plugin.getDataFolder()
					.getAbsolutePath() + File.separator + fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tree);
			oos.close();
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
