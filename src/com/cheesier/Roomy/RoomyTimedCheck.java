package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.entity.Player;

public class RoomyTimedCheck implements Runnable {
	private Player player = null;
	
	RoomyTimedCheck(Player player) {
		this.player = player;
	}
	
	public void run() {
		if (Roomy.track) {
			addLastRoom(player);
		}
    }
	
	public static void addLastRoom(Player player) {
		List<String> inRooms = RoomyLibrary.getRoomsIn(player);
		if (!Roomy.lastRoom.containsKey(player)) { // add players last room
			Roomy.lastRoom.put(player, inRooms);
		}
		else {
			if (Roomy.lastRoom.get(player).hashCode() != inRooms.hashCode()) {
				Roomy.lastRoom.put(player, inRooms);
				
				RoomyEvent event = new RoomyEvent(RoomyEvent.Reason.ROOM_CHANGE, player, inRooms);
				Roomy.plugin.getServer().getPluginManager().callEvent(event);
			}
		}
	}
}
