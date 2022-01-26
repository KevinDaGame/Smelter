package com.therealspoljo.smelter.enums;

import org.bukkit.command.CommandSender;

import com.therealspoljo.smelter.Main;
import com.therealspoljo.smelter.utilities.Utils;

public enum Lang {

    TITLE("title", "&7[&eSmelter&7]&r "),
    ONLY_PLAYER_COMMAND("only-player-command", "&cThis command can only be run by a player."),
    NO_PERMISSION("no-permission", "&cYou don't have permission to perform this action."),
    NO_ITEM_PERMISSION("no-item-permission", "&cYou don't have permission to smelt this item."),
    NO_SMELTABLE_ITEMS("no-smeltable-items", "&cYou have no smeltable items in your inventory."),
    CONFIGS_RELOADED("configs-reloaded", "&7Configs have been reloaded."),
    SMELTED_one("smelted-one", "&7Successfully smelted item from your hand."),
    SMELTED_all("smelted-all", "&7Successfully smelted all smeltable items."),
    NOT_ENOUGH_MONEY("not-enough-money", "&cYou don't have enough money. Money needed: &e%money_needed&c."),
    INVALID_ITEM("invalid-item", "&cPlease use a valid item to smelt."),
    ON_COOLDOWN("on-cooldown", "&cYou are on a cooldown for &e%time_left &cmore seconds.");

    private String message;

    Lang(String path, String defaultMessage) {
	this.message = Main.getInstance().getLangConfig().getString(path, defaultMessage);
    }

    public String getMessage() {
	return message;
    }

    @Override
    public String toString() {
	return Utils.colorize(TITLE.getMessage()) + Utils.colorize(getMessage());
    }

    public void send(CommandSender sender) {
	sender.sendMessage(this.toString());
    }
}