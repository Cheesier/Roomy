package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author Cheesier
 */


/*
 * Much nicer code now that all the commands moved outta here
 * 
 */


public class RoomyPlayerListener extends PlayerListener {

	public RoomyPlayerListener(Roomy instance) {
		
	}



	public void onPlayerMove(PlayerMoveEvent event)  {
		Player player = event.getPlayer();
		Location to = event.getTo();
		Location from = event.getFrom();

		int toX = to.getBlockX();
		int toY = to.getBlockY();
		int toZ = to.getBlockZ();

		int fromX = from.getBlockX();
		int fromY = from.getBlockY();
		int fromZ = from.getBlockZ();

		if (toX != fromX || toY != fromY || toZ != fromZ) { // trigger on passing a block, not all the time
			if (Roomy.track) {
				addLastRoom(player);
			}
		}
		return;
	}
	
	
	public static void addLastRoom(Player player) {
		List<String> inRooms = RoomyRoomManager.getRoomsIn(player);
		if (!Roomy.lastRoom.containsKey(player)) { // add players last room
			Roomy.lastRoom.put(player, inRooms);
		}
		else {
			if (Roomy.lastRoom.get(player).hashCode() != inRooms.hashCode()) {
				Roomy.lastRoom.put(player, inRooms);
				if (inRooms.size() >= 1)
					player.sendMessage("Welcome to room: " + RoomyRoomManager.stringlistToString(inRooms, true));
			}
		}
	}
}

