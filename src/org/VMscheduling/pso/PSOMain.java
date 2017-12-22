package org.VMscheduling.pso;

import java.util.Vector;
import java.util.*;
import java.io.*;

public class PSOMain implements PSOConstants {
    private Vector<Particle> swarm = new Vector<Particle>();
    private double gBest; // memory + cpu
    private double[] gBestUtil; // used memory and cpu ratio w.r.t. original values
    public Particle gSolution;
    private Vector<Particle> lSolution = new Vector<Particle>();
    private double[] pBest = new double[SWARM_SIZE];
    private double[][] pBestUtil = new double[SWARM_SIZE][NO_OF_PM];
    public static Vector<VM> vmArray = new Vector<VM>();
    public static double [] PMram = new double[NO_OF_PM];
    public static double [] PMmips = new double[NO_OF_PM];

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void execute() {
        initializeSwarm();

        Random rand = new Random(SEED);
        for(int t = 0;t < MAX_ITERATION;t++){
            double minFit = 0;
            for(int i=0; i<SWARM_SIZE; i++) {

                boolean [] velocity = swarm.get(i).velocity.getVel();
                boolean [] location = swarm.get(i).location.getLoc();

                //updating the velocity
                boolean [] newVelocity = new boolean[NO_OF_PM];
                for(int j=0; j<NO_OF_PM;j++){
                    double [] probi = PSOUtility.ratio(1/swarm.get(i).calculateFitness(), 1/pBest[i], 1/gBest);

                    double randNum = rand.nextDouble();
                    if(randNum < probi[0]) newVelocity[j] = velocity[j];
                    if(probi[0] <randNum && randNum < probi[1]) newVelocity[j] = lSolution.get(i).location.getLoc()[j] ^ location[j];
                    if(probi[1] < randNum && randNum < probi[2]) newVelocity[j] = gSolution.location.getLoc()[j] ^ location[j];
                    if(velocity[j] == lSolution.get(i).location.getLoc()[j] ^ location[j] && velocity[j] == gSolution.location.getLoc()[j] ^ location[j])
                        newVelocity[j] = velocity[j];
                    swarm.get(i).velocity.setVel(newVelocity);
                }


                //updating the location
                boolean [] newLocation = new boolean[NO_OF_PM];
                Set<Integer> ids = new HashSet<Integer>();
                PM[] pmArray = new PM[NO_OF_PM];
                for(int j=0; j<NO_OF_PM;j++){
                    double [] probi = PSOUtility.ratio(swarm.get(i).getUtil()[j], pBestUtil[i][j], gBestUtil[j]);
                    if(!swarm.get(i).velocity.getVel()[j]){
                        double randNum = rand.nextDouble();
                        if(randNum < probi[0])
                            {newLocation[j] = location[j]; pmArray[j] = (PM) deepClone(swarm.get(i).pmArray[j]);}
                        else if(probi[0] <randNum && randNum < probi[1])
                            {newLocation[j] = lSolution.get(i).location.getLoc()[j];pmArray[j] = (PM) deepClone(lSolution.get(i).pmArray[j]);}
                        else if(probi[1] < randNum)
                            {newLocation[j] = gSolution.location.getLoc()[j];pmArray[j] = (PM) deepClone(gSolution.pmArray[j]);}
                        else
                            {newLocation[j] = location[j]; pmArray[j] = (PM) deepClone(swarm.get(i).pmArray[j]);}
                    }else
                        {newLocation[j] = location[j]; pmArray[j] = swarm.get(i).pmArray[j];}
                        boolean a=true;
                        Vector<VM> vmVect = pmArray[j].vmArray;
                        for(int f=0;f<vmVect.size();f++){
                            if(ids.contains(vmVect.get(f).id)){
                                a=false;
                                pmArray[j] = new PM(j,PMram[j],PMmips[j]);
                                newLocation[j] = false;
                                break;
                            }}
                        if(a){
                            for(int f=0;f<vmVect.size();f++){
                                ids.add(vmVect.get(f).id);
                            }
                        }
                }
                for (int j=0;j<NO_OF_VM ;j++ ) {
                    if(!ids.contains(j)){
                        //VM vm = new VM(j);
                        boolean assigned =false;
                        LOOP:
                        for(int k=0; k< NO_OF_PM;k++){
                            if(newLocation[k]){
                                assigned =pmArray[k].assignVM(vmArray.get(j));
                                if(assigned)
                                    break LOOP;
                            }
                        }
                        if(!assigned){
                            for(int k=0;k<NO_OF_PM;k++){
                                if(!newLocation[k]){
                                    newLocation[k] = true;
                                    pmArray[k].assignVM(vmArray.get(j));
                                    break;
                                }
                            }
                        }
                    }
                }
                swarm.get(i).update(newLocation, pmArray);

                if(pBest[i]>swarm.get(i).calculateFitness()){
                    pBest[i] = swarm.get(i).calculateFitness();
                    pBestUtil[i] = swarm.get(i).getUtil();
                        lSolution.set(i, (Particle) deepClone(swarm.get(i)));

                }
            }
            for(int i=0; i<SWARM_SIZE; i++) {
                if(pBest[i] < gBest){
                    gBest = pBest[i]; // min value
                        gSolution = (Particle) deepClone(swarm.get(i));
                    gBestUtil = pBestUtil[i];
                }
            }
            //System.out.println("Global opt: Iteration "+t);
        }
            gSolution.print();

    }


    // initialization
    public void initializeSwarm() {
        Particle p;
        Random rand = new Random(SEED);
        for(int i=0; i<SWARM_SIZE; i++) {
            //VM vm;

            // create the PM list
            PM[] pmArray = new PM[NO_OF_PM];
            boolean pmOnArray[] =new boolean[NO_OF_PM];
            for(int j=0; j<NO_OF_PM; j++){
                pmArray[j] = new PM(j,PMram[j],PMmips[j]);
            }

            // assign the VM to the PM
            ASSIGN:
            for(int j=0; j<NO_OF_VM; j++){
                //vm = new VM(j);
                int  n = rand.nextInt(NO_OF_PM);
                if(!pmArray[n].assignVM(vmArray.get(j))){
                    j--;
                    continue ASSIGN;
                }
                pmOnArray[n] = true;
            }

            boolean velocity[] = new boolean[NO_OF_PM];
            for(int j=0; j<NO_OF_PM; j++){
                velocity[j] = rand.nextBoolean();
            }

            p = new Particle(i, velocity, pmOnArray, pmArray);
            swarm.add(p);
            //swarm.get(i).print();

        }

        // set the global min
        double minFitness = Double.POSITIVE_INFINITY;
        int minFitnessIndex = 0;
        for(int i = 0; i<SWARM_SIZE; i++){
            pBest[i] = swarm.get(i).calculateFitness();
            pBestUtil[i] = swarm.get(i).getUtil();

            // creating a copy of the oject
            // https://stackoverflow.com/questions/2624165/how-to-copy-an-object-by-value-not-by-reference
                lSolution.add((Particle) deepClone(swarm.get(i)));
            if(minFitness > pBest[i]){
                minFitness = pBest[i];
                minFitnessIndex = i;
                gBestUtil = pBestUtil[i]; //max value
            }
        }
        gBest = minFitness; // min value
            gSolution = (Particle) deepClone(swarm.get(minFitnessIndex));
    }

}