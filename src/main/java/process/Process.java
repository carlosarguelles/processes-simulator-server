package process;

import lombok.Data;

import java.time.Duration;

@Data
public class Process {
    private long pid;
    private String user;
    private String name;
    private String fullName;
    private boolean priority;
    private long cpuTime;

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
    }
}
