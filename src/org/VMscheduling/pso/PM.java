package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location

public class PM{
    // store the Location in an array to accommodate multi-dimensional problem space
    private int id;
    public active = false;
    public double originalMemmory = 1000;
    public double originalCpu = 1000;
    public double memmory = 1000;
    public double cpu = 1000;
    public int noVM = 0;
    public Vector<VM> vmArray = new Vector<VM>();

    public PM(int id, double memmory = 100, double cpu =100){
        this.id =id;
        this.memmory =memmory;
        this.cpu =cpu;
    }



    public int getId(int id){
        return id;
    }

    public boolean assignVM(VM vm) {
        if(this.memmory - vm.memmory >= 0 && this.cpu - vm.cpu >= 0){
            this.memmory-=vm.memmory;
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
            this.memmory+=vm.memmory;
            this.cpu+=vm.cpu;
            this.noVM-=1;
            return true;
        } else {
            return false;
        }
    }

}
