package me.Vark123.EpicRPGAchievements.AchievementSystem;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Material;

import lombok.Getter;

@Getter
public final class AchievementManager {

	private static final AchievementManager inst = new AchievementManager();
	
	private final Collection<Achievement> achievements;
	private final Collection<AchievementCategory> categories;
	
	private AchievementManager() {
		achievements = new HashSet<>();
		categories = new HashSet<>();
		
		categories.add(AchievementCategory.builder()
				.id("KILL")
				.display("§c§oZabojstwa")
				.material(Material.IRON_SWORD)
				.build());
		categories.add(AchievementCategory.builder()
				.id("DUNGEON_KILL")
				.display("§4§lWyzwania dungeonowe")
				.material(Material.DIAMOND_SWORD)
				.build());
		categories.add(AchievementCategory.builder()
				.id("DUNGEON_PASS")
				.display("§6§oDungeony")
				.material(Material.SKELETON_SKULL)
				.build());
		categories.add(AchievementCategory.builder()
				.id("DISCOVER")
				.display("§7§oOdkrycia")
				.material(Material.FILLED_MAP)
				.build());
		categories.add(AchievementCategory.builder()
				.id("QUEST")
				.display("§2§oHistoria gry")
				.material(Material.WRITTEN_BOOK)
				.build());
		categories.add(AchievementCategory.builder()
				.id("LEVEL")
				.display("§e§oRozwoj postaci")
				.material(Material.EXPERIENCE_BOTTLE)
				.build());
		categories.add(AchievementCategory.builder()
				.id("CRAFTING")
				.display("§a§oRzemioslo")
				.material(Material.CRAFTING_TABLE)
				.build());
		categories.add(AchievementCategory.builder()
				.id("FISHING")
				.display("§9§oRybak")
				.material(Material.SALMON_BUCKET)
				.build());
		categories.add(AchievementCategory.builder()
				.id("MINING")
				.display("§8§oGornik")
				.material(Material.STONE_PICKAXE)
				.build());
	}
	
	public static final AchievementManager get() {
		return inst;
	}
	
	public void registerAchievement(Achievement achievement) {
		achievements.add(achievement);
	}
	
	public void registerCategory(AchievementCategory category) {
		categories.add(category);
	}
	
}
