package org.VMscheduling.pso;

/* author: harsh jain */

// bean class to represent location

public class PM{
    // store the Location in an array to accommodate multi-dimensional problem space
    private int id;
    public active = false;
    public double memory = 1000;
    public double cpu = 1000;
    public int noVM = 0;
    public Vector<VM> vmArray = new Vector<VM>();

    public void setId(int id){
        this.id =id;
    }

    public int getId(int id){
        return id;
    }

    public boolean assignVM(VM vm) {
        if(this.memory - vm.memory >= 0 && this.cpu - vm.cpu >= 0){
            this.memory-=vm.memory;
            this.cpu-=vm.cpu;
            this.vmArray.add(vm);
            this.noVM+=1;
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
