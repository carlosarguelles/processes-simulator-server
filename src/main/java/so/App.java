package so;

import io.javalin.Javalin;
import process.ProcessController;
import simulator.SimulatorController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> config.jsonMapper(JSON.getJsonMapper()));
        addRoutes(app);
        app.start(9090);
    }

    public static void addRoutes(Javalin app) {
        app.routes(() -> {
            path("process", () -> {
                get("all", ProcessController::all);
            });
            path("simulation", () -> {
                post("run", SimulatorController::simulate);
            });
        });
    }
}