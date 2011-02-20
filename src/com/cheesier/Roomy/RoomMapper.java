package com.cheesier.Roomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

/**
 * RoomMapper for Bukkit
 *
 * @author Cheesier
 */

//@SuppressWarnings("unused")
public class RoomMapper extends JavaPlugin {
    private final RoomMapperPlayerListener playerListener = new RoomMapperPlayerListener(this);
    private final RoomMapperBlockListener blockListener = new RoomMapperBlockListener(this);
    
    public static PermissionHandler Permissions = null; // permissions
    private static Logger log = Logger.getLogger("Minecraft");
    
    // Varibles to keep, love em
    public static List<SavedVector> savedVectors = new ArrayList<SavedVector>(); // all the vectors loaded
    public static HashMap<Player, Boolean> roomSetting = new HashMap<Player, Boolean>();
    public static HashMap<Player, Vector> preSaved1 = new HashMap<Player,Vector>(); // the Vector b4 its saved as an area, position 1
    public static HashMap<Player, Vector> preSaved2 = new HashMap<Player,Vector>(); // position 2
    
    // Temporary!!! (maybe)
    public static HashMap<Player, List<String>> lastRoom = new HashMap<Player, List<String>>();
    public static boolean track = false;

    

    public void onEnable() {
    	RoomMapperFileManager.makeFile(); // makes config file if needed
    	RoomMapperFileManager.load();
    	setupPermissions();
        
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGED, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
        
        
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version [" + pdfFile.getVersion() + "] loaded");
    }
    public void onDisable() {
    	int saved = RoomMapperFileManager.save();
    	
    	PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] saved " + saved + " rooms");
    }
    
    
    public void setupPermissions() {
    	PluginDescriptionFile pdfFile = this.getDescription();
    	Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

    	if(RoomMapper.Permissions == null) {
    		if(permissions != null) {
    			Permissions = ((Permissions)permissions).getHandler();
    		} else {
    			System.out.println("["+pdfFile.getName()+"] Permission system not enabled. Disabling plugin.");
    			this.getServer().getPluginManager().disablePlugin(this);
    		}
    	}
    }
}