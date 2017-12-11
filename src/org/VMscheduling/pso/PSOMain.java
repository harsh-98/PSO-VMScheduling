package org.VMscheduling.pso;

import java.util.Vector;
import java.util.*;

public class PSOmain implements PSOConstants {
    private Vector<Particle> swarm = new Vector<Particle>();
    private Location gBestLocation;
    private double gBest; // memmory + cpu
    private double gBestFit; // used memmory and cpu ratio w.r.t. original values
    private Particle gSolution;
    private Vector<Particle> lSolution = new Vector<Particle>();
    private double[] pBest = new double[SWARM_SIZE];
    private double[] pBestFit = new double[SWARM_SIZE];
    private Vector<Location> pBestLocation = new Vector<Location>();
    public Vector<VM> vmArray = new Vector<VM>();


    public void execute() {
        initializeSwarm();
        Random rand = new Random(SEED);
        for(int t = 0;t < MAX_ITERATION;t++){
            double minFit = 0;
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
                    if(velocity[j] == pBestLocation[i][j] ^ location[j] && velocity[j] == gBestLocation[j] ^ location[j])
                        newVelocity[j] = velocity[j];
                    swarm.get(i).velocity = newVelocity;
                }


                //updating the location
                boolean [] newLocation = new boolean[NO_OF_PM];
                Set<Integer> ids = new HashSet<Integer>();
                public PM[] pmArray = new PM[NO_OF_PM];
                for(int j=0; j<NO_OF_PM;j++){
                    double [] probi = PSOUtility.ratio(swarm.get(i).getFit(), pBestFit[i][j], gBestFit[j]);
                    if(swarm.get(i).velocity[j]) {newLocation[j] = location[j]; pmArray[j] = swarm.get(i).pmArray[j];}
                    if(!swarm.get(i).velocity[j]){
                        double randNum = rand.nextDouble();
                        if(randNum < probi[0]) {newLocation[j] = location[j]; pmArray[j] = swarm.get(i).pmArray[j];}
                        if(probi[0] <randNum && randNum < probi[1]) {newLocation[j] = pBestLocation[i][j];pmArray[j] = lSolution[i][j];}
                        if(probi[1] < randNum && randNum < probi[2]) {newLocation[j] = gBestLocation[j];pmArray[j] = gSolution[j];}
                        boolean a=true;
                        for(VM x : pmArray[j])
                            if(ids.contains(x.id)){
                                a=false;
                                pmArray[j] =null;
                                newLocation[j] = false;
                                break;
                            }
                        for(VM x : pmArray[j])
                            if(a){
                                ids.add(x.id);
                            }
                    }
                }
                for (int j=0;j<NO_OF_VM ;j++ ) {
                    if(!ids.contains(j)){
                        vm = new VM(j);
                        boolean assigned =false;
                        loop:
                        for(int k=0; k< NO_OF_PM;k++){
                            if(newLocation[k]){
                                pmArray[k].assign(vm);
                                assigned =true;
                                break loop;
                            }
                        }
                        if(!assigned){
                            for(int k=0;k<NO_OF_PM;k++){
                                if(!newLocation[k]){
                                    newLocation[k] = true;
                                    pmArray[k] =new PM();
                                    pmArray[k].assign(vm);
                                }
                            }
                        }
                    }
                }
                swarm.get(i).update(newLocation, pmArray);
                if(pBest[i]<swarm.get(i).calculateFitness()){
                    pBest[i] = swarm.get(i).calculateFitness();
                    pBestFit[i] = swarm.get(i).getFit();
                    lSolution = (Particle) swarm.get(i).clone();
                    pBestLocation.add(newLocation);
                    if(pBest[i] < gBest){
                        gBest = pBest[i]; // min value
                        gSolution = (Particle) swarm.get(i).clone();
                        gBestLocation = swarm[i].location;
                        gBestFit = pBestFit[i];
                    }
                }
            }
            System.out.println("Global opt"+gSolution.print());
        }

    }


    // initialization
    public void initializeSwarm() {
        for(int i =0; i<NO_OF_VM;i++){
            vmArray[i] = new VM(i);
        }
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
            for(int j=0; j<NO_OF_VM; j++){
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

            // creating a copy of the oject
            // https://stackoverflow.com/questions/2624165/how-to-copy-an-object-by-value-not-by-reference
            lSolution = (Particle) swarm.get(i).clone();
            if(minFitness > pBest[i]){
                minFitness = pBest[i];
                minFitnessIndex = i;
                gBestFit = pBestFit[i]; //max value
            }
        }
        gBest = minFitness; // min value
        gSolution = (Particle) swarm.get(minFitnessIndex).clone();
        gBestLocation = swarm[minFitnessIndex].location;
    }

}