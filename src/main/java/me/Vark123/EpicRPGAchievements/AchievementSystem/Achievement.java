package me.Vark123.EpicRPGAchievements.AchievementSystem;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.AccessLevel;
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
	
	@Getter(value = AccessLevel.NONE)
	private ItemStack item;
	
	public ItemStack toItem() {
		if(item != null)
			return item.clone();
		item = new ItemStack(Material.RED_TERRACOTTA);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(display);
		
		List<String> lore = new LinkedList<>(this.lore);
		lore.add(" ");
		lore.add("§6§l》 §c§nNAGRODA§6§l 《");
		rewards.forEach(reward -> lore.add(reward.asString()));
		im.setLore(lore);
		
		item.setItemMeta(im);
		return item.clone();
	}
	
}
