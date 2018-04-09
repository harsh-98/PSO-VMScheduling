package org.VMscheduling.pso;

/* author: harsh jain */
import java.io.*;
import java.lang.Cloneable;

public class Particle implements Serializable{
	public boolean feasible =false;
	public Velocity velocity;
	public Location location;
	public PM[] pmArray;
	public int id;
	public double fitness = 0;
	public Particle() {
		super();
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
		double memory = 0, cpu = 0;
		for(int i =0;i<location.size();i++){
			if(location.getLoc()[i]){
				memory += pmArray[i].memory;
				cpu += pmArray[i].cpu;
			}
		}
		return memory+cpu;
	}

	public double[] getUtil(){
		double [] fit = new double[location.size()];
		for(int i = 0; i<location.size(); i++){
			fit[i] = 1-pmArray[i].memory/pmArray[i].originalmemory + 1-pmArray[i].cpu/pmArray[i].originalCpu;
			if(fit[i] == 0)fit[i] = PSOConstants.MIN/(pmArray[i].originalmemory+pmArray[i].originalCpu);
		}
	return fit;
	}

	public boolean update(boolean [] newLocation, PM[] pmArray){
		this.location.setLoc(newLocation);
		this.pmArray = pmArray;
		return true;
	}

	public void print(String filename) throws FileNotFoundException {
  PrintWriter pw = new PrintWriter("data/"+filename);
  pw.print("PM,VM\n");
		for(PM x : this.pmArray){
			// System.out.print(String.format("%d (Pcpu%d, Pram:%d):",(int)x.id, (int)x.cpu, (int)x.memory));
			for(VM v : x.vmArray){
			pw.print(x.id+","+v.id+"\n");
				// System.out.print(String.format("%d (Vcpu%d, Vram:%d),",(int)v.id, (int)v.cpu, (int)v.memory));
			}
		}
  pw.close();
			// System.out.println("");
	}

}
