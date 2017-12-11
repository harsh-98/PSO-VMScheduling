package org.VMscheduling.pso;

/* author: harsh jain */

import java.lang.Cloneable;

public class Particle implements Cloneable{
	public boolean feasible =false;
	public Velocity velocity;
	public Location location;
	public PM[] pmArray;
	public int id;
	public double fitness = 0;
	public Particle() {
		super();
	}

	public Particle clone()throws CloneNotSupportedException{  
		return (Particle) super.clone();
	}

	public Particle(int id, boolean[] velocity, boolean[] location, PM[] pmArray) {
		super();
		this.feasible = true;
		this.velocity = new Velocity(velocity);
		this.location = new Location(location);
		this.pmArray = pmArray;
		this.id = id;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double calculateFitness(){
		double memmory = 0, cpu = 0;
		for(int i =0;i<location.size();i++){
			if(location.getLoc()[i]){
				memmory += pmArray[i].memmory;
				cpu += pmArray[i].cpu;
			}
		}
		return memmory+cpu;
	}

	public double[] getFit(){
		double [] fit = new double[location.size()];
		for(int i = 0; i<location.size(); i++){
			fit[i] = 1-pmArray[i].memmory/pmArray[i].originalMemmory + 1-pmArray[i].cpu/pmArray[i].originalCpu;
		}
	return fit;
	}

	public boolean update(boolean [] newLocation, PM[] pmArray){
		this.location.setLoc(newLocation);
		this.pmArray = pmArray;
		return true;
	}

	public void print(){
		for(PM x : this.pmArray){
			System.out.print(x.id+":");
			for(VM v : x.vmArray){
				System.out.print(v.id+",");
			}
			System.out.println(" ");
		}
	}

}
