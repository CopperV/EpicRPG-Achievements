package me.Vark123.EpicRPGAchievements.RewardSystem.Impl;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPG.Players.PlayerManager;
import me.Vark123.EpicRPG.Players.RpgPlayer;
import me.Vark123.EpicRPGAchievements.Config;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Getter
@AllArgsConstructor
public class MoneyReward implements IReward {

	private double amount;
	
	@Override
	public void giveReward(Player p) {
		RpgPlayer rpg = PlayerManager.getInstance().getRpgPlayer(p);
		rpg.getVault().addMoney(amount);
		p.sendMessage("§7["+Config.get().getPrefix()+"§7] §aOtrzymales §e§o"+String.format("%.2f", amount)+"$");
	}

	@Override
	public String asString() {
		return "  §4§l» §e§o"+String.format("%.2f", amount)+"$";
	}

}
