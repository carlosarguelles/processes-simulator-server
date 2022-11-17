package simulator;

import process.Process;
import process.ProcessState;
import java.util.ArrayList;
import process.Iteration;

public class Simulator {

    private static Integer sumOfProcessesTimes(ArrayList<Process> processes) {
        Integer sum = 0;
        for (Process process : processes) {
            sum += process.getBurstTime();
        }
        return sum;
    }

    public static ArrayList<Process> roundRobin(ArrayList<Process> processes, int quantum) {
        var pQueue = processes;
        Integer currentQuantum = 0;
        Integer totalTime = sumOfProcessesTimes(pQueue);
        Iteration newIteration;

        while(currentQuantum < totalTime) {
            for (int i = 0; i < pQueue.size(); i++) {

                newIteration = new Iteration(currentQuantum, 0);
                Integer actualBurstTime = pQueue.get(i).getBurstTime();
                if(actualBurstTime > 0) {

                    if(actualBurstTime < quantum) {

                        newIteration.setEnd(currentQuantum + actualBurstTime);
                        newIteration.setActualBurstTime(0);
                        pQueue.get(i).setBurstTime(0);
                        currentQuantum += actualBurstTime;
    
                    }
                    else {
                        newIteration.setEnd(currentQuantum + quantum);
                        Integer gotActualBurstTime = actualBurstTime - quantum;
                        newIteration.setActualBurstTime(gotActualBurstTime);
                        pQueue.get(i).setBurstTime(gotActualBurstTime);
                        currentQuantum += quantum;
                    }
                    pQueue.get(i).addIteration(newIteration);
                }
            }
        }


        Integer counter = 0;
        for (Process process : pQueue) {
            if (process.getFinalTime() != 0) 
                System.out.println("El proceso " + counter + " terminó en T ->" + process.getFinalTime());
            counter++;
        }
        return pQueue;
    }

}
