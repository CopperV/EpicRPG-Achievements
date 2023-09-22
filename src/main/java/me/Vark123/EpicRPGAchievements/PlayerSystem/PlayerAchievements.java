package me.Vark123.EpicRPGAchievements.PlayerSystem;

import java.util.Collection;
import java.util.Map;

import org.bukkit.entity.Player;

import lombok.Builder;
import lombok.Getter;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Achievement;

@Getter
@Builder
public class PlayerAchievements {

	private Player player;
	private Map<Achievement, Integer> achievementsInProgress;
	private Collection<String> completedAchievements;
	
}
