package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;

import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

public class DiscoverListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.isCancelled())
			return;
		if(e.getFrom().getBlock().getLocation().equals(e.getTo().getBlock().getLocation()))
			return;
		
		Player p = e.getPlayer();
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				WorldGuard.getInstance().getPlatform()
					.getRegionContainer().createQuery()
					.getApplicableRegions(BukkitAdapter.adapt(e.getTo()))
					.getRegions()
					.stream()
					.map(region -> region.getId())
					.map(result -> AchievementManager.get().getAchievementsByTarget(result))
					.filter(list -> list != null && !list.isEmpty())
					.forEach(list -> list.stream()
							.filter(achievement -> achievement.getCategory().getId().equals("DISCOVER"))
							.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
							.forEach(achievement -> pa.updateAchievement(achievement, 1)));
			});
	}

}
