package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.entity.Player;

public class RoomyTimedCheck implements Runnable {
	private Player player = null;
	
	RoomyTimedCheck(Player player) {
		this.player = player;
		System.out.println("Ran!");
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
				if (inRooms.size() >= 1)
					player.sendMessage("Welcome to room: " + RoomyLibrary.stringlistToString(inRooms, true));
			}
		}
	}
}
