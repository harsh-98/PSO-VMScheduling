package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location

public class Location {
	// store the Location in an array to accommodate multi-dimensional problem space
	private boolean[] loc;

	public Location(boolean[] loc) {
		super();
		this.loc = loc;
	}

	public boolean[] getLoc() {
		return loc;
	}

	public void setLoc(boolean[] loc) {
		this.loc = loc;
	}
	
}
