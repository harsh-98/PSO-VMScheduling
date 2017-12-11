package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent velocity
import java.io.*;
public class Velocity implements Serializable{
	// store the Velocity in an array to accommodate multi-dimensional problem space
	private boolean[] vel;

	public Velocity(boolean[] vel) {
		super();
		this.vel = vel;
	}

	public boolean[] getVel() {
		return this.vel;
	}


	public void setVel(boolean[] vel) {
		this.vel = vel;
	}

}
