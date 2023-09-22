package me.Vark123.EpicRPGAchievements.MenuSystem;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.mutable.MutableInt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import me.Vark123.EpicRPGAchievements.Main;
import me.Vark123.EpicRPGAchievements.AchievementSystem.Achievement;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementCategory;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievements;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;

public final class AchievementMenuManager {

	private static final AchievementMenuManager inst = new AchievementMenuManager();
	
	private final ItemStack previous;
	private final ItemStack next;
	
	private final ItemStack empty;
	private final ItemStack back;
	
	private final InventoryProvider categoryProvider;
	
	private final int ITEMS_PER_PAGE = 9*4;
	
	private AchievementMenuManager() {
		previous = new ItemStack(Material.ARROW);{
			ItemMeta im = previous.getItemMeta();
			im.setDisplayName("§fPoprzednia");
			previous.setItemMeta(im);
		}
		next = new ItemStack(Material.ARROW);{
			ItemMeta im = next.getItemMeta();
			im.setDisplayName("§fNastepna");
			next.setItemMeta(im);
		}

		empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);{
			ItemMeta im = empty.getItemMeta();
			im.setDisplayName(" ");
			empty.setItemMeta(im);
		}
		back = new ItemStack(Material.BARRIER);{
			ItemMeta im = back.getItemMeta();
			im.setDisplayName("§cPowrot");
			back.setItemMeta(im);
		}
		
		categoryProvider = new InventoryProvider() {
			@Override
			public void init(Player player, InventoryContents contents) {
				MutableInt slot = new MutableInt();
				AchievementManager.get().getCategories().forEach(category -> {
					contents.set(slot.getAndIncrement(), IntelligentItem.of(category.getItem(), click -> {
						openAchievementsMenu(player, category, 0);
					}));
				});
			}
		};
	}
	
	public static final AchievementMenuManager get() {
		return inst;
	}
	
	public void openCategoryMenu(Player p) {
		RyseInventory.builder()
			.title("§2§lTWOJE OSIAGNIECIA")
			.rows(1)
			.disableUpdateTask()
			.provider(categoryProvider)
			.build(Main.getInst())
			.open(p);
	}
	
	public void openAchievementsMenu(Player p, AchievementCategory category, int page) {
		RyseInventory.builder()
			.title("§2§lTWOJE OSIAGNIECIA §7[§r"+category.getDisplay()+"§7]")
			.rows(6)
			.disableUpdateTask()
			.provider(getAchievementProvider(p, category, page))
			.build(Main.getInst())
			.open(p);
	}
	
	private InventoryProvider getAchievementProvider(Player p, AchievementCategory category, int page) {
		return new InventoryProvider() {
			@Override
			public void init(Player player, InventoryContents contents) {
				for(int i = 0; i < 9; ++i) {
					contents.set(i, empty);
					contents.set(i+45, empty);
				}
				
				contents.set(4, category.getItem());
				contents.set(8, IntelligentItem.of(back, click -> openCategoryMenu(p)));
				
				List<Achievement> achievements = AchievementManager.get().getAchievements()
						.stream()
						.filter(av -> av.getCategory().equals(category))
						.collect(Collectors.toList());
				
				int start = page * ITEMS_PER_PAGE;
				int end = (page+1) * ITEMS_PER_PAGE;
				if(start < 0)
					start = 0;
				if(end > achievements.size())
					end = achievements.size();
				
				ItemStack pageInfo = new ItemStack(Material.PAPER);{
					ItemMeta im = pageInfo.getItemMeta();
					im.setDisplayName("§f§lStrona "+(page+1));
					pageInfo.setItemMeta(im);
				}
				contents.set(49, pageInfo);
				
				PlayerAchievements pa = PlayerAchievementsManager.get().getPlayerAchievements(p).get();
				if(page > 0) 
					contents.set(47, IntelligentItem.of(previous, click -> openAchievementsMenu(p, category, page-1)));
				if(end < achievements.size())
					contents.set(51, IntelligentItem.of(next, click -> openAchievementsMenu(p, category, page+1)));
				
				for(int i = 0; i < (end-start); ++i) {
					Achievement achievement = achievements.get(i+start);
					ItemStack it = achievement.toItem();
					
					if(pa.getCompletedAchievements().contains(achievement.getId()))
						it.setType(Material.GREEN_TERRACOTTA);
					else if(pa.getAchievementsInProgress().containsKey(achievement)) {
						int progress = pa.getAchievementsInProgress().get(achievement);
						double percent = ((double) progress) / ((double) achievement.getAmount()) * 100.;
						
						ItemMeta im = it.getItemMeta();
						List<String> lore = im.getLore();
						lore.add(" ");
						lore.add("§8§lPOSTEP: §7§o"+String.format("%.2f", percent)+"%");
						im.setLore(lore);
						it.setItemMeta(im);
					}
					
					contents.set(i+9, it);
				}
			}
		};
	}
	
}
