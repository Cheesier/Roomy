package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerEvent;
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

@SuppressWarnings("unused")
public class RoomyPlayerListener extends PlayerListener {
	private Roomy plugin = null;

	public RoomyPlayerListener(Roomy instance) {
		this.plugin = instance;
	}

	// Start a timer on login to check what rooms you are in
	public void onPlayerJoin(PlayerEvent event) {
		RoomyLibrary.startCheckTimer(event.getPlayer());
	}

	// Stop the timer as the player logs out or waste cpu
	public void onPlayerQuit(PlayerEvent event) {
		Roomy.checkTimers.remove(event.getPlayer());
	}

	// Leftclick marking a position (Position 1)
	public void onPlayerAnimation(PlayerAnimationEvent event) {
		Player player = event.getPlayer();

		if (PlayerAnimationType.ARM_SWING == event.getAnimationType()) {
			if (Roomy.roomySetting.containsKey(player) && Roomy.roomySetting.get(player) == true) {
				if (player.getItemInHand().getTypeId() == 271) {
					Block block = event.getPlayer().getTargetBlock(null, 4);

					// We don't care about air and non-blocks
					if (!(block instanceof Block) || block.getTypeId() == 0) {
						return;
					}
					player.sendMessage("¤eFirst point of room set");

					Roomy.preSaved1.put(player,block.getLocation().toVector());
				}
			}
		}
	}
}

