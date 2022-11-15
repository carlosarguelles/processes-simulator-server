package process;

import java.util.ArrayList;
import java.util.List;

public class ProcessService {
    public static List<Process> all() throws SecurityException, UnsupportedOperationException {
        return all(null);
    }
    public static List<Process> all(Integer limit) throws SecurityException, UnsupportedOperationException {
        var ps = ProcessHandle.allProcesses();
        var processes = new ArrayList<Process>();
        for (ProcessHandle p : ps.toList()) {
            processes.add(new Process(p));
        }
        return limit != null ? processes.subList(0, limit): processes;
    }
}
