package com.therealspoljo.smelter.enums;

import org.bukkit.command.CommandSender;

public enum Permissions {

    ADMIN("smelter.admin"),
    SMELT("smelter.smelt"),
    SMELT_FREE("smelter.smelt.free"),
    SMELT_NO_COOLDOWN("smelter.smelt.no-cooldown"),
    SMELTALL("smelter.smeltall"),
    SMELTALL_FREE("smelter.smeltall.free"),
    SMELTALL_NO_COOLDOWN("smelter.smeltall.no-cooldown");

    private final String node;

    Permissions(String node) {
	this.node = node;
    }

    public String getNode() {
	return node;
    }

    public boolean isAllowed(CommandSender sender) {
	return sender.isOp() || sender.hasPermission(this.getNode());
    }

    public static String getNode(Permissions type) {
	return type.toString();
    }
}