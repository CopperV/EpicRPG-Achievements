package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;
import me.Vark123.EpicRPGGornik.PlayerSystem.Events.OreDropEvent;

public class MiningListener implements Listener {

	@EventHandler
	public void onMining(OreDropEvent e) {
		if(e.isCancelled())
			return;
		
		Player p = e.getMiner().getPlayer();
		ItemStack it = e.getItemOre();
		NBTItem nbt = new NBTItem(it);
		if(!nbt.hasTag("MYTHIC_TYPE"))
			return;
		String result = nbt.getString("MYTHIC_TYPE");
		
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				AchievementManager.get().getAchievementsByTarget(result)
					.stream()
					.filter(achievement -> achievement.getCategory().getId().equals("MINING"))
					.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
					.forEach(achievement -> pa.updateAchievement(achievement, 1));
			});
	}
	
}
