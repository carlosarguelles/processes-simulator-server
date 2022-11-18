package roundrobin;

import io.javalin.websocket.WsMessageContext;
import process.Process;
import process.ProcessState;
import process.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scheduler extends Thread {
    private final long quantum;
    private final List<Process> queue;
    private final List<Process> done;
    private final WsMessageContext ctx;

    private record SimulationResponse(List<Process> queue, List<Process> done) {
    }

    public Scheduler(long quantum, List<Process> processes, WsMessageContext ctx) {
        this.quantum = quantum;
        this.queue = processes;
        this.done = new ArrayList<>();
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            simulate();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void simulate() throws IOException, InterruptedException {
        Util.removeFilesFromTempFolder();
        while (!queue.isEmpty()) {
            queue.get(0).execute(quantum);

            if (queue.get(0).getState().equals(ProcessState.DONE)) {
                done.add(queue.get(0));
            }

            if (queue.get(0).getState().equals(ProcessState.RUNNING)) {
                queue.add(queue.get(0));
            }

            queue.remove(0);

            ctx.send(new SimulationResponse(queue, done));
        }
    }
}
