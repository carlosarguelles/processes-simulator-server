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
    private long turnAround;
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

    public String toStringFile() {
        String message = this.pid + ";"
        + this.fullName + ";"
        + this.name + ";"
        + this.user + ";"
        + this.priority + ";"
        + this.cpuTime + ";"
        + this.executions + ";"
        + this.state;

        return message;
    }

    public void execute(long quantum) throws IOException, InterruptedException {
        var path = Util.createFilePath("temp", this.name + " (" + this.pid + ")");
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
            var charactersToWrite = Util.millisToSeconds(this.initialBurstTime - this.burstTime);
            var s = this.name.substring(0, Math.min(charactersToWrite, this.name.length()));
            System.out.println(s);
            writer.write(s);
            this.burstTime = this.burstTime - quantum;
            this.executions++;
            this.finalTime = this.executions * quantum;
            Thread.sleep(quantum);
            this.setState(ProcessState.RUNNING);
        } else {
            this.burstTime = 0;
            writer.write(this.name);
            this.setState(ProcessState.DONE);
        }
        setTurnAround();
        writer.close();
    }

    public void setTurnAround() {
        this.turnAround = this.finalTime - this.arrivalTime;
    }
}
