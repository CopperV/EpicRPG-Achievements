package me.Vark123.EpicRPGAchievements.RewardSystem.Impl;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPG.Core.ExpSystem;
import me.Vark123.EpicRPG.Players.PlayerManager;
import me.Vark123.EpicRPG.Players.RpgPlayer;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Getter
@AllArgsConstructor
public class XpReward implements IReward {

	private int amount;
	
	@Override
	public void giveReward(Player p) {
		RpgPlayer rpg = PlayerManager.getInstance().getRpgPlayer(p);
		ExpSystem.getInstance().addExp(rpg, amount, "achievement");
//		rpg.getInfo().addXP(amount);
//		p.sendMessage("§7["+Config.get().getPrefix()+"§7] §aOtrzymales §6§o"+amount+" §apunktow doswiadczenia");
	}

	@Override
	public String asString() {
		return "  §4§l» §6§o"+amount+" xp";
	}

}
