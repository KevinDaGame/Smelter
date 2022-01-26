package com.therealspoljo.smelter.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.therealspoljo.smelter.enums.Lang;
import com.therealspoljo.smelter.enums.Permissions;
import com.therealspoljo.smelter.utilities.ConfigUtils;
import com.therealspoljo.smelter.utilities.TempStorage;
import com.therealspoljo.smelter.utilities.Utils;

public class SmeltAll implements CommandExecutor {

	public SmeltAll() {
	}

	@Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
	if (!Permissions.SMELTALL.isAllowed(sender)) {
	    Lang.NO_PERMISSION.send(sender);
	    return true;
	}

	if (!(sender instanceof Player)) {
	    Lang.ONLY_PLAYER_COMMAND.send(sender);
	    return true;
	}

	if (args.length > 0) {
	    sender.sendMessage("&cUsage: &7" + command.getUsage().replaceAll("<command>", commandLabel));
	    return true;
	}

	Player player = (Player) sender;
	String primaryGroup = Utils.getPrimaryGroup(player);
	int amount = 0;
	double price = primaryGroup.isEmpty() ? 0 : ConfigUtils.getSmeltPerRankCost(primaryGroup);

	for (ItemStack itemStack : player.getInventory().getContents()) {
	    if (itemStack == null || itemStack.getType() == Material.AIR) {
		continue;
	    }

	    if (!Utils.isValidMaterial(itemStack)) {
		continue;
	    }

	    if (ConfigUtils.isPerItem() && !Utils.hasItemPerm(player, itemStack.getType())) {
		continue;
	    }

	    amount += itemStack.getAmount();
	}

	if (amount <= 0) {
	    Lang.NO_SMELTABLE_ITEMS.send(sender);
	    return true;
	}

	boolean isEconomyEnabled = ConfigUtils.isEconomy();

	if (isEconomyEnabled && !Utils.hasEnough(player, price * amount)) {
	    String message = Lang.NOT_ENOUGH_MONEY.toString();
	    message = message.replaceAll("%money_needed", price + "");

	    player.sendMessage(message);
	    return true;
	}

	if (TempStorage.isOnCooldown(player.getUniqueId(), true)) {
	    String message = Lang.ON_COOLDOWN.toString();
	    message = message.replaceAll("%time_left", TempStorage.getFormattedTimeLeft(player.getUniqueId(), true) + "");

	    player.sendMessage(message);
	    return true;
	}

	for (ItemStack itemStack : player.getInventory().getContents()) {
	    if (itemStack == null || itemStack.getType() == Material.AIR) {
		continue;
	    }

	    if (!Utils.isValidMaterial(itemStack)) {
		continue;
	    }

	    if (ConfigUtils.isPerItem() && !Utils.hasItemPerm(player, itemStack.getType())) {
		continue;
	    }

	    ItemStack smelted = Utils.getSmeltedItemStack(itemStack);
	    if (smelted == null) {
		continue;
	    }

	    itemStack.setAmount(smelted.getAmount());
	    itemStack.setItemMeta(smelted.getItemMeta());
	    itemStack.setType(smelted.getType());
	    itemStack.setData(itemStack.getData());
	}

	if (isEconomyEnabled && !Permissions.SMELTALL_FREE.isAllowed(sender)) {
	    Utils.withdrawPlayer(player, price * amount);
	}

	Lang.SMELTED_all.send(player);

	if (!Permissions.SMELTALL_NO_COOLDOWN.isAllowed(player)) {
	    TempStorage.updateCooldown(player.getUniqueId(), true);
	}
	return true;
    }
}