package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;
import me.Vark123.EpicRecipes.CraftingSystem.CraftEvent;

public class CraftingListener implements Listener {

	@EventHandler
	public void onCraft(CraftEvent e) {
		if(e.isCancelled())
			return;
		
		Player p = e.getPlayer();
		String result = e.getRecipe().getMmResult();
		
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				AchievementManager.get().getAchievementsByTarget(result)
					.stream()
					.filter(achievement -> achievement.getCategory().getId().equals("CRAFTING"))
					.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
					.forEach(achievement -> pa.updateAchievement(achievement, 1));
			});
	}
	
}
