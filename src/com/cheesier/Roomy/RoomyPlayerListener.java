package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author Cheesier
 */


/*
 * Much nicer code now that all the commands moved outta here
 * int timeId = getServer().getScheduler().scheduleSyncRepeatingTask(this, new RoomyTimedCheck(), 200, 200);
 */

@SuppressWarnings("unused")
public class RoomyPlayerListener extends PlayerListener {
	private Roomy plugin = null;

	public RoomyPlayerListener(Roomy instance) {
		this.plugin = instance;
	}
	
	
	public void onPlayerJoin(PlayerEvent event) {
		RoomyRoomManager.startCheckTimer(event.getPlayer());
	}
	
	public void onPlayerQuit(PlayerEvent event) {
		Roomy.checkTimers.remove(event.getPlayer());
	}
}

