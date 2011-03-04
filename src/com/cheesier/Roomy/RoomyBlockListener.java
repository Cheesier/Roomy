package com.cheesier.Roomy;

import org.bukkit.block.Block;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.util.Vector;

/**
 * FirstBukkit block listener
 * @author Cheesier
 */
@SuppressWarnings("unused")
public class RoomyBlockListener extends BlockListener {
    private final Roomy plugin;
    
    public RoomyBlockListener(final Roomy plugin) {
    	this.plugin = plugin;
    }

    
    // Left click
    public void onBlockDamage(BlockDamageEvent event) {
    	Player player = event.getPlayer();

    	if (Roomy.roomSetting.containsKey(player) && Roomy.roomSetting.get(player) == true) {
    		
    		if (event.getDamageLevel().equals(BlockDamageLevel.STARTED)) { // as a player starts hitting a block
    			if (player.getItemInHand().getTypeId() == 271) {
    				player.sendMessage("¤eFirst point of room set");

    				Roomy.preSaved1.put(player, event.getBlock().getLocation().toVector());
    			}
    		}
    	}
    }

    
    // Right click
    public void onBlockRightClick(BlockRightClickEvent event) {
    	Player player = event.getPlayer();
    	
    	if (Roomy.roomSetting.containsKey(player) && Roomy.roomSetting.get(player) == true) {
	    	if (player.getItemInHand().getTypeId() == 271) {
	    		player.sendMessage("¤eSecond point of room set");
	    		
	    		Roomy.preSaved2.put(player, event.getBlock().getLocation().toVector());
	    	}
    	}
    }
}
