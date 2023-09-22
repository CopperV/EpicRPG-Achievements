package me.Vark123.EpicRPGAchievements.PlayerSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Vark123.EpicRPGAchievements.FileManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		playerCleaner(e.getPlayer());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		playerCleaner(e.getPlayer());
	}
	
	private void playerCleaner(Player p) {
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				FileManager.savePlayerAchievements(pa);
				PlayerAchievementsManager.get().unregisterPlayerAchievements(pa);
			});
	}
	
}
