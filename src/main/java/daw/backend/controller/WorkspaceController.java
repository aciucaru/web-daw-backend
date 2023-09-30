package daw.backend.controller;


import daw.backend.controller.constants.HttpHeaderValue;
import daw.backend.model.dto.WorkspaceDTO;
import daw.backend.repo.DBSessionFactory;
import daw.backend.repo.WorkspaceDTORepo;
import daw.backend.service.WorkspaceService;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;

public class WorkspaceController
{
    private static daw.backend.repo.DBSessionFactory DBSessionFactory = new DBSessionFactory();
    private static WorkspaceDTORepo workspaceDTORepo = new WorkspaceDTORepo(DBSessionFactory.getSessionFactory());
    private static WorkspaceService workspaceService = new WorkspaceService(workspaceDTORepo);

    public static Handler<RoutingContext> getHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");

            workspaceService.getById(id)
                            .onSuccess(optionalWorkspaceDTO ->
                            {
                                if(optionalWorkspaceDTO.isPresent())
                                {
                                    JsonObject workspaceJSONBody = JsonObject.mapFrom(optionalWorkspaceDTO.get());

                                    context.response()
                                            .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                            .end(workspaceJSONBody.encodePrettily());
                                }
                                else
                                    context.response().setStatusCode(404).end();
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }

//    public static Handler<RoutingContext> getAllHandler()
//    {
//        return (RoutingContext context) ->
//        {
//            List<WorkspaceDTO> workspaceArray = workspaceService.getAll();
//            JsonArray workspaceJSONArray = new JsonArray(workspaceArray);
//
//            context.response()
//                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
//                .end(workspaceJSONArray.encodePrettily());
//        };
//    }

    public static Handler<RoutingContext> createHandler()
    {
        return (RoutingContext context) ->
        {
            RequestBody body = context.body();
            WorkspaceDTO workspace = body.asPojo(WorkspaceDTO.class);

            workspaceService.create(workspace)
                            .onSuccess(workspaceDTO ->
                            {
                                JsonObject workspaceJSON = JsonObject.mapFrom(workspaceDTO);

                                context.response()
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                    .end(workspaceJSON.encodePrettily());
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }

    public static Handler<RoutingContext> updateHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");
            RequestBody body = context.body();
            WorkspaceDTO workspace = body.asPojo(WorkspaceDTO.class);

            workspaceService.update(id, workspace)
                            .onSuccess(workspaceDTO ->
                            {
                                JsonObject workspaceJSON = JsonObject.mapFrom(workspaceDTO);

                                context.response()
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                    .end(workspaceJSON.encodePrettily());
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }

    public static Handler<RoutingContext> deleteHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");

            workspaceService.delete(id)
                            .onSuccess(voidObj ->
                            {
                                context.response()
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_PLAIN_TEXT.value)
                                    .end("DELETE workspace successful");
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }
}
