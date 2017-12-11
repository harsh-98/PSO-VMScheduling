package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location

public class VM{
    // store the Location in an array to accommodate multi-dimensional problem space
    public int id;
    public boolean assigned = false;
    public double memmory = 100;
    public double cpu = 100;

    public VM(int id){
        this.id =id;
        //this.memmory =memmory;
        //this.cpu =cpu;
        this.assigned = true;
    }

}