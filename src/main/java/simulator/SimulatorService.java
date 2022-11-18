package simulator;

import process.ProcessList;
import process.Process;
import java.util.ArrayList;

public class SimulatorService {
    public static ArrayList<Process> simulate(ProcessList processes, Integer quantum) {
        for (Process process : processes) {
            var burstTime = quantum * process.getFullName().length();
            process.setBurstTime(burstTime);
            process.setCurrentBurstTime(burstTime);
        }
        return Simulator.roundRobin(processes, quantum);
    }

}
