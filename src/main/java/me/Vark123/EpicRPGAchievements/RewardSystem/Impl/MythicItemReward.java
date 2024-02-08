package me.Vark123.EpicRPGAchievements.RewardSystem.Impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.bukkit.MythicBukkit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPG.Utils.Utils;
import me.Vark123.EpicRPGAchievements.RewardSystem.IReward;

@Getter
@AllArgsConstructor
public class MythicItemReward implements IReward {

	private String item;
	private int amount;
	
	@Override
	public void giveReward(Player p) {
		ItemStack it = MythicBukkit.inst().getItemManager().getItemStack(item);
		if(it == null)
			return;
		it.setAmount(amount);
		Utils.dropItemStack(p, it);
		p.sendMessage("§aOtrzymano §7§o"+amount+" "+it.getItemMeta().getDisplayName());
	}

	@Override
	public String asString() {
		ItemStack it = MythicBukkit.inst().getItemManager().getItemStack(item);
		if(it == null)
			return " ";
		return "  §7§l» §7§o"+amount+"x "+it.getItemMeta().getDisplayName();
	}

}
