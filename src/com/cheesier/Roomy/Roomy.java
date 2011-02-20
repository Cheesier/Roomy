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
 * Roomy for Bukkit
 *
 * @author Cheesier
 */

//@SuppressWarnings("unused")
public class Roomy extends JavaPlugin {
    private final RoomyPlayerListener playerListener = new RoomyPlayerListener(this);
    private final RoomyBlockListener blockListener = new RoomyBlockListener(this);
    
    //The implementation of Nijikokun's "Permission" plugin
    public static PermissionHandler Permissions = null; // permissions
    private static Logger log = Logger.getLogger("Minecraft");
    
    // 
    public static List<SavedRoom> savedRooms = new ArrayList<SavedRoom>();
    public static HashMap<Player, Boolean> roomSetting = new HashMap<Player, Boolean>();
    
    // before we make a room, we need 2 vectors to define it
    // 
    public static HashMap<Player, Vector> preSaved1 = new HashMap<Player,Vector>();
    public static HashMap<Player, Vector> preSaved2 = new HashMap<Player,Vector>();
    
    // Temporary!!! (maybe)
    public static HashMap<Player, List<String>> lastRoom = new HashMap<Player, List<String>>();
    public static boolean track = false;

    

    public void onEnable() {
    	RoomyFileManager.makeFile(); // makes config file if needed
    	RoomyFileManager.load(); // load all the rooms and put in "savedRooms"
    	
    	//The implementation of Nijikokun's "Permission" plugin
    	setupPermissions();
        
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGED, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
        
        
        // Say hello in a fancy way
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version [" + pdfFile.getVersion() + "] loaded");
    }
    
    
    public void onDisable() {
    	int saved = RoomyFileManager.save();
    	
    	PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] saved " + saved + " rooms");
    }
    
    
    //The implementation of Nijikokun's "Permission" plugin
    public void setupPermissions() {
    	PluginDescriptionFile pdfFile = this.getDescription();
    	Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

    	if(Roomy.Permissions == null) {
    		if(permissions != null) {
    			Permissions = ((Permissions)permissions).getHandler();
    		} else {
    			System.out.println("["+pdfFile.getName()+"] Permission system not enabled. Disabling plugin.");
    			this.getServer().getPluginManager().disablePlugin(this);
    		}
    	}
    }
}