package com.cheesier.Roomy;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class RoomyEvent extends Event implements Cancellable{
    private Reason reason;
    private boolean isCancelled = false;
    private Player player = null;
    private List<String> rooms = null;

    protected RoomyEvent(Reason reason, Player player, List<String> rooms) {
        super("RoomyEvent");
        this.reason = reason;
        this.player = player;
        this.rooms = rooms;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
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