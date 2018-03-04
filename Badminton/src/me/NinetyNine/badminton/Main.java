package me.NinetyNine.badminton;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Badminton(), this);
		getCommand("badminton").setExecutor(new Badminton());
	}
	
	@Override
	public void onDisable() {
		
	}
}
