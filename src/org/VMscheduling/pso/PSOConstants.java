package org.VMscheduling.pso;

/* author: harsh jain */

// this is an interface to keep the configuration for the PSO
// you can modify the value depends on your needs

public class PSOConstants {
	public static int SWARM_SIZE = 50;
	public static int MAX_ITERATION = 10;
	public static int NO_OF_PM = 200;
	public static int NO_OF_VM = 500;
	public static int SEED = 1;
	public static int NO_OF_CLOUDLETS = 1000;
	public static Double MIN = 20.0;
	public static int [] PMArray = {200 ,300 ,250, 300 ,350, 250, 200};
	public static int [] VMArray = {1200 ,500 ,900, 1000 ,1000, 1000, 1200};
	public static void iterate(int i){
		NO_OF_VM = VMArray[i];
		NO_OF_PM = PMArray[i];
	}

}
