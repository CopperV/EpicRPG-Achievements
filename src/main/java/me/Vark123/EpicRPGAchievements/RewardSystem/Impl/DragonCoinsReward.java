package me.Vark123.EpicRPGAchievements.RewardSystem.Impl;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPG.Core.CoinsSystem;
import me.Vark123.EpicRPG.Players.PlayerManager;
import me.Vark123.EpicRPG.Players.RpgPlayer;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Getter
@AllArgsConstructor
public class DragonCoinsReward implements IReward {

	private int amount;
	
	@Override
	public void giveReward(Player p) {
		RpgPlayer rpg = PlayerManager.getInstance().getRpgPlayer(p);
		CoinsSystem.getInstance().addCoins(rpg, amount, "achievement");
//		rpg.getVault().addDragonCoins(amount);
//		p.sendMessage("§7["+Config.get().getPrefix()+"§7] §aOtrzymales §4§o"+amount+" §asmoczych monet");
	}

	@Override
	public String asString() {
		return "  §4§l» §4§o"+amount+" smoczych monet";
	}

}
