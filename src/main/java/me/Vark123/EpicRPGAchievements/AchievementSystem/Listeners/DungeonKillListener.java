package me.Vark123.EpicRPGAchievements.AchievementSystem.Listeners;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import io.lumine.mythic.bukkit.events.MythicMobDespawnEvent;
import me.Vark123.EpicRPG.FightSystem.Modifiers.DamageModifier;
import me.Vark123.EpicRPGAchievements.AchievementSystem.AchievementManager;
import me.Vark123.EpicRPGAchievements.PlayerSystem.PlayerAchievementsManager;
import me.Vark123.EpicRPGAchievements.Tools.Pair;

public class DungeonKillListener implements Listener, DamageModifier {

	private static Map<Entity, List<Pair<Player, Double>>> damageCounters = new ConcurrentHashMap<>();
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		LivingEntity victim = e.getEntity();
		if(victim instanceof Player)
			return;
		if(!damageCounters.containsKey(victim))
			return;
		
		List<Player> players = new LinkedList<>();
		Player p = victim.getKiller();
		if(p != null)
			players.add(p);
		
		double maxHp = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		double percent = maxHp * 0.05;
		damageCounters.get(victim).stream()
			.filter(pair -> !pair.getKey().equals(p))
			.filter(pair -> pair.getValue() >= percent)
			.map(pair -> pair.getKey())
			.forEach(players::add);
		
		String result = victim.getName();
		players.forEach(_p -> {
			PlayerAchievementsManager.get().getPlayerAchievements(_p)
				.ifPresent(pa -> {
					AchievementManager.get().getAchievementsByTarget(result)
						.stream()
						.filter(achievement -> achievement.getDifficulty().isPresent())
						.filter(achievement -> {
							String world = victim.getWorld().getName().toLowerCase();
							switch(achievement.getDifficulty().get().toLowerCase()) {
								case "normal":
									return !(world.contains("heroic") || world.contains("mythic"));
								case "heroic":
									return world.contains("heroic");
								case "mythic":
									return world.contains("mythic");
								default:
									return false;
							}
						})
						.filter(achievement -> achievement.getCategory().getId().equals("DUNGEON_KILL"))
						.filter(achievement -> !pa.getCompletedAchievements().contains(achievement.getId()))
						.forEach(achievement -> pa.updateAchievement(achievement, 1));
				});
		});
		damageCounters.remove(victim);
	}
	
	@EventHandler
	public void onDespawn(MythicMobDespawnEvent e) {
		Entity entity = e.getEntity();
		damageCounters.remove(entity);
	}
	
	@Override
	public double modifyDamage(Entity damager, Entity victim, double damage, DamageCause cause) {
		if(victim instanceof Player)
			return damage;
		if(cause.equals(DamageCause.PROJECTILE))
			damager = (Entity) ((Projectile) damager).getShooter();
		if(!(damager instanceof Player))
			return damage;
		
		List<Pair<Player, Double>> players = damageCounters.getOrDefault(victim, new LinkedList<>());
		Player p = (Player) damager;
		
		players.stream()
			.filter(pair -> pair.getKey().equals(p))
			.findAny()
			.ifPresentOrElse(pair -> {
				players.remove(pair);
				players.add(new Pair<>(p, pair.getValue()+damage));
			}, () -> {
				players.add(new Pair<>(p, damage));
			});
		
		damageCounters.put(victim, players);
		return damage;
	}

}
