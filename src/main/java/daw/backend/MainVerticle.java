package daw.backend;

import daw.backend.route.UserRoute;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import daw.backend.route.AudioDocumentRoute;
import daw.backend.route.WorkspaceRoute;

public class MainVerticle extends AbstractVerticle
{
    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {
        System.out.println("Vert.x has started");

        Router router = Router.router(vertx);

        router.post().handler(BodyHandler.create());
        router.put().handler(BodyHandler.create());

        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        UserRoute.addRoutesTo(router);
        AudioDocumentRoute.addRoutesTo(router);
        WorkspaceRoute.addRoutesTo(router);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888, http ->
                {
                    if (http.succeeded())
                    {
                        startPromise.complete();
                        System.out.println("HTTP server started on port 8888");
                    }
                    else
                        startPromise.fail(http.cause());
                }
            );
    }

    @Override
    public void stop() { System.out.println("Vert.x has stopped"); }
}
