package simulator;

import process.Process;
import process.ProcessState;
import java.util.ArrayList;

public class Simulator {
    public static ArrayList<Process> roundRobin(ArrayList<Process> processes, int quantum) {
        var pq = processes;
        var fq  = new ArrayList<Process>();
        while (!pq.isEmpty()) {
            for (int i = 0; i < pq.size(); i++) {
                pq.get(i).execute();
                pq.get(i).setBurstTime(Math.max(pq.get(i).getBurstTime() - quantum, 0));
                if (pq.get(i).getBurstTime() > 0) {
                    pq.get(i).setState(ProcessState.RUNNING);
                    pq.add(pq.get(i));
                } else {
                    pq.get(i).setState(ProcessState.DONE);
                    pq.remove(pq.get(i));
                }
                fq.add(pq.get(i));
            }
        }
        return fq;
    }
}
