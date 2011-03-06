package net.cheesier.Roomy;

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
    
    
    // Right click
    public void onBlockRightClick(BlockRightClickEvent event) {
    	Player player = event.getPlayer();
    	
    	if (Roomy.roomySetting.containsKey(player) && Roomy.roomySetting.get(player) == true) {
	    	if (player.getItemInHand().getTypeId() == 271) {
	    		player.sendMessage("¤eSecond point of room set");
	    		
	    		Roomy.preSaved2.put(player, event.getBlock().getLocation().toVector());
	    	}
    	}
    }
}
