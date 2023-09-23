package me.Vark123.EpicRPGAchievements;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementCommand;

public final class CommandManager {

	private CommandManager() { }
	
	public static void setExecutors() {
		Bukkit.getPluginCommand("osiagniecia").setExecutor(new AchievementCommand());
	}
	
}
