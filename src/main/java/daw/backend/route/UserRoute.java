package daw.backend.route;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import daw.backend.controller.UserController;

public class UserRoute
{
    public static void addRoutesTo(Router router)
    {
        if(router != null)
        {
            router.route(HttpMethod.GET, "/user/get/:id").handler(UserController.getHandler());
//            router.route(HttpMethod.GET, "/user/getall").handler(UserController.getAllHandler());
            router.route(HttpMethod.POST, "/user/post").consumes("*/json").handler(UserController.createHandler());
            router.route(HttpMethod.PUT, "/user/put/:id").consumes("*/json").handler(UserController.updateHandler());
            router.route(HttpMethod.DELETE, "/user/delete/:id").handler(UserController.deleteHandler());
        }
    }
}
