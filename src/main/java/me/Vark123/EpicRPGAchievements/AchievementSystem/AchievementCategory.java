package me.Vark123.EpicRPGAchievements.AchievementSystem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class AchievementCategory {

	private String id;
	
	private Material material;
	private String display;
	
	private ItemStack item;
	
	public ItemStack getItem() {
		if(item != null)
			return item;
		
		item = new ItemStack(material);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§f§lKategoria: §r"+display);
		item.setItemMeta(im);
		
		return item;
	}
	
}
