package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;
import me.Vark123.EpicRPGSkillsAndQuests.PlayerSystem.PlayerQuestImpl.PlayerDungeonQuest;
import me.Vark123.EpicRPGSkillsAndQuests.QuestSystem.DungeonSystem.Events.DungeonEndEvent;

public class DungeonPassListener implements Listener {

	@EventHandler
	public void onDungeonPass(DungeonEndEvent e) {
		PlayerDungeonQuest dungeon = e.getDungeon();
		List<Player> players = new LinkedList<>();
		if(dungeon.getParty().isEmpty())
			players.add(dungeon.getPartyPlayer().getPlayer());
		else
			players = dungeon.getParty().get().getMembers()
				.stream()
				.map(member -> member.getPlayer())
				.collect(Collectors.toList());
		
		String result = dungeon.getQuest().getDisplay();
		players.stream().forEach(p -> {
			PlayerAchievementsManager.get().getPlayerAchievements(p)
				.ifPresent(pa -> {
					AchievementManager.get().getAchievementsByTarget(result)
						.stream()
						.filter(achievement -> achievement.getDifficulty().isPresent())
						.filter(achievement -> {
							String world = dungeon.getWorld().toLowerCase();
							switch(achievement.getDifficulty().get().toLowerCase()) {
								case "normal":
									return !(world.contains("heroic") || world.contains("mythic"));
								case "heroic":
									return world.contains("heroic");
								case "mythic":
									return world.contains("mythic");
								default:
									return false;
							}
						})
						.filter(achievement -> achievement.getCategory().getId().equals("DUNGEON_PASS"))
						.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
						.forEach(achievement -> pa.updateAchievement(achievement, 1));
				});
		});
	}

}
