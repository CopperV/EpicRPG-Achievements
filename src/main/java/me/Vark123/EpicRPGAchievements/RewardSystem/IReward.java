package me.Vark123.EpicRPGAchievements.RewardSystem;

import org.bukkit.entity.Player;

public interface IReward {

	public void giveReward(Player p);
	public String asString();
	
}
