package com.therealspoljo.smelter.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.therealspoljo.smelter.Main;
import com.therealspoljo.smelter.enums.Lang;
import com.therealspoljo.smelter.enums.Permissions;

public class Smelter implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!Permissions.ADMIN.isAllowed(sender)) {
			Lang.NO_PERMISSION.send(sender);
			return true;
		}

		if (args.length < 1 || args.length > 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7") + command.getUsage().replaceAll("<command>", commandLabel));
			return true;
		}

		if (args.length == 1) {
			switch (args[0].toLowerCase()) {
				case "reload":
				case "rl":
					Main.getInstance().getConfig().reload();
					Main.getInstance().getLangConfig().reload();
					Lang.CONFIGS_RELOADED.send(sender);
					return true;
			}

			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7") + command.getUsage().replaceAll("<command>", commandLabel));
			return true;
		}

		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7") + command.getUsage().replaceAll("<command>", commandLabel));
		return true;
	}
}