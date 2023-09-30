package daw.backend.route;

import daw.backend.controller.AudioDocumentController;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class AudioDocumentRoute
{
    public static void addRoutesTo(Router router)
    {
        if(router != null)
        {
            router.route(HttpMethod.GET, "/document/get/:id").handler(AudioDocumentController.getHandler());
//            router.route(HttpMethod.GET, "/document/getall").handler(SheetDocumentController.getAllHandler());
            router.route(HttpMethod.POST, "/document/post").consumes("*/json").handler(AudioDocumentController.createHandler());
            router.route(HttpMethod.PUT, "/document/put/:id").consumes("*/json").handler(AudioDocumentController.updateHandler());
            router.route(HttpMethod.DELETE, "/document/delete/:id").handler(AudioDocumentController.deleteHandler());
        }
    }
}
