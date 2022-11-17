package simulator;

import process.ProcessService;
import process.Process;

import java.util.ArrayList;
import java.util.Random;

public class SimulatorService {
    public static ArrayList<Process> simulate() {
        var processes = ProcessService.all(10);
        for (int i = 0; i < processes.size(); i++) {
            processes.get(i).setArrivalTime(i + 2);
            processes.get(i).setBurstTime(new Random().nextInt(5));
        }
        return Simulator.roundRobin(processes, 3);
    }

}
