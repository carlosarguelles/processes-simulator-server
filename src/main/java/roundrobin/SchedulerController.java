package roundrobin;

import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsMessageContext;
import process.ProcessList;


public class SchedulerController {
    private record WebSocketRequest(long quantum, ProcessList processList) {
    }

    public static void bootstrap(WsConfig ws) {
        ws.onConnect(ctx -> ctx.send("Connection established"));
        ws.onMessage(SchedulerController::simulate);
        ws.onClose(ctx -> ctx.send("bye"));
    }

    public static void simulate(WsMessageContext ctx) {
        WebSocketRequest data;
        data = ctx.messageAsClass(WebSocketRequest.class);
        int counter = 1;
        for (var p : data.processList) {
            long burstTime = data.quantum * p.getName().length();
            p.setInitialBurstTime(burstTime);
            p.setArrivalTime(counter);
            p.setBurstTime(burstTime);
            counter++;
        }
        var simulator = new Scheduler(data.quantum, data.processList, ctx);
        simulator.start();
    }
}
