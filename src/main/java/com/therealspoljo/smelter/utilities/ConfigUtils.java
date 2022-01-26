package com.therealspoljo.smelter.utilities;

import com.therealspoljo.smelter.enums.Lang;
import org.bukkit.Material;

import com.therealspoljo.smelter.Main;

import java.util.ArrayList;
import java.util.List;

public final class ConfigUtils {

    private ConfigUtils() {
    }

    public static boolean isPerItem() {
	return Main.getInstance().getConfig().getBoolean("per-item-permissions", false);
    }

    public static Material getMaterialForSmelting() {
	Material material = Material.getMaterial(Main.getInstance().getConfig().getString("items-required-for-smelting.item", "COAL"));

	if (material == null) {
	    return Material.COAL;
	}

	return material;
    }

    public static boolean isEconomy() {
	return !Main.getInstance().getConfig().getBoolean("items-required-for-smelting.enabled", false);
    }

    public static double getSmeltPerRankCost(String rank) {
	return Main.getInstance().getConfig().getDouble("ranks." + rank + ".cost", 0);
    }
    public static List<Material> getWhitelistedMaterials() {
        var list = Main.getInstance().getConfig().getStringList("smeltable-items");

        var newList = new ArrayList<Material>();
        for(String item: list){
            var material = Material.getMaterial(item);
            if(material == null){
                System.out.println(Lang.TITLE + "Invalid material specified in config!");
                continue;
            }
            newList.add(material);
        }
        return newList;
    }

    public static long getSmeltPerRankCooldown(String rank, boolean all) {
	String key = all ? "all" : "one";

	return Main.getInstance().getConfig().getLong("ranks." + rank + ".cooldown." + key, 0);
    }

    public static String getMessage(String value) {
	return Main.getInstance().getConfig().getString("messages." + value, "null");
    }

    public static String getPrefix() {
	return getMessage("prefix");
    }
}