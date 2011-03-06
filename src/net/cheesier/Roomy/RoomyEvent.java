package net.cheesier.Roomy;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class RoomyEvent extends Event {
    private Reason reason;
    private Player player = null;
    private List<String> rooms = null;

    protected RoomyEvent(Reason reason, Player player, List<String> rooms) {
        super("RoomyEvent");
        this.reason = reason;
        this.player = player;
        this.rooms = rooms;
    }

    public Reason getReason() {
        return this.reason;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public List<String> getRooms() {
    	return this.rooms;
    }

    public enum Reason{
        ROOM_CHANGE
    }

}