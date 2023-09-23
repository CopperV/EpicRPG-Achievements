package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Vark123.EpicRPG.Core.Events.PlayerLevelUpdateEvent;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

public class LevelUpdateListener implements Listener {

	@EventHandler
	public void onUpdate(PlayerLevelUpdateEvent e) {
		Player p = e.getRpg().getPlayer();
		int result = e.getNewLevel();
		
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				AchievementManager.get().getAchievementsByTarget(""+result)
					.stream()
					.filter(achievement -> achievement.getCategory().getId().equals("LEVEL"))
					.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
					.forEach(achievement -> pa.updateAchievement(achievement, 1));
			});
	}
	
}
