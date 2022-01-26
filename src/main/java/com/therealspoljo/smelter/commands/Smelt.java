package com.therealspoljo.smelter.commands;

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

public class Smelt implements CommandExecutor {

	public Smelt() {
	}

	@Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
	if (!Permissions.SMELT.isAllowed(sender)) {
	    Lang.NO_PERMISSION.send(sender);
	    return true;
	}

	if (!(sender instanceof Player)) {
	    Lang.ONLY_PLAYER_COMMAND.send(sender);
	    return true;
	}

	if (args.length > 0) {
	    sender.sendMessage("�cUsage: �7" + command.getUsage().replaceAll("<command>", commandLabel));
	    return true;
	}

	Player player = (Player) sender;
	ItemStack mainHand = player.getInventory().getItemInMainHand();

	if (!Utils.isValidMaterial(mainHand)) {
	    Lang.INVALID_ITEM.send(sender);
	    return true;
	}

	if (ConfigUtils.isPerItem() && !Utils.hasItemPerm(player, mainHand.getType())) {
	    Lang.NO_ITEM_PERMISSION.send(sender);
	    return true;
	}

	String primaryGroup = Utils.getPrimaryGroup(player);
	int amount = mainHand.getAmount();
	double price = primaryGroup.isEmpty() ? 0 : ConfigUtils.getSmeltPerRankCost(primaryGroup);
	boolean isEconomyEnabled = ConfigUtils.isEconomy();

	if (isEconomyEnabled && !Utils.hasEnough(player, price * amount) && !Permissions.SMELT_FREE.isAllowed(sender)) {
	    String message = Lang.NOT_ENOUGH_MONEY.toString();
	    message = message.replaceAll("%money_needed", price + "");

	    player.sendMessage(message);
	    return true;
	}

	if (TempStorage.isOnCooldown(player.getUniqueId(), false)) {
	    String message = Lang.ON_COOLDOWN.toString();
	    message = message.replaceAll("%time_left", TempStorage.getFormattedTimeLeft(player.getUniqueId(), false) + "");

	    player.sendMessage(message);
	    return true;
	}

	player.getInventory().setItemInMainHand(Utils.getSmeltedItemStack(mainHand));

	if (isEconomyEnabled && !Permissions.SMELT_FREE.isAllowed(sender)) {
	    Utils.withdrawPlayer(player, price * amount);
	}

	Lang.SMELTED_one.send(player);

	if (!Permissions.SMELT_NO_COOLDOWN.isAllowed(player)) {
	    TempStorage.updateCooldown(player.getUniqueId(), false);
	}
	return true;
    }
}