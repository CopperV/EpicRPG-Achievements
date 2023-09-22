package me.Vark123.EpicRPGAchievements.AchievementSystem;

import java.util.Collection;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Builder
@Getter
@EqualsAndHashCode
public class Achievement {

	private AchievementCategory category;
	
	private String id;
	private String target;
	private int amount;
	private boolean hidden;
	
	public String display;
	public List<String> lore;
	
	public Collection<IReward> rewards;
	
}
