package process;

import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

@Data
public class Process {
    private long pid;
    private String user;
    private String name;
    private String fullName;
    private boolean priority;
    private long cpuTime;
    private long arrivalTime;
    private long burstTime;
    private long initialBurstTime;
    private ProcessState state;
    private Integer turnAround;
    private long executions;
    private long finalTime;

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
        this.executions = 0;
        this.state = ProcessState.READY;
    }

    public void execute(long quantum) throws IOException, InterruptedException {
        var path = Util.createFilePath("temp", this.pid + this.name);
        var f = new File(path);
        var writer = new FileWriter(f.toPath().toString());
        if (this.priority) {
            writer.write(this.name);
            Thread.sleep(this.burstTime);
            this.finalTime = this.burstTime;
            this.burstTime = 0;
            this.executions = 1;
            this.setState(ProcessState.DONE);
        } else if (this.burstTime > quantum) {
            var s = this.name.substring(0, (int) quantum / 1000);
            System.out.println(s);
            writer.write(s);
            this.burstTime = this.burstTime - quantum;
            this.executions++;
            this.finalTime = this.executions * quantum;
            Thread.sleep(quantum);
            this.setState(ProcessState.RUNNING);
        } else {
            this.burstTime = 0;
            this.setState(ProcessState.DONE);
        }
        writer.close();
    }
}
