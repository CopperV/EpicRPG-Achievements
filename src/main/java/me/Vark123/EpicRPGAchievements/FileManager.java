package me.Vark123.EpicRPGAchievements;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Achievement;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievements;
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
										.category(category)
										.build();
								AchievementManager.get().registerAchievement(achievement);
							});
					});
			});
		AchievementManager.get().getAchievements().sort((av1, av2) -> av1.getId().compareTo(av2.getId()));
		AchievementManager.get().getCategories().sort((cat1, cat2) -> cat1.getId().compareTo(cat2.getId()));
	}
	
	public static PlayerAchievements loadPlayerAchievements(Player p) {
		UUID uid = p.getUniqueId();
		File pFile = new File(playerDir, uid.toString()+".yml");
		if(!pFile.exists()) {
			try {
				pFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return PlayerAchievements.builder()
					.player(p)
					.achievementsInProgress(new ConcurrentHashMap<>())
					.completedAchievements(new LinkedList<>())
					.build();
		}
		
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(pFile);
		Collection<String> completedAchievements = fYml.getStringList("completed-achievements");
		Map<Achievement, Integer> achievementsInProgress = new ConcurrentHashMap<>();
		
		ConfigurationSection progressSection = fYml.getConfigurationSection("achievements-in-progress");
		if(progressSection != null) {
			progressSection.getKeys(false).stream()
				.map(id -> AchievementManager.get().getAchievements().stream()
						.filter(achievement -> achievement.getId().equals(id))
						.findAny())
				.filter(achievement -> achievement.isPresent())
				.map(achievement -> achievement.get())
				.forEach(achievement -> {
					int progress = progressSection.getInt(achievement.getId());
					achievementsInProgress.put(achievement, progress);
				});
		}
		
		return PlayerAchievements.builder()
				.player(p)
				.completedAchievements(completedAchievements)
				.achievementsInProgress(achievementsInProgress)
				.build();
	}
	
	public static void savePlayerAchievements(PlayerAchievements pa) {
		Player p = pa.getPlayer();
		UUID uid = p.getUniqueId();
		File pFile = new File(playerDir, uid.toString()+".yml");
		if(!pFile.exists())
			try {
				pFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(pFile);
		
		fYml.set("last-nick", p.getName());
		fYml.set("completed-achievements", pa.getCompletedAchievements());
		fYml.set("achievements-in-progress", null);
		
		pa.getAchievementsInProgress().entrySet().stream()
			.forEach(entry -> {
				String id = entry.getKey().getId();
				int progress = entry.getValue();
				fYml.set("achievements-in-progress."+id, progress);
			});
		
		try {
			fYml.save(pFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
