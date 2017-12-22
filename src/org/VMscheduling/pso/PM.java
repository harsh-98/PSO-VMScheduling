package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location

import java.util.Vector;
import java.io.*;
public class PM implements Serializable{
    // store the Location in an array to accommodate multi-dimensional problem space
    public int id;
    public boolean active = false;
    public double originalmemory = 1000;
    public double originalCpu = 1000;
    public double memory = 1000;
    public double cpu = 1000;
    public int noVM = 0;
    public Vector<VM> vmArray = new Vector<VM>();

    public PM(int id,double memory,double cpu){
        this.id =id;
        this.memory =memory;
        this.cpu =cpu;
    }




    public boolean assignVM(VM vm) {
        if(this.memory - vm.memory >= 0 && this.cpu - vm.cpu >= 0){
            this.memory-=vm.memory;
            this.cpu-=vm.cpu;
            this.vmArray.add(vm);
            this.noVM+=1;
            this.active = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeVM(VM vm) {
        if(vmArray.remove(vm)){
            this.memory+=vm.memory;
            this.cpu+=vm.cpu;
            this.noVM-=1;
            return true;
        } else {
            return false;
        }
    }

}
