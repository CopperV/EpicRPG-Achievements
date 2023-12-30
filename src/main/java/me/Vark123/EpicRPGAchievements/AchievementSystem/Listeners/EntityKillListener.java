package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

public class EntityKillListener implements Listener {

	@EventHandler
	public void onKill(MythicMobDeathEvent e) {
		Entity victim = e.getEntity();
		LivingEntity killer = e.getKiller();
		if(killer == null || !(killer instanceof Player))
			return;
		Player p = (Player) killer;
		
		String result = victim.getName();
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				AchievementManager.get().getAchievementsByTarget(result)
					.stream()
					.filter(achievement -> achievement.getCategory().getId().equals("KILL"))
					.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
					.forEach(achievement -> pa.updateAchievement(achievement, 1));
			});
	}
	
}
