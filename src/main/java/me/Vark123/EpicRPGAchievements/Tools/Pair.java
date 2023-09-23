package me.Vark123.EpicRPGAchievements.Tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "value")
public class Pair <K, V> {
	
	private K key;
	private V value;

}
