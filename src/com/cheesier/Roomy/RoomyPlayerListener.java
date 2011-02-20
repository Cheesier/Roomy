package com.cheesier.Roomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Handle events for all Player related events
 * @author Cheesier
 */

public class RoomyPlayerListener extends PlayerListener {
	//@SuppressWarnings("unused")
	private final Roomy plugin;
	//private Server server;
	//private World world;

	public RoomyPlayerListener(Roomy instance) {
		plugin = instance;
		//server = plugin.getServer();
		//world = server.getWorld("world");
	}



	//@SuppressWarnings("unused")
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
		List<String> inRooms = getRoomsIn(player);
		if (!Roomy.lastRoom.containsKey(player)) { // add players last room
			Roomy.lastRoom.put(player, inRooms);
		}
		else {
			if (Roomy.lastRoom.get(player).hashCode() != inRooms.hashCode()) {
				Roomy.lastRoom.put(player, inRooms);
				if (inRooms.size() >= 1)
					player.sendMessage("Welcome to room: " + inRooms.get(0));
			}
		}
	}
	
	
	public static List<String> getRoomsIn(Player player) {
		player.getLocation();
		Location l = player.getLocation();
		Vector playerVector = new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ());
		
		List<String> rooms = new ArrayList<String>();
		Iterator<SavedRoom> it = Roomy.savedRooms.iterator();
		
		while(it.hasNext()) {
			SavedRoom sv = it.next();
			if (sv.isInAABB(playerVector)) {
				if (!rooms.contains(sv.getName())) {
					rooms.add(sv.getName());
				}
			}
		}
		return rooms;
	}



	//@SuppressWarnings("static-access")
	public void onPlayerCommand(PlayerChatEvent event) {
		String[] split = event.getMessage().split(" ");
		Player player = event.getPlayer();

		// /setroom
		if (split[0].equalsIgnoreCase("/setroom")) {
			if (Roomy.Permissions.has(player, "Roomy.manage.set")) {
				HashMap<Player, Vector> pre1 = Roomy.preSaved1;
				HashMap<Player, Vector> pre2 = Roomy.preSaved2;

				if (split.length == 2) {
					if (pre1.containsKey(player) && pre1.containsKey(player)) {
						//int id = Roomy.savedRooms.size();
						Roomy.savedRooms.add(new SavedRoom(split[1], pre1.get(player), pre2.get(player)));
						player.sendMessage("Saved room: " + split[1]);
					}
					else { // player do not have 2 positions of the room
						player.sendMessage("Set 2 positions first, with the wand tool");
					}
				}
				else { // wrong number of arguments
					player.sendMessage("Usage is: /saveroom [name] - save the room :)");
				}
			}
			event.setCancelled(true);
		}
		
		else if (split[0].equalsIgnoreCase("/track")) {
			if (Roomy.Permissions.has(player, "Roomy.tool")) {
				Roomy.track = !Roomy.track;
				
				if (Roomy.track) {
					plugin.getServer().broadcastMessage("Tracking ¤2enabled");
				}
				else {
					plugin.getServer().broadcastMessage("Tracking ¤cdisabled");
				}
			}
		}
		
		else if (split[0].equalsIgnoreCase("/roomy")) {
			if (Roomy.Permissions.has(player, "Roomy.tool")) {

				if (!Roomy.roomSetting.containsKey(player)) { // add user
					Roomy.roomSetting.put(player, true);
					player.sendMessage("RoomTool ¤2enabled¤f, uses same item as WorldEdit(Wooden axe) id: 271");
				}else{
					boolean newVal = !Roomy.roomSetting.get(player);
					Roomy.roomSetting.put(player, newVal);
					
					if (newVal) {
						player.sendMessage("RoomyTool ¤2enabled¤f, item id 271 (Wooden axe)");
					}
					else {
						player.sendMessage("RoomyTool ¤cdisabled");
					}
				}
			}
			else {
				player.sendMessage("¤cYou do not have the rights to use this command.");
			}
			event.setCancelled(true);
		}

		// /removeroom
		else if (split[0].equalsIgnoreCase("/removeroom")) {
			if (Roomy.Permissions.has(player, "Roomy.manage.delete")) {
				if (split.length >= 2) {
					int deleted = RoomyFileManager.deleteRoom(split[1], player.getName());
					if (deleted > 1) {
						player.sendMessage("Deleted " + deleted + " parts of " + split[1]);
					}
					else if (deleted == 1) {
						player.sendMessage("Deleted room " + split[1]);
					}
					else {
						player.sendMessage("Room not found.");
					}
				}
				else {
					player.sendMessage("Usage is: /removeroom [name] - removes all the parts of a room");
				}
			}
			event.setCancelled(true);
		}

		else if (split[0].equalsIgnoreCase("/saverooms")) {
			if (Roomy.Permissions.has(player, "Roomy.manage.save")) {
				RoomyFileManager.save();
				player.sendMessage("Saved.");
			}
			event.setCancelled(true);
		}
		
		else if (split[0].equalsIgnoreCase("/loadrooms")) {
			if (Roomy.Permissions.has(player, "Roomy.manage.load")) {
				RoomyFileManager.load();
				player.sendMessage("Loaded.");
			}
			event.setCancelled(true);
		}

		// /listrooms
		else if (split[0].equalsIgnoreCase("/listrooms")) {
			if (Roomy.Permissions.has(player, "Roomy.use.list")) {
				Iterator<SavedRoom> it = Roomy.savedRooms.iterator();
				//String rooms = "";
				List<String> rooms = new ArrayList<String>();
				while(it.hasNext()) {
					String name = it.next().getName();
					if (!rooms.contains(name))
					rooms.add(name);
				}
				
				Iterator<String> roomit = rooms.iterator();
				//int perLine = 5;
				//int lines = (int)Math.ceil(rooms.size() / perLine);
				player.sendMessage(ChatColor.RED + "Room's defined: ");
				String tell = "";
				Random rand = new Random();
				//String clr = Integer.toHexString(rand.nextInt(16));
				String clr = "";
				while(roomit.hasNext()) {
						clr = Integer.toHexString(rand.nextInt(15)+1);
						tell += "¤" + clr + roomit.next() + " ";
					}
					player.sendMessage(tell);
				/*if (rooms.length() > 0) {
					player.sendMessage("Existing rooms: " + rooms.substring( 0, rooms.length() - 2 ));
				}
				else {
					player.sendMessage("No rooms defined");
				}*/
			}
			event.setCancelled(true);
		}

		// /inside
		else if (split[0].equalsIgnoreCase("/inside")) {
			if (Roomy.Permissions.has(player, "Roomy.use.inside")) {
				player.getLocation();
				Location l = player.getLocation();
				Vector playerVector = new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ());

				Iterator<SavedRoom> it = Roomy.savedRooms.iterator();
				String inside = "";

				while(it.hasNext())
				{
					SavedRoom sv = it.next();
					if (sv.isInAABB(playerVector)) {
						inside += sv.getName() + ", ";
						//System.out.println(sv.toString());
					}
				}
				if (inside.length() > 0) {
					player.sendMessage("Inside of room: " + inside.substring( 0, inside.length() - 2 ));
				}
				else {
					player.sendMessage("You are not in a defined room");
				}
			}
			event.setCancelled(true);
		}
	}
}

