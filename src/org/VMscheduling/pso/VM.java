package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location

public class VM{
    // store the Location in an array to accommodate multi-dimensional problem space
    private int id;
    public assigned = false;
    public double memory = 1000;
    public double cpu = 1000;

    public void setId(int id){
        this.id =id;
    }

    public int getId(int id){
        return id;
    }
}