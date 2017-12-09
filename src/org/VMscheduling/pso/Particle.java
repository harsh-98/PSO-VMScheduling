package org.gandhim.pso;

public class Particle {
	public boolean feasible =false;
	private Velocity velocity;
	private Location location;
	public PM[] pmArray;
	public int id;
	public fitness = 0;
	public Particle() {
		super();
	}

	public Particle(int id, Velocity velocity, Location location, PM[] pmArray) {
		super();
		this.feasible = true;
		this.velocity = velocity;
		this.location = location;
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
		for(int i =0;i<location.length;i++){
			double memmory = 0, cpu = 0;
			if(location[i] == 1){
				memmory += pmArray[i].memmory;
				cpu += pmArray[i].cpu;
				return memmory+cpu;
			}
		}
	}

	public double[] getFit(){
		double [] fit = new double[location.length];
		for(int i = 0; i<location.length; i++){
			fit[i] = 1-pmArray.get(i).memmory/pmArray.get(i).originalMemmory + 1-pmArray.get(i).cpu/pmArray.get(i).originalCpu;
		}
	return fit;
	}

}
