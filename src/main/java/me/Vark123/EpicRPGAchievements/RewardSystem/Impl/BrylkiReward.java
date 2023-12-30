package me.Vark123.EpicRPGAchievements.RewardSystem.Impl;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPG.Core.RudaSystem;
import me.Vark123.EpicRPG.Players.PlayerManager;
import me.Vark123.EpicRPG.Players.RpgPlayer;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Getter
@AllArgsConstructor
public class BrylkiReward implements IReward {

	private int amount;
	
	@Override
	public void giveReward(Player p) {
		RpgPlayer rpg = PlayerManager.getInstance().getRpgPlayer(p);
		RudaSystem.getInstance().addRuda(rpg, amount, "achievement");
//		rpg.getVault().addBrylkiRudy(amount);
//		p.sendMessage("§7["+Config.get().getPrefix()+"§7] §aOtrzymales §9§o"+amount+" §abrylek rudy");
	}

	@Override
	public String asString() {
		return "  §4§l» §9§o"+amount+" brylek rudy";
	}

}
