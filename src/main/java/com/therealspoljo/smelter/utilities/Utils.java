package com.therealspoljo.smelter.utilities;

import com.therealspoljo.smelter.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Utils {

    private Utils() {
    }

    public static void setup() {
        smeltingRecipes = new HashMap<>();
        var whitelistedMaterials = ConfigUtils.getWhitelistedMaterials();
        Iterator<Recipe> iter = Bukkit.recipeIterator();
        while(iter.hasNext()){
            Recipe recipe = iter.next();
            if(recipe instanceof FurnaceRecipe && whitelistedMaterials.contains(((FurnaceRecipe) recipe).getInput().getType())){
                smeltingRecipes.put(((FurnaceRecipe) recipe).getInput().getType(), (FurnaceRecipe) recipe);
            }
        }
    }
    private static Map<Material, FurnaceRecipe> smeltingRecipes;

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean isValidMaterial(ItemStack stack) {
        return getSmeltedItemStack(stack) != null;
    }

    public static boolean hasEnough(Player player, double amount) {
        return Main.getInstance().getEconomy().has(player, amount);
    }

    public static void withdrawPlayer(Player player, double amount) {
        Main.getInstance().getEconomy().withdrawPlayer(player, amount);
    }

    public static String getPrimaryGroup(Player player) {
        try {
            return Main.getInstance().getPermission().getPrimaryGroup(player);
        } catch (Exception e) {
            return "";
        }
    }

    public static ItemStack getSmeltedItemStack(ItemStack itemStack) {
        FurnaceRecipe recipe = smeltingRecipes.get(itemStack.getType());
        ItemStack result = null;
        if (recipe != null) {
             result = recipe.getResult();
            result.setAmount(itemStack.getAmount());
        }
        return result;
    }

    public static boolean hasItemPerm(Player player, Material material) {
        player.hasPermission("smelter.item." + material.name().toLowerCase());
        return false;
    }
}