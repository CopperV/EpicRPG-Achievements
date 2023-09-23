package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;
import me.Vark123.EpicRPGFishing.KhorinisFishing.Events.KoloniaFishEvent;

public class FishingListener implements Listener {

	@EventHandler
	public void onFish(KoloniaFishEvent e) {
		if(e.isCancelled())
			return;
		
		Player p = e.getPlayer();
		ItemStack it = e.getFish();
		NBTItem nbt = new NBTItem(it);
		if(!nbt.hasTag("MYTHIC_TYPE"))
			return;
		String result = nbt.getString("MYTHIC_TYPE");
		
		PlayerAchievementsManager.get().getPlayerAchievements(p)
			.ifPresent(pa -> {
				AchievementManager.get().getAchievementsByTarget(result)
					.stream()
					.filter(achievement -> achievement.getCategory().getId().equals("FISHING"))
					.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
					.forEach(achievement -> pa.updateAchievement(achievement, 1));
			});
	}
	
}
