package net.cheesier.Roomy;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RoomyAPI {
	
	// Returns the player of a specified string
	public Player getPlayerFromName(String playerName) {
		return RoomyLibrary.getPlayerFromName(playerName);
	}
	
	// Gets the players vector
	public Vector getPlayerVector(Player player) {
		return RoomyLibrary.getPlayerVector(player);
	}
	
	
	// Gets the block vector
	public Vector getBlockVector(Block block) {
		return RoomyLibrary.getBlockVector(block);
	}
	
	// Gets a list of all the rooms a given player is inside of
	public List<String> getRoomsIn(Player player) {		
		return RoomyLibrary.getRoomsIn(player);
	}
	
	
	public boolean isInRoom(Player player, String room) {
		return RoomyLibrary.isInRoom(player, room);
	}
	
	public boolean isInRoom(Block block, String room) {
		return RoomyLibrary.isInRoom(block, room);
	}
	
	
	public List<String> getAllRooms() {
		return RoomyLibrary.getAllRooms();
	}
	
	
	public String stringlistToString(List<String> list, boolean color) {
		return RoomyLibrary.stringlistToString(list, color);
	}
}
