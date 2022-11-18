package simulator;

import io.javalin.http.Context;
import process.ProcessList;

public class SimulatorController {
    public static void simulate(Context ctx) {
        var processes = ctx.bodyAsClass(ProcessList.class);
        var quantum = ctx.queryParamAsClass("quantum", Integer.class);
        var simulation = SimulatorService.simulate(processes, quantum.get());
        ctx.json(simulation);
    }
}
