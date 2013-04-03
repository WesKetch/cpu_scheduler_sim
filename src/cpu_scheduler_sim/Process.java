package osproject;

import java.util.*;

/**
 *
 * @author Wes
 */
public class Process{

    //Process Object has cpuBurst time, cpuRunTime(to know when process is finished)
    //an id, arrival time, and ioBurstList, used to simulate the how many io bursts
    //there are and when they occur in the process
    private int cpuburst;
    private int cpuRunTime;
    private int id;
    private int arrival;
    private int wait;
    LinkedList<Integer> ioburstList = new LinkedList<Integer>();

    Process(int cpu, int arrival){
        this.cpuburst = cpu;
        this.arrival = arrival;
        this.cpuRunTime = 0;
        addiobursts();
    }

    public void incrementRunTime(){
        this.cpuRunTime++;
    }

    public void incrementWait(){
        this.wait++;
    }

    public int getRunTime(){
        return this.cpuRunTime;
    }
    public void setcpu(int x){
        this.cpuburst = x;
    }

    public void setID(int id) {
        this.id= id;
    }

    public int getcpu(){
        return cpuburst;
    }

    public int getArrival() {
        return this.arrival;
    }

    public int getId(){
        return this.id;
    }

    public int getWait(){
        return this.wait;
    }

    public int getTurnAround(){
        return this.wait+this.cpuRunTime;
    }

    //adds an io burst at either 6 seconds into process execution
    //or 12 seconds depending on length of processes cpu burst
    private void addiobursts(){
           if(this.cpuburst > 5)
                ioburstList.add(6);
            if(this.cpuburst > 12)
                ioburstList.add(12);
            
    }

    //for comparison in schedulers, makes sure theres a next burst
    //if not return -1 signaling its empty
    public int getNextBurst(){
        if(ioburstList.peek() != null) {
            return ioburstList.peek();
        } else
            return -1;
    }

}
    class ProcComparator implements Comparator<Process> {

    public int compare(Process p1, Process p2) {
       if(p1.getArrival() < p2.getArrival())
           return -1;
       else if(p1.getArrival() == p2.getArrival()){
           return 0;
       } else
           return 1;
    }
}