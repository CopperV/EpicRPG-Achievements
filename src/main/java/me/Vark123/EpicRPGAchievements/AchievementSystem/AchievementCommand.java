package me.Vark123.EpicRPGAchievements.AchievementSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGAchievements.MenuSystem.AchievementMenuManager;

public class AchievementCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("osiagniecia"))
			return false;
		if(!(sender instanceof Player))
			return false;
		
		Player p = (Player) sender;
		AchievementMenuManager.get().openCategoryMenu(p);
		return true;
	}

}
