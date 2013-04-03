package osproject;


import java.util.*;

public class Main {


    public static void main(String[] args) {
    
        Random generator = new Random();
        LinkedList<Process> rrProcQueue = new LinkedList<Process>();
        LinkedList<Process> fcfsProcQueue = new LinkedList<Process>();

        for(int i = 1; i <= 4; i++) {
            int cpuTime = generator.nextInt(20);
            int arrival = generator.nextInt(10);

            Process x = new Process(cpuTime, arrival);
            Process y = new Process(cpuTime, arrival);

            rrProcQueue.add(x);
            fcfsProcQueue.add(y);
            
        }

        //sort the list to make it simulate arrival times
        //add ID to each process based on the index of the sorted list
        ProcComparator p = new ProcComparator();
        Collections.sort(rrProcQueue, p);
        for(Process a : rrProcQueue) {
            a.setID(rrProcQueue.indexOf(a) + 1);
        }
        Collections.sort(fcfsProcQueue, p);
        for(Process b : fcfsProcQueue) {
            b.setID(fcfsProcQueue.indexOf(b) + 1);
        }

        System.out.println("Round Robin Algorithm");
        System.out.println("----------------------");
        RoundRobin rrScheduler = new RoundRobin(rrProcQueue);
        rrScheduler.execute();
        System.out.println();
        System.out.println("FCFS Algorithm");
        System.out.println("----------------------");
        FCFS fcfsScheduler = new FCFS(fcfsProcQueue);
        fcfsScheduler.execute();

         //get the list of processes for each scheduler once they are all
        //finished executing
        LinkedList<Process> fcfsProcesses =
                new LinkedList<Process>(fcfsScheduler.returnFinalProc());
        LinkedList<Process> rrProcesses =
                new LinkedList<Process>(rrScheduler.returnFinalListRR());
      
        //calculate total wait time and turnaround time for FCFS
        System.out.println();
        System.out.println("Individual Stats for FCFS");
        System.out.println("------------------");
        double averageWait = 0;
        double averageTurnaround = 0;
        for(Process proc: fcfsProcesses){
            System.out.println("( P" + proc.getId() + " ) ");
            System.out.println("Waiting Time: " + proc.getWait());
            System.out.println("TurnAround Time: " + proc.getTurnAround());
            averageWait = averageWait + proc.getWait();
            averageTurnaround = averageTurnaround + 
                    (proc.getcpu() + proc.getWait());
        }

        //calculate total wait time and turnaround time for RR
        System.out.println();
        System.out.println("Individual Stats for RR");
        System.out.println("------------------");
        double averageWaitRR = 0;
        double averageTurnaroundRR = 0;
        for(Process proc: rrProcesses){
            System.out.println("( P" + proc.getId() + " ) ");
            System.out.println("Waiting Time: " + proc.getWait());
            System.out.println("TurnAround Time: " + proc.getTurnAround());
            averageWaitRR = averageWaitRR + proc.getWait();
            averageTurnaroundRR = averageTurnaroundRR +
                    (proc.getcpu() + proc.getWait());
        }
        
        //calculate total avg wait times and turnaround times for both
        averageWait = averageWait/fcfsProcesses.size();
        averageTurnaround = averageTurnaround/fcfsProcesses.size();
        averageWaitRR = averageWaitRR/fcfsProcesses.size();
        averageTurnaroundRR = averageTurnaroundRR/fcfsProcesses.size();

        System.out.println();
        System.out.println("Scheduler Stats");
        System.out.println("average turn around fcfs " + averageTurnaround);
        System.out.println("average turnaround rr " + averageTurnaroundRR);
        System.out.println("average wait fcfs " + averageWait);
        System.out.println("average wait rr " + averageWaitRR);
        
    }
}

