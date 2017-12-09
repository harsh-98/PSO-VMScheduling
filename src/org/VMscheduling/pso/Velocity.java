package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent velocity

public class Velocity {
	// store the Velocity in an array to accommodate multi-dimensional problem space
	private boolean[] vel;

	public Velocity(boolean[] vel) {
		super();
		this.vel = vel;
	}

	public boolean[] getPos() {
		return vel;
	}

	public void setPos(boolean[] vel) {
		this.vel = vel;
	}
	
}
