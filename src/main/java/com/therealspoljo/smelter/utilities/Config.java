package com.therealspoljo.smelter.utilities;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config extends YamlConfiguration {

    public static Config createConfig(Plugin plugin, String file) {
	return new Config(plugin, file + (!file.endsWith(".yml") ? ".yml" : ""));
    }

    private final File configFile;

    public Config(Plugin plugin, String file) {
	this(plugin, new File(plugin.getDataFolder(), file));
    }

    public Config(Plugin plugin, File file) {
	configFile = file;

	if (!plugin.getDataFolder().exists()) {
	    plugin.getDataFolder().mkdir();
	}

	if (!configFile.exists()) {
	    if (plugin.getResource(file.getName()) == null) {
		try {
		    configFile.createNewFile();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    } else {
		plugin.saveResource(file.getName(), true);
	    }
	}

	reload();
    }

    public boolean reload() {
	try {
	    load(configFile);
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    public boolean save() {
	try {
	    save(configFile);
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
}