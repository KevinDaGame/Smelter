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
//        switch (itemStack.getType()) {
//            case PORKCHOP: {
//                return new ItemStack(Material.COOKED_PORKCHOP, itemStack.getAmount());
//            }
//
//            case BEEF: {
//                return new ItemStack(Material.COOKED_BEEF, itemStack.getAmount());
//            }
//
//            case CHICKEN: {
//                return new ItemStack(Material.COOKED_CHICKEN, itemStack.getAmount());
//            }
//
//            case COD: {
//                return new ItemStack(Material.COOKED_COD, itemStack.getAmount());
//            }
//            case SALMON: {
//                return new ItemStack(Material.COOKED_SALMON, itemStack.getAmount());
//            }
//
//            case POTATO: {
//                return new ItemStack(Material.BAKED_POTATO, itemStack.getAmount());
//            }
//
//            case MUTTON: {
//                return new ItemStack(Material.COOKED_MUTTON, itemStack.getAmount());
//            }
//
//            case RABBIT: {
//                return new ItemStack(Material.COOKED_RABBIT, itemStack.getAmount());
//            }
//
//            case IRON_ORE: {
//                return new ItemStack(Material.IRON_INGOT, itemStack.getAmount());
//            }
//
//            case GOLD_ORE: {
//                return new ItemStack(Material.GOLD_INGOT, itemStack.getAmount());
//            }
//            case NETHER_GOLD_ORE: {
//                return new ItemStack(Material.GOLD_INGOT, itemStack.getAmount());
//            }
//
//            case SAND: {
//                return new ItemStack(Material.GLASS, itemStack.getAmount());
//            }
//            case RED_SAND: {
//                return new ItemStack(Material.GLASS, itemStack.getAmount());
//            }
//            case SANDSTONE: {
//                return new ItemStack(Material.SMOOTH_SANDSTONE, itemStack.getAmount());
//            }
//            case RED_SANDSTONE: {
//                return new ItemStack(Material.SMOOTH_RED_SANDSTONE, itemStack.getAmount());
//            }
//            case QUARTZ_BLOCK: {
//                return new ItemStack(Material.SMOOTH_QUARTZ, itemStack.getAmount());
//            }
//
//            case COBBLESTONE: {
//                return new ItemStack(Material.STONE, itemStack.getAmount());
//            }
//
//            case STONE: {
//                return new ItemStack(Material.SMOOTH_STONE, itemStack.getAmount());
//            }
//
//            case CLAY_BALL: {
//                return new ItemStack(Material.BRICK, itemStack.getAmount());
//            }
//
//            case NETHERRACK: {
//                return new ItemStack(Material.NETHER_BRICK, itemStack.getAmount());
//            }
//
//            case CLAY: {
//                return new ItemStack(Material.TERRACOTTA, itemStack.getAmount());
//            }
//
//            case STONE_BRICKS: {
//                return new ItemStack(Material.CRACKED_STONE_BRICKS, itemStack.getAmount());
//            }
//            case INFESTED_STONE_BRICKS: {
//                return new ItemStack(Material.CRACKED_STONE_BRICKS, itemStack.getAmount());
//            }
//
//            case NETHER_BRICKS: {
//                return new ItemStack(Material.CRACKED_NETHER_BRICKS, itemStack.getAmount());
//            }
//            case POLISHED_BLACKSTONE_BRICKS: {
//                return new ItemStack(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, itemStack.getAmount());
//            }
//
//            case DIAMOND_ORE: {
//                return new ItemStack(Material.DIAMOND, itemStack.getAmount());
//            }
//
//            case LAPIS_ORE: {
//                return new ItemStack(Material.LAPIS_LAZULI, itemStack.getAmount());
//            }
//
//            case REDSTONE_ORE: {
//                return new ItemStack(Material.REDSTONE, itemStack.getAmount());
//            }
//
//            case COAL_ORE: {
//                return new ItemStack(Material.COAL, itemStack.getAmount());
//            }
//
//            case EMERALD_ORE: {
//                return new ItemStack(Material.EMERALD, itemStack.getAmount());
//            }
//
//            case NETHER_QUARTZ_ORE: {
//                return new ItemStack(Material.QUARTZ, itemStack.getAmount());
//            }
//
//            case OAK_LOG:
//            case SPRUCE_LOG:
//            case BIRCH_LOG:
//            case JUNGLE_LOG:
//            case ACACIA_LOG:
//            case DARK_OAK_LOG:
//            case STRIPPED_OAK_LOG:
//            case STRIPPED_SPRUCE_LOG:
//            case STRIPPED_BIRCH_LOG:
//            case STRIPPED_JUNGLE_LOG:
//            case STRIPPED_ACACIA_LOG:
//            case STRIPPED_DARK_OAK_LOG:
//            case OAK_WOOD:
//            case SPRUCE_WOOD:
//            case BIRCH_WOOD:
//            case JUNGLE_WOOD:
//            case ACACIA_WOOD:
//            case DARK_OAK_WOOD:
//            case STRIPPED_OAK_WOOD:
//            case STRIPPED_SPRUCE_WOOD:
//            case STRIPPED_BIRCH_WOOD:
//            case STRIPPED_JUNGLE_WOOD:
//            case STRIPPED_ACACIA_WOOD:
//            case STRIPPED_DARK_OAK_WOOD:{
//                return new ItemStack(Material.CHARCOAL, itemStack.getAmount());
//            }
//
//
//            case CACTUS: {
//                return new ItemStack(Material.GREEN_DYE, itemStack.getAmount());
//            }
//
//            case WET_SPONGE: {
//                return new ItemStack(Material.SPONGE, itemStack.getAmount());
//            }
//
//            case CHORUS_FRUIT: {
//                return new ItemStack(Material.POPPED_CHORUS_FRUIT, itemStack.getAmount());
//            }
//
//            case ANCIENT_DEBRIS:{
//                return new ItemStack(Material.NETHERITE_SCRAP, itemStack.getAmount());
//            }
//
//            case WHITE_TERRACOTTA:{
//                return  new ItemStack(Material.WHITE_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case ORANGE_TERRACOTTA:{
//                return  new ItemStack(Material.ORANGE_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case MAGENTA_TERRACOTTA:{
//                return  new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case LIGHT_BLUE_TERRACOTTA:{
//                return  new ItemStack(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case YELLOW_TERRACOTTA:{
//                return  new ItemStack(Material.YELLOW_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case LIME_TERRACOTTA:{
//                return  new ItemStack(Material.LIME_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case PINK_TERRACOTTA:{
//                return  new ItemStack(Material.PINK_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case GRAY_TERRACOTTA:{
//                return  new ItemStack(Material.GRAY_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case LIGHT_GRAY_TERRACOTTA:{
//                return  new ItemStack(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case CYAN_TERRACOTTA:{
//                return  new ItemStack(Material.CYAN_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case PURPLE_TERRACOTTA:{
//                return  new ItemStack(Material.PURPLE_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case BLUE_TERRACOTTA:{
//                return  new ItemStack(Material.BLUE_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case BROWN_TERRACOTTA:{
//                return  new ItemStack(Material.BROWN_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case GREEN_TERRACOTTA:{
//                return  new ItemStack(Material.GREEN_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case RED_TERRACOTTA:{
//                return  new ItemStack(Material.RED_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case BLACK_TERRACOTTA:{
//                return  new ItemStack(Material.BLACK_GLAZED_TERRACOTTA, itemStack.getAmount());
//            }
//            case KELP:{
//                return new ItemStack(Material.DRIED_KELP, itemStack.getAmount());
//            }
//
//            default:
//                return null;
//        }

    public static boolean hasItemPerm(Player player, Material material) {
        player.hasPermission("smelter.item." + material.name().toLowerCase());
        return false;
    }

    // public static boolean isAFuel(ItemStack item) {
    // switch (item.getType()) {
    // case LAVA_BUCKET:
    // case COAL_BLOCK:
    // case BLAZE_ROD:
    // case COAL:
    // case LOG:
    // case LOG_2:
    // case WOOD:
    // case WOOD_PLATE:
    // case FENCE:
    // case ACACIA_FENCE:
    // case BIRCH_FENCE:
    // case DARK_OAK_FENCE:
    // case JUNGLE_FENCE:
    // case SPRUCE_FENCE:
    // case FENCE_GATE:
    // case ACACIA_FENCE_GATE:
    // case BIRCH_FENCE_GATE:
    // case DARK_OAK_FENCE_GATE:
    // case JUNGLE_FENCE_GATE:
    // case SPRUCE_FENCE_GATE:
    // case WOOD_STAIRS:
    // case ACACIA_STAIRS:
    // case BIRCH_WOOD_STAIRS:
    // case DARK_OAK_STAIRS:
    // case JUNGLE_WOOD_STAIRS:
    // case SPRUCE_WOOD_STAIRS:
    // case TRAP_DOOR:
    // case WORKBENCH:
    // case BOOKSHELF:
    // case CHEST:
    // case TRAPPED_CHEST:
    // case DAYLIGHT_DETECTOR:
    // case DAYLIGHT_DETECTOR_INVERTED:
    // case JUKEBOX:
    // case NOTE_BLOCK:
    // case HUGE_MUSHROOM_1:
    // case HUGE_MUSHROOM_2:
    // case BANNER:
    // case STANDING_BANNER:
    // case WALL_BANNER:
    // case WOOD_AXE:
    // case WOOD_HOE:
    // case WOOD_PICKAXE:
    // case WOOD_SPADE:
    // case WOOD_SWORD:
    // case DOUBLE_STEP:
    // case WOOD_DOUBLE_STEP:
    // case WOOD_STEP:
    // case SAPLING:
    // case STICK:
    // return true;
    // default:
    // return false;
    // }
    // }
}