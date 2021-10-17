package com.therealspoljo.smelter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.therealspoljo.smelter.commands.Smelt;
import com.therealspoljo.smelter.commands.SmeltAll;
import com.therealspoljo.smelter.commands.Smelter;
import com.therealspoljo.smelter.utilities.Config;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {

    private static Main instance;

    private Economy econ = null;
    private Permission perms = null;

    private Config config, langConfig;

    @Override
    public void onEnable() {
	instance = this;

		if (!setupEconomy() ) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setupPermissions();

	config = Config.createConfig(this, "config");
	langConfig = Config.createConfig(this, "lang");

	registerCommands();
	setupPermissions();
    }

    @Override
    public void onDisable() {
	instance = null;

	econ = null;
	perms = null;

	config = null;
	langConfig = null;
    }

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

    private void registerCommands() {
	getCommand("smelt").setExecutor(new Smelt());
	getCommand("smeltall").setExecutor(new SmeltAll());
	getCommand("smelter").setExecutor(new Smelter());
    }

    public Config getLangConfig() {
	return langConfig;
    }

    @Override
    public Config getConfig() {
	return config;
    }

    public static Main getInstance() {
	return instance;
    }

    public Economy getEconomy() {
	return econ;
    }

    public Permission getPermission() {
	return perms;
    }
}