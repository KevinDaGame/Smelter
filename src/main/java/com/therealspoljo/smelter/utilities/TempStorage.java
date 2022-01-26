package com.therealspoljo.smelter.utilities;

import java.util.UUID;

import com.google.common.collect.HashBasedTable;
import com.therealspoljo.smelter.Main;

public final class TempStorage {

    private TempStorage() {
    }

    private static final HashBasedTable<UUID, String, Long> cooldowns = HashBasedTable.create();

    public static boolean isOnCooldown(UUID uuid, boolean all) {
	String key = all ? "SmeltAll" : "Smelt";

	if (!cooldowns.contains(uuid, key)) {
	    return false;
	}

	if (cooldowns.get(uuid, key) < System.currentTimeMillis()) {
	    removeCooldown(uuid, all);
	    return false;
	}
	return true;
    }

    public static void updateCooldown(UUID uuid, boolean all) {
	String key = all ? "SmeltAll" : "Smelt";
	long time = ConfigUtils.getSmeltPerRankCooldown(
		Main.getInstance().getPermission().getPrimaryGroup(Main.getInstance().getServer().getPlayer(uuid)), all) * 1000;

	if (time <= 0) {
	    return;
	}

	cooldowns.put(uuid, key, System.currentTimeMillis() + time);
    }

    public static void removeCooldown(UUID uuid, boolean all) {
	String key = all ? "SmeltAll" : "Smelt";

	cooldowns.remove(uuid, key);
    }

    public static long getTimeLeft(UUID uuid, boolean all) {
	String key = all ? "SmeltAll" : "Smelt";
	long timeLeft = -1;

	if (isOnCooldown(uuid, all)) {
	    long cooldownDone = cooldowns.get(uuid, key);
	    timeLeft = cooldownDone - System.currentTimeMillis();
	}

	return timeLeft;
    }

    public static double getFormattedTimeLeft(UUID uuid, boolean all) {
	return getTimeLeft(uuid, all) / 1000.0;
    }
}