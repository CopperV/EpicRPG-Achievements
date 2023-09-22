package me.Vark123.EpicRPGAchievements;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Achievement;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;
import me.Vark123.EpicRPGAchievements.RewardSystem.Impl.BrylkiReward;
import me.Vark123.EpicRPGAchievements.RewardSystem.Impl.MoneyReward;
import me.Vark123.EpicRPGAchievements.RewardSystem.Impl.StygiaReward;
import me.Vark123.EpicRPGAchievements.RewardSystem.Impl.XpReward;

public final class FileManager {

	private static File achievementDir = new File(Main.getInst().getDataFolder(), "achievements");
	private static File playerDir = new File(Main.getInst().getDataFolder(), "players");
	
	@Getter
	private static File config = new File(Main.getInst().getDataFolder(), "config.yml");
	
	private FileManager() { }
	
	public static void init() { 
		if(!Main.getInst().getDataFolder().exists())
			Main.getInst().getDataFolder().mkdir();
		
		if(!achievementDir.exists())
			achievementDir.mkdir();
		
		if(!playerDir.exists())
			playerDir.mkdir();
		
		loadAchievements();
		
		Main.getInst().saveResource("config.yml", false);
		
		Config.get().init();
	}
	
	private static void loadAchievements() {
		if(!achievementDir.exists())
			return;
		Arrays.asList(achievementDir.listFiles()).stream()
			.filter(file -> file.isFile())
			.filter(file -> file.getName().endsWith(".yml"))
			.map(YamlConfiguration::loadConfiguration)
			.map(fYml -> fYml.getConfigurationSection("categories"))
			.filter(category -> category != null)
			.forEach(categorySection -> {
				categorySection.getKeys(false).stream()
					.map(categoryId -> AchievementManager.get().getCategories()
							.stream()
							.filter(category -> category.getId().equals(categoryId))
							.findAny())
					.filter(category -> category.isPresent())
					.map(category -> category.get())
					.forEach(category -> {
						ConfigurationSection achievementsSection = categorySection.getConfigurationSection(category.getId());
						achievementsSection.getKeys(false).stream()
							.map(key -> achievementsSection.getConfigurationSection(key))
							.filter(section -> section != null)
							.forEach(achievementSection -> {
								String id = achievementSection.getString("id");
								String target = ChatColor.translateAlternateColorCodes('&', achievementSection.getString("target"));
								int amount = achievementSection.getInt("amount");
								boolean hidden = achievementSection.getBoolean("hide");
								String display = ChatColor.translateAlternateColorCodes('&', achievementSection.getString("display"));
								List<String> lore = achievementSection.getStringList("lore").stream()
										.map(line -> ChatColor.translateAlternateColorCodes('&', line))
										.collect(Collectors.toList());
								
								List<IReward> rewards = new LinkedList<>();
								ConfigurationSection rewardsSection = achievementSection.getConfigurationSection("rewards");
								if(rewardsSection != null) {
									rewardsSection.getKeys(false).stream()
										.forEach(rewardType -> {
											switch(rewardType.toUpperCase()) {
												case "XP":
													rewards.add(new XpReward(rewardsSection.getInt(rewardType+".amount")));
													break;
												case "MONEY":
													rewards.add(new MoneyReward(rewardsSection.getDouble(rewardType+".amount")));
													break;
												case "STYGIA":
													rewards.add(new StygiaReward(rewardsSection.getInt(rewardType+".amount")));
													break;
												case "COINS":
													rewards.add(new MoneyReward(rewardsSection.getInt(rewardType+".amount")));
													break;
												case "BRYLKI":
													rewards.add(new BrylkiReward(rewardsSection.getInt(rewardType+".amount")));
													break;
											}
										});
								}
								
								Achievement achievement = Achievement.builder()
										.id(id)
										.target(target)
										.amount(amount)
										.hidden(hidden)
										.display(display)
										.lore(lore)
										.rewards(rewards)
										.build();
								AchievementManager.get().registerAchievement(achievement);
							});
					});
			});
	}
	
}
