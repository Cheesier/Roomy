package com.cheesier.Roomy;

import org.bukkit.block.Block;
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
public class RoomMapperBlockListener extends BlockListener {
    private final RoomMapper plugin;

    public RoomMapperBlockListener(final RoomMapper plugin) {
    	this.plugin = plugin;
    }


    public void onBlockDamage(BlockDamageEvent event) {
    	Player player = event.getPlayer();

    	if (RoomMapper.roomSetting.containsKey(player) && RoomMapper.roomSetting.get(player) == true) {

    		int dmg = event.getDamageLevel().getLevel();
    		if (dmg == 2) {
    			if (player.getItemInHand().getTypeId() == 271) {
    				player.sendMessage("¤eFirst point of room set...");
    				Block b = event.getBlock();

    				RoomMapper.preSaved1.put(player, new Vector(b.getX(), b.getY(), b.getZ()));
    			}
    		}
    	}
    }


    public void onBlockRightClick(BlockRightClickEvent event) {
    	Player player = event.getPlayer();
    	
    	if (RoomMapper.roomSetting.containsKey(player) && RoomMapper.roomSetting.get(player) == true) {
	    	if (player.getItemInHand().getTypeId() == 271) {
	    		player.sendMessage("¤eSecond point of room set...");
	    		Block b = event.getBlock();
	    		
	    		RoomMapper.preSaved2.put(player, new Vector(b.getX(), b.getY(), b.getZ()));
	    	}
    	}
    }
}
