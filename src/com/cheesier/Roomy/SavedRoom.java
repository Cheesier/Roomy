package com.cheesier.Roomy;

import org.bukkit.util.Vector;

public class SavedRoom {
	private String name;
	private Vector min;
	private Vector max;

	SavedRoom(String name, Vector vec1, Vector vec2) {
		this.name = name;
		this.min = Vector.getMinimum(vec1, vec2);
		this.max = Vector.getMaximum(vec1, vec2);
	}
	
	public String getName() {
		return name;
	}
	
	public Vector getVectorMin() {
		return min;
	}
	
	public Vector getVectorMax() {
		return max;
	}
	
	public boolean isInAABB(Vector playerVec) {
		return playerVec.isInAABB(this.min, this.max);
	}
	
	public String toSaveFormat() {
		String s = ":"; // Seperator character
		return name+s+
			min.getBlockX()+s+min.getBlockY()+s+min.getBlockZ()+s+
			max.getBlockX()+s+max.getBlockY()+s+max.getBlockZ();
	}
	
}
