package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;
import me.Vark123.EpicRPGSkillsAndQuests.PlayerSystem.APlayerQuest;
import me.Vark123.EpicRPGSkillsAndQuests.QuestSystem.Events.QuestEndEvent;

public class QuestCompleteListener implements Listener {

	@EventHandler
	public void onQuestComplete(QuestEndEvent e) {
		APlayerQuest pQuest = e.getPlayerQuest();
		Player p = pQuest.getPlayer();
		String result = pQuest.getQuest().getId();
		
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				AchievementManager.get().getAchievementsByTarget(result)
					.stream()
					.filter(achievement -> achievement.getCategory().getId().equals("QUEST"))
					.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
					.forEach(achievement -> pa.updateAchievement(achievement, 1));
			});
	}
	
}
