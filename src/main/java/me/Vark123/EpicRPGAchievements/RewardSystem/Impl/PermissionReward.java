package me.Vark123.EpicRPGAchievements.RewardSystem.Impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Getter
@AllArgsConstructor
public class PermissionReward implements IReward {

	private String perm;
	private int amount;
	
	@Override
	public void giveReward(Player p) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+p.getName()+" permission settemp "+perm+" true "+amount+"h accumulate");
		p.sendMessage("§aOtrzymano uprawnienia §7§o"+perm+" §ana §7§o"+amount+" §agodzin");
	}

	@Override
	public String asString() {
		return "  §4§l» §c§oUprawnienia "+perm+" na "+amount+" godzin";
	}

}
