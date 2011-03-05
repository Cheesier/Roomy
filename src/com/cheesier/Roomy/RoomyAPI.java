package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RoomyAPI {
	
	// Returns the player of a specified string
	public Player getPlayerFromName(String playerName) {
		return RoomyRoomManager.getPlayerFromName(playerName);
	}
	
	// Gets the players vector
	public Vector getPlayerVector(Player player) {
		return RoomyRoomManager.getPlayerVector(player);
	}
	
	// Gets a list of all the rooms a given player is inside of
	public List<String> getRoomsIn(Player player) {		
		return RoomyRoomManager.getRoomsIn(player);
	}
	
	
	public boolean isInRoom(Player player, String room) {
		return RoomyRoomManager.isInRoom(player, room);
	}
	
	
	public List<String> getAllRooms() {
		return RoomyRoomManager.getAllRooms();
	}
	
	
	public String stringlistToString(List<String> list, boolean color) {
		return RoomyRoomManager.stringlistToString(list, color);
	}
}
