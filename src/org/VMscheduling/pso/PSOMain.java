package org.VMscheduling.pso;

import java.util.Random;
import java.util.Vector;

public class PSOmain implements PSOConstants {
    private Vector<Particle> swarm = new Vector<Particle>();
    private Location gBestLocation;
    private double gBest; // memmory + cpu
    private double gBestFit; // used memmory and cpu ratio w.r.t. original values
    private double[] pBest = new double[SWARM_SIZE];
    private double[] pBestFit = new double[SWARM_SIZE];
    private Vector<Location> pBestLocation = new Vector<Location>();

    public void execute() {
        initializeSwarm();
        Random rand = new Random(SEED);
        for(int t = 0;t < MAX_ITERATION;t++){

            for(int i=0; i<SWARM_SIZE; i++) {
                boolean [] velocity = swarm.get(i).velocity;
                boolean [] location = swarm.get(i).location;

                //updating the velocity
                boolean [] newVelocity = new boolean[NO_OF_PM];
                for(int j=0; j<NO_OF_PM;j++){
                    double [] probi = PSOUtility.ratio(1/swarm.get(i).calculateFitness(), 1/pBest[i], 1/gBest);

                    double randNum = rand.nextDouble();
                    if(randNum < probi[0]) newVelocity[j] = velocity[j];
                    if(probi[0] <randNum && randNum < probi[1]) newVelocity[j] = pBestLocation[i][j] ^ location[j];
                    if(probi[1] < randNum && randNum < probi[2]) newVelocity[j] = gBestLocation[j] ^ location[j];
                    if(location[j] == pBestLocation[i][j] && pBestLocation[i][j] == gBestLocation[j]) newVelocity[j] = velocity[j];
                    swarm.get(i).velocity = newVelocity;
                }


                //updating the location
                boolean [] newLocation = new boolean[NO_OF_PM];
                for(int j=0; j<NO_OF_PM;j++){
                    double [] probi = PSOUtility.ratio(swarm.get(i).getFit(), pBestFit[i][j], gBestFit[j]);
                    if(!swarm.get(i).velocity[j]){
                        double randNum = rand.nextDouble();
                        if(randNum < probi[0]) newVelocity[j] = location[j];
                        if(probi[0] <randNum && randNum < probi[1]) newVelocity[j] = pBestLocation[i][j];
                        if(probi[1] < randNum && randNum < probi[2]) newVelocity[j] = gBestLocation[j];
                    }
                }

            }
        }

    }


    // initialization
    public void initializeSwarm() {
        Particle p;
        for(int i=0; i<SWARM_SIZE; i++) {
            VM vm;
            Random rand = new Random(SEED);

            // create the PM list
            public PM[] pmArray = new PM[NO_OF_PM];
            public boolean pmOnArray[NO_OF_PM];
            for(int j=0; j<NO_OF_PM; j++){
                pmArray[j] = new PM(j);
            }

            // assign the VM to the PM
            ASSIGN:
            for(int j=0; j<NO_OF_VM = 100; j++){
                vm = new VM(j);
                int  n = rand.nextInt(NO_OF_PM);
                if(!pmArray[n].assignVM(vm)){
                    j--;
                    continue ASSIGN;
                }
                pmOnArray[n] = true;
            }

            Random rand = new Random(SEED);
            public boolean velocity[NO_OF_PM];
            for(int j=0; j<NO_OF_PM; j++){
                velocity[j] = rand.nextBoolean();
            }

            p = new Particle(i, velocity, pmOnArray, pmArray);
            swarm.add(p);
            pBestLocation.add(pmOnArray);
        }

        // set the global min
        var minFitness = Double.POSITIVE_INFINITY;
        var minFitnessIndex = 0;
        for(int i = 0; i<SWARM_SIZE; i++){
            pBest[i] = swarm.get(i).calculateFitness();
            pBestFit[i] = swarm.get(i).getFit();
            if(minFitness > pBest[i]){
                minFitness = pBest[i];
                minFitnessIndex = i;
                gBestFit = pBestFit[i];
            }
        }
        gBest = minFitness;
        gBestLocation = swarm[minFitnessIndex].location;
    }

}