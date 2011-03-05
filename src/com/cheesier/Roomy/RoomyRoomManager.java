package com.cheesier.Roomy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class RoomyRoomManager {
	private static Logger log = Logger.getLogger("Minecraft");

	// Say something like [Roomy] before we try talk to the commandline
	private static String infoName = "[" + Roomy.plugin.getDescription().getName() + "]";
	
	// Returns the player of a specified string
	public static Player getPlayerFromName(String playerName) {
		List<Player> players = Roomy.plugin.getServer().matchPlayer(playerName);
		// if not just one player popped up, say so :)
		return (players.size() == 1 ? players.get(0) : null);
	}
	
	// Gets the players vector
	public static Vector getPlayerVector(Player player) {
		player.getLocation();
		Location l = player.getLocation();
		return new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}
	
	
	// Gets a list of all the rooms a given player is inside of
	public static List<String> getRoomsIn(Player player) {		
		List<String> roomsInside = new ArrayList<String>();
		Iterator<SavedRoom> it = Roomy.savedRooms.iterator();
		
		while(it.hasNext()) {
			SavedRoom sv = it.next();
			if (sv.isInAABB(getPlayerVector(player))) {
				if (!roomsInside.contains(sv.getName())) {
					roomsInside.add(sv.getName().toLowerCase());
				}
			}
		}
		return roomsInside;
	}
	
	
	public static boolean isInRoom(Player player, String room) {
		if (getRoomsIn(player).contains(room.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	
	public static List<String> getAllRooms() {
		List<String> rooms = new ArrayList<String>();
		Iterator<SavedRoom> it = Roomy.savedRooms.iterator();
		
		while(it.hasNext()) {
			SavedRoom sv = it.next();
			if (!rooms.contains(sv.getName())) {
				rooms.add(sv.getName());
			}
		}
		return rooms;
	}
	
	
	public static String stringlistToString(List<String> list, boolean color) {
		Iterator<String> it = list.iterator();
		String output = "";
		Random rand = new Random(); // the randomization for colorcodes
		
		if (color) {
			while (it.hasNext()) {
				String clr = "¤" + Integer.toHexString(rand.nextInt(15)+1);
				output += clr + it.next() + ", ";
			}
		}
		else {
			while (it.hasNext()) {
				output += it.next() + ", ";
			}
		}
		if (output.length() > 2)
			output = output.substring( 0, output.length() - 2 ); // remove the 2 last chars ", "
		
		return output;
	}
}
