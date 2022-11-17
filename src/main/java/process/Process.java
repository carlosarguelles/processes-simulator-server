package process;

import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;

@Data
public class Process {
    private long pid;
    private String user;
    private String name;
    private String fullName;
    private boolean priority;
    private long cpuTime;
    private int arrivalTime;
    private int burstTime;
    private ProcessState state;

    //ArrayList to save the times quantum of time assigned to the process.
    private ArrayList<Iteration> cpuTimes;


    public Process(ProcessHandle process) {
        this.pid = process.pid();
        var command = process.info().command().orElse("");
        this.fullName = command;
        var splitCommand = command.split("/");
        this.name = splitCommand[splitCommand.length - 1];
        this.user = process.info().user().orElse("");
        this.priority = process.info().user().orElse("").equals("root");
        var cpuTime = process.info().totalCpuDuration();
        this.cpuTime = cpuTime.map(Duration::getSeconds).orElse(0L);

        this.cpuTimes = new ArrayList<>();
        
    }

    public void execute() { }

    public void addIteration(Iteration iteration) {
        cpuTimes.add(iteration);
    }

    @Override
    public String toString() {
        String message = "PID: " + this.pid
        + "\nName: " + this.name
        + "\nUser: " + this.user
        + "\nPriority: " + this.priority
        + "\nCPU time: " + this.cpuTime
        + "\nEnd Time: " + getFinalTime()
        + "\n________________________________________\n";

        int timeCounter = 1;
        for (Iteration i : this.cpuTimes) {
            message += "\n\t ITERATIONS:";
            message += "\n\t: Iteration " + timeCounter + "\n\t\tStart: " + i.getStart()
            + "\n\t\tEnd: " + i.getEnd();
        }
        return message;
    }

    public Integer getFinalTime() {
        return (this.cpuTimes.size() > 0)? this.cpuTimes.get(cpuTimes.size() - 1).getEnd() : 0;
    }
}
