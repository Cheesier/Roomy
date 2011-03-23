package net.cheesier.Roomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;
import org.bukkit.util.config.Configuration;

/**
 * Roomy for Bukkit
 *
 * @author Cheesier
 */


public class Roomy extends JavaPlugin {
    private final RoomyPlayerListener playerListener = new RoomyPlayerListener(this);
    private final RoomyAPI roomyAPI = new RoomyAPI();
    
    //The plugin, Probably a bad way of doing it...
    public static Roomy plugin;
    
    //The implementation of Nijikokun's "Permission" plugin
    public static PermissionHandler Permissions = null; // permissions
    private static Logger log = Logger.getLogger("Minecraft");
    
    // The saved room and setting for the /roomy command
    //public static HashMap<String, >
    public static List<SavedRoom> savedRooms = new ArrayList<SavedRoom>();
    public static HashMap<Player, Boolean> roomySetting = new HashMap<Player, Boolean>();
    public static HashMap<Player, Integer> checkTimers = new HashMap<Player, Integer>();
    public static int timerInterval = 1;
    
    // before we make a room, we need 2 vectors to define it
    public static HashMap<Player, Vector> preSaved1 = new HashMap<Player, Vector>();
    public static HashMap<Player, Vector> preSaved2 = new HashMap<Player, Vector>();
    
    // Temporary!!! (maybe)
    public static HashMap<Player, List<String>> lastRoom = new HashMap<Player, List<String>>();
    public static boolean track = false;
    
    // Config file
    public static Configuration config;
    
    
    public RoomyAPI getAPI() {
    	return roomyAPI;
    }



    public void onEnable() {
    	plugin = this;
    	// load config file
    	config = this.getConfiguration();
    	
    	// Start all the players timers
    	RoomyLibrary.startPlayerTracking();
    	
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_ANIMATION, playerListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Monitor, this);
        
        
        // Say hello in a fancy way and load the rooms :)
        PluginDescriptionFile pdfFile = this.getDescription();
        int numLoadedRooms = RoomyFileManager.load();
        
        //The implementation of Nijikokun's "Permission" plugin
    	setupPermissions();
        
        if (numLoadedRooms != -1) {
        	log.info("[" + pdfFile.getName() + "] version [" + pdfFile.getVersion() + "] loaded " + numLoadedRooms + " Room's");
        }
        else {
        	log.warning("[" + pdfFile.getName() + "] version [" + pdfFile.getVersion() + "] loaded, but did not find 'Roomy/config.yml'");
        }
    }
    
    
    public void onDisable() {
    	int savedNumRooms = RoomyFileManager.save(); // save and see how many rooms that was saved
    	//System.out.println("[Roomy] Saved: " + RoomyFileManager.newSave());
    	
    	PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] saved " + savedNumRooms + " rooms");
    }
    
    // Check for all the commands that the plugin registered
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
    	String cmd = command.getName().toLowerCase();
    	
    	if (cmd.equalsIgnoreCase("roomy"))
    		return RoomyCommands.roomyCmd(sender, args);
    	
    	else if (cmd.equalsIgnoreCase("setroom"))
    		return RoomyCommands.setroomCmd(sender, args);
    	
    	else if(cmd.equalsIgnoreCase("loadrooms"))
    		return RoomyCommands.loadroomsCmd(sender, args);
    	
    	else if(cmd.equalsIgnoreCase("saverooms"))
    		return RoomyCommands.saveroomsCmd(sender, args);
    	
    	else if(cmd.equalsIgnoreCase("track"))
    		return RoomyCommands.trackCmd(sender, args);
    	
    	else if(cmd.equalsIgnoreCase("inside"))
    		return RoomyCommands.insideCmd(sender, args);
    	
    	else if(cmd.equalsIgnoreCase("removeroom"))
    		return RoomyCommands.removeroomCmd(sender, args);
    	
    	else if(cmd.equalsIgnoreCase("listrooms"))
    		return RoomyCommands.listroomsCmd(sender, args);
    	
    	return false;
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