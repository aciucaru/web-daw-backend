package daw.backend.route;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import daw.backend.controller.WorkspaceController;

public class WorkspaceRoute
{
    public static void addRoutesTo(Router router)
    {
        if(router != null)
        {
            router.route(HttpMethod.GET, "/workspace/get/:id").handler(WorkspaceController.getHandler());
//            router.route(HttpMethod.GET, "/workspace/getall").handler(WorkspaceController.getAllHandler());
            router.route(HttpMethod.POST, "/workspace/post").consumes("*/json").handler(WorkspaceController.createHandler());
            router.route(HttpMethod.PUT, "/workspace/put/:id").consumes("*/json").handler(WorkspaceController.updateHandler());
            router.route(HttpMethod.DELETE, "/workspace/delete/:id").handler(WorkspaceController.deleteHandler());
        }
    }
}
