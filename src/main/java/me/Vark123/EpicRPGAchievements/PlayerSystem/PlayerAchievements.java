package me.Vark123.EpicRPGAchievements.PlayerSystem;

import java.util.Collection;
import java.util.Map;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import lombok.Builder;
import lombok.Getter;
import me.Vark123.EpicRPGAchievements.Config;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Achievement;

@Getter
@Builder
public class PlayerAchievements {

	private Player player;
	private Map<Achievement, Integer> achievementsInProgress;
	private Collection<String> completedAchievements;
	
	public void updateAchievement(Achievement achievement, int toAdd) {
		int progress = achievementsInProgress.getOrDefault(achievement, 0);
		progress += toAdd;
		if(progress >= achievement.getAmount()) {
			completeAchievement(achievement);
			return;
		}
		achievementsInProgress.put(achievement, progress);
	}
	
	public void completeAchievement(Achievement achievement) {
		achievementsInProgress.remove(achievement);
		completedAchievements.add(achievement.getId());
		
		player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1.15f);
		player.spawnParticle(Particle.FLAME, player.getLocation().clone().add(0,1.25,0), 30,
				0.8, 0.8, 0.8, 0.1);
		player.sendMessage("§7["+Config.get().getPrefix()+"§7] §aWykonano osiagniecie §r"+achievement.getDisplay());
		achievement.getRewards().forEach(reward -> reward.giveReward(player));
	}
	
}
