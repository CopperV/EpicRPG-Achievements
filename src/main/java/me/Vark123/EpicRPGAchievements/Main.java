package me.Vark123.EpicRPGAchievements;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

@Getter
public class Main extends JavaPlugin {

	@Getter
	private static Main inst;
	
	private InventoryManager invManager;
	
	@Override
	public void onEnable() {
		inst = this;
		
		invManager = new InventoryManager(inst);
		invManager.invoke();
		
		ListenerManager.registerListeners();
		CommandManager.setExecutors();
		FileManager.init();
		
		Bukkit.getOnlinePlayers().stream()
			.map(FileManager::loadPlayerAchievements)
			.forEach(PlayerAchievementsManager.get()::registerPlayerAchievements);
	}

	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().stream()
			.map(p -> PlayerAchievementsManager.get().getPlayerAchievements(p))
			.filter(pa -> pa.isPresent())
			.map(pa -> pa.get())
			.forEach(FileManager::savePlayerAchievements);
		PlayerAchievementsManager.get().getPlayerAchievements().clear();
	}
	
}
