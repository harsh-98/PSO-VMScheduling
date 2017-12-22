package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location
import java.io.*;
public class VM implements Serializable{
    // store the Location in an array to accommodate multi-dimensional problem space
    public int id;
    public boolean assigned = false;
    public double memory = 100;
    public double cpu = 100;

    public VM(int id,double memory,double cpu){
        this.id =id;
        this.memory =memory;
        this.cpu =cpu;
        this.assigned = true;
    }

}