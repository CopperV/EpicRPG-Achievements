package me.Vark123.EpicRPGAchievements;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.CraftingListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.DiscoverListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.DungeonKillListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.DungeonPassListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.EntityKillListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.FishingListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.LevelUpdateListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.MiningListener;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners.QuestCompleteListener;
import me.Vark123.EpicRPGAchievements.PlayerSystem.Listeners.PlayerJoinListener;
import me.Vark123.EpicRPGAchievements.PlayerSystem.Listeners.PlayerQuitListener;

public final class ListenerManager {

	private ListenerManager() { }
	
	public static void registerListeners() {
		Main inst = Main.getInst();
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), inst);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), inst);

		Bukkit.getPluginManager().registerEvents(new CraftingListener(), inst);
		Bukkit.getPluginManager().registerEvents(new FishingListener(), inst);
		Bukkit.getPluginManager().registerEvents(new MiningListener(), inst);
		Bukkit.getPluginManager().registerEvents(new LevelUpdateListener(), inst);
		Bukkit.getPluginManager().registerEvents(new QuestCompleteListener(), inst);
		Bukkit.getPluginManager().registerEvents(new DiscoverListener(), inst);
		Bukkit.getPluginManager().registerEvents(new EntityKillListener(), inst);
		Bukkit.getPluginManager().registerEvents(new DungeonPassListener(), inst);
		Bukkit.getPluginManager().registerEvents(new DungeonKillListener(), inst);
	}
	
}
