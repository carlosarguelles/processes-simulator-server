package so;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import process.ProcessController;
import roundrobin.SchedulerController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.jsonMapper(JSON.getJsonMapper());
            config.plugins.enableCors(cors -> cors.add(CorsPluginConfig::anyHost));
        });
        addRoutes(app);
        app.start(9090);
    }

    public static void addRoutes(Javalin app) {
        app.routes(() -> {
            path("process", () -> {
                get("all", ProcessController::all);
            });
        });
        app.ws("runAsync", SchedulerController::bootstrap);
    }
}