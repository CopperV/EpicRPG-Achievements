package me.Vark123.EpicRPGAchievements.PlayerSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Vark123.EpicRPGAchievements.FileManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievements;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerAchievements pa = FileManager.loadPlayerAchievements(p);
		PlayerAchievementsManager.get().registerPlayerAchievements(pa);
	}

}
