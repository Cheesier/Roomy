package net.cheesier.Roomy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.util.Vector;
import org.bukkit.util.config.Configuration;


/* 
 * Rewritten the save and load function to work with bukkit configsystem
 * 
 * TODO Comments seems to disappear :O
 */

public class RoomyFileManager {
	public static String savefile = "plugins/Roomy/rooms.txt";
	private static Logger log = Logger.getLogger("Minecraft");
	public static int desiredPara = 7;
	public static Configuration config = Roomy.config;

	
	public static int load() {
		// reload the data from the file
		config.load();
		List<String> loadedRooms = config.getStringList("rooms", null);
		if (loadedRooms == null) {
			return -1;
		}
		Roomy.savedRooms.clear();
		Iterator<String> lit = loadedRooms.iterator();
		int lineNum = 0, roomsLoaded = 0;
		
		while(lit.hasNext()) {
			lineNum ++;
			String[] s = lit.next().split(":");
			if (s.length == desiredPara) {
				roomsLoaded++;
				// get the list String into the 2 vectors that makes a room
				Vector v1 = new Vector(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
				Vector v2 = new Vector(Integer.parseInt(s[4]), Integer.parseInt(s[5]), Integer.parseInt(s[6]));
				
				// Make the actual saved room, s[0] is the name
				Roomy.savedRooms.add(new SavedRoom(s[0], v1, v2));
			}
			else {
				/*
				 * In the case that a parameter is missing or something, we dont want errors :)
				 * Inform user that a room was broken
				 */
				if (s[0].length() >= 1) {
					log.warning("[Roomy] found a faulty room \"" + s[0] + "\"");
				}
				else {
					log.warning("[Roomy] found a faulty room in the list at position: " + lineNum);
				}
			}
		}
		// tell the caller how many rooms we loaded/tried to load
		return roomsLoaded;
	}
	
	
	
	public static int save() {
		List<String> StringRooms = new ArrayList<String>();
		Iterator<SavedRoom> srit = Roomy.savedRooms.iterator();
		
		while(srit.hasNext()) {
			StringRooms.add(srit.next().toSaveFormat());
		}
		config.setProperty("rooms", StringRooms);
		
		config.save();
		return StringRooms.size();
	}
	
	
	
	// Delete a room
	// Works, happy with it
	public static int deleteRoom(String name) {
		int del = 0;		
		Iterator<SavedRoom> rmit = Roomy.savedRooms.iterator();
		
		while(rmit.hasNext()) {
			SavedRoom sv = rmit.next();
			if (sv.getName().equalsIgnoreCase(name)) {
				int index = Roomy.savedRooms.indexOf(sv);
				if (index >=0) {
					rmit.remove();
					del++;
				}
			}
		}
		return del;
	} // end deleteRoom()
}
