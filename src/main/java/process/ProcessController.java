package process;

import io.javalin.http.Context;

public class ProcessController {
    public static void all(Context ctx) {
        var limit = ctx.queryParamAsClass("limit", Integer.class).allowNullable();
        var processList = ProcessService.all(limit.get());
        ctx.status(200);
        ctx.json(processList);
    }
}
