package simulator;

import process.Process;
import java.util.ArrayList;
import process.Iteration;

public class Simulator {

    private static Integer sumOfProcessesTimes(ArrayList<Process> processes) {
        Integer sum = 0;
        for (Process process : processes) {
            sum += process.getCurrentBurstTime();
        }
        return sum;
    }

    public static ArrayList<Process> roundRobin(ArrayList<Process> processes, int quantum) {
        Integer currentQuantum = 0;
        Integer totalTime = sumOfProcessesTimes(processes);
        Iteration newIteration;
        while (currentQuantum < totalTime) {
            for (Process process : processes) {
                newIteration = new Iteration(currentQuantum, 0);
                Integer actualBurstTime = process.getCurrentBurstTime();
                if (actualBurstTime > 0) {
                    if (actualBurstTime < quantum) {
                        newIteration.setEnd(currentQuantum + actualBurstTime);
                        newIteration.setActualBurstTime(0);
                        process.setCurrentBurstTime(0);
                        currentQuantum += actualBurstTime;
                    } else {
                        newIteration.setEnd(currentQuantum + quantum);
                        Integer gotActualBurstTime = actualBurstTime - quantum;
                        newIteration.setActualBurstTime(gotActualBurstTime);
                        process.setCurrentBurstTime(gotActualBurstTime);
                        currentQuantum += quantum;
                    }
                    process.addIteration(newIteration);
                    process.setState();
                }
            }
        }

        Integer counter = 0;
        for (Process process : processes) {
            if (process.getFinalTime() != 0)
                System.out.println("El proceso " + counter + " terminó en T ->" + process.getFinalTime());
            counter++;
        }
        return processes;
    }

}
