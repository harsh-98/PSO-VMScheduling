package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location
import java.io.*;
public class Location implements Serializable{
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

	public int size(){
		return this.loc.length;
	}
}
