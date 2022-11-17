package process;

import java.util.ArrayList;

public class ProcessService {
    public static ArrayList<Process> all() throws SecurityException, UnsupportedOperationException {
        return all(null);
    }

    public static ArrayList<Process> all(Integer limit) throws SecurityException, UnsupportedOperationException {
        var ps = ProcessHandle.allProcesses();
        var processes = new ArrayList<Process>();
        for (ProcessHandle p : ps.toList()) {
            processes.add(new Process(p));
        }
        return limit != null ? new ArrayList<>(processes.subList(0, limit)) : processes;
    }
}
