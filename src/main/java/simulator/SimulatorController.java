package simulator;

import io.javalin.http.Context;

public class SimulatorController {
    public static void simulate(Context ctx) {
        var simulation = SimulatorService.simulate();
        ctx.json(simulation);
    }
}
