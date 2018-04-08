package org.VMscheduling.pso;

import java.util.*;
import java.io.*;

public class Execution {

	public static void main(String args[]) {
        // PSOConstants.iterate(1);
        PSOMain psomain = new PSOMain();
        Random rand = new Random(PSOConstants.SEED);
        for(int i=0;i < PSOConstants.NO_OF_PM; i++){
            int ram = rand.nextInt(51)*10 + 500;
            int cpu = rand.nextInt(51)*10 + 500;
            psomain.PMram[i] = ram;
            psomain.PMmips[i] = cpu;
            System.out.println(String.format("%d (Pcpu%d, Pram:%d):", i, cpu, ram));

        }
        for(int i=0;i < PSOConstants.NO_OF_VM; i++){
            int ram = rand.nextInt(11)*10 + 100;
            int cpu = rand.nextInt(11)*10 + 100;
            psomain.vmArray.add(new VM(i, ram, cpu));
            System.out.println(String.format("%d (Pcpu%d, Pram:%d):", i, cpu, ram));
        }
        psomain.execute();

    }

}
