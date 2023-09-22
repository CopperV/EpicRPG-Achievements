package me.Vark123.EpicRPGAchievements;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;

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
	}

	@Override
	public void onDisable() {
		
	}
	
}
