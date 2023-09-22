package me.Vark123.EpicRPGAchievements;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

@Getter
public final class Config {

	private static final Config config = new Config();
	
	private String prefix;
	
	private Config() {
		
	}
	
	public static final Config get() {
		return config;
	}
	
	public void init() {
		File conf = FileManager.getConfig();
		if(!conf.exists())
			return;
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(conf);
		
		this.prefix = ChatColor.translateAlternateColorCodes('&', fYml.getString("prefix"));
	}
	
}
