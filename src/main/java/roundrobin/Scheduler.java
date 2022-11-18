package roundrobin;

import io.javalin.websocket.WsConnectContext;
import process.Process;
import process.ProcessState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scheduler extends Thread {
    private final long quantum;
    private final List<Process> queue;
    private final List<Process> done;
    private final WsConnectContext ctx;

    private record SimulationResponse(List<Process> queue, List<Process> done) {
    }

    public Scheduler(long quantum, List<Process> processes, WsConnectContext ctx) {
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
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                queue.get(i).execute(quantum);

                if (queue.get(i).getState().equals(ProcessState.DONE)) {
                    done.add(queue.get(i));
                }

                if (queue.get(i).getState().equals(ProcessState.RUNNING)) {
                    queue.add(queue.get(i));
                }

                queue.remove(0);
                ctx.send(new SimulationResponse(queue, done));
            }
        }
    }
}
