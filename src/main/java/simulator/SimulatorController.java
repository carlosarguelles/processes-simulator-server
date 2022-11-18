package simulator;

import io.javalin.http.Context;
import io.javalin.websocket.WsConnectContext;
import process.ProcessList;
import process.ProcessService;
import roundrobin.Scheduler;

public class SimulatorController {
    public static void simulate(Context ctx) {
        //var processes = ctx.bodyAsClass(ProcessList.class);
        //var quantum = ctx.queryParamAsClass("quantum", Integer.class);
        //var simulation = SimulatorService.simulate(processes, quantum.get());
        //ctx.json(simulation);
    }

    public static void simulateAsync(WsConnectContext ctx) {
        var processes = ProcessService.all(4);
        long quantum = 1000;
        for (var p : processes) {
            long burstTime = quantum * p.getName().length();
            p.setBurstTime(burstTime);
        }
        var simulator = new Scheduler(quantum, processes, ctx);
        simulator.start();
    }
}
