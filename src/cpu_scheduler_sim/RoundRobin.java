package osproject;

import java.util.*;
/**
 *
 * @author Wes
 */
public class RoundRobin {
    private LinkedList<Process> rrProcQueue;
    private LinkedList<Process> finalProcListRR = new LinkedList<Process>();
    private  int quantum;

    public RoundRobin(LinkedList<Process> procQueue) {
        this.rrProcQueue = procQueue;
        this.quantum = 3;
    }

    //runs the RR Algorithm on Process List
    public void execute() {
        Process x = rrProcQueue.peek();
        int totaltime = x.getArrival();

        //loop to keep scheduling processes until
        //they are all finished
        while(!rrProcQueue.isEmpty()){
            //grabs first process in List and gets the necessary
            //info for that process
            Process runningProcess = rrProcQueue.peek();
            int ioBurst = runningProcess.getNextBurst();
            int runTime = runningProcess.getRunTime();
            int cpuTime = runningProcess.getcpu();
            int id = runningProcess.getId();

            //print statements just to debug for now
           
                int quantumCount = 0;
                System.out.println("( P" + runningProcess.getId() + " ) - Arrived");
                //Process Runs in the "cpu" as long as there is not an IO burst
                //or the cpu burst is finished or time quantum is reached
                while( (runTime != cpuTime) && (ioBurst != runTime) && (quantumCount != this.quantum) ) {
                    System.out.println("( P" + runningProcess.getId() + " ) - Executing");
                    runningProcess.incrementRunTime();
                    runTime = runningProcess.getRunTime();
                    quantumCount++;
              
                    for(Process p: rrProcQueue){
                        if(p.getId() != id  && (p.getArrival() <= totaltime)){
                            p.incrementWait();
                        }
                    }
                    totaltime++;
                }


                //if process has finished executing remove from
                //process list, if process hit io burst, remove that io burst from
                //process and move process to end of list, if time quantum is
                //reached move process to end of list
                if(runTime == cpuTime){
                    System.out.println("( P" + runningProcess.getId() + " ) - Finished, Removed");
                    finalProcListRR.add(runningProcess);
                    rrProcQueue.remove(runningProcess);
                }
                else if (ioBurst == runTime) {
                    System.out.println("( P" + runningProcess.getId() + " ) - Switched, IOburst");
                    runningProcess.ioburstList.remove(0);
                    moveProcess(runningProcess, totaltime);
                }
                else if(quantumCount == this.quantum) {
                    System.out.println("( P" + runningProcess.getId() + " ) - Switched, Time Quantum");
                    moveProcess(runningProcess, totaltime);
                }
        }
        System.out.println("finished");
   }

 

   public LinkedList<Process> returnFinalListRR() {
       return finalProcListRR;
   }

   //once a process is interupted this method moves the process
   //based on arrival times
   public void moveProcess(Process p, int totaltime){
        
              boolean flag = false;
                    for(int b = 1; b < rrProcQueue.size();b++) {

                        Process c = rrProcQueue.get(b);
                        
                        if(c.getWait()== 0){
                            rrProcQueue.add(b - 1, rrProcQueue.pop());
                            flag = true;
                            break;
                        }
                    }
                    if(!flag) {
                        rrProcQueue.addLast(rrProcQueue.pop());
                    }
   }

}