package me.Vark123.EpicRPGAchievements.PlayerSystem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public final class PlayerAchievementsManager {

	private static final PlayerAchievementsManager inst = new PlayerAchievementsManager();
	
	private final Collection<PlayerAchievements> playerAchievements;
	
	private PlayerAchievementsManager() {
		playerAchievements = new HashSet<>();
	}
	
	public static final PlayerAchievementsManager get() {
		return inst;
	}
	
	public void registerPlayerAchievements(PlayerAchievements pa) {
		playerAchievements.add(pa);
	}
	
	public void unregisterPlayerAchievements(PlayerAchievements pa) {
		playerAchievements.remove(pa);
	}
	
	public Optional<PlayerAchievements> getPlayerAchievements(Player p) {
		return playerAchievements.stream()
				.filter(pa -> pa.getPlayer().equals(p))
				.findAny();
	}
	
}
