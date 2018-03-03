package me.NinetyNine.staffmode;

import org.bukkit.plugin.java.JavaPlugin;

import me.NinetyNine.staffmode.commands.StaffCommands;

public class StaffMode extends JavaPlugin {

	// Added some by NinetyNine 3/3/18.
	@Override
	public void onEnable() {
        getLogger().info("[StaffMode]" + " StaffMode has been enabled!");
        getServer().getPluginManager().registerEvents(new StaffEventHandlers(), this);
		
        // TODO add other commands here too
        getCommand("staff").setExecutor(new StaffCommands());
        getCommand("chat").setExecutor(new StaffCommands());
        getCommand("vanish").setExecutor(new StaffCommands());
	}
	
	@Override
	public void onDisable() {
		getLogger().info("[StaffMode]" + " StaffMode has been disabled!");
	}
}