package daw.backend.controller;

import daw.backend.controller.constants.HttpHeaderValue;
import daw.backend.model.dto.UserDTO;
import daw.backend.repo.DBSessionFactory;
import daw.backend.repo.UserDTORepo;
import daw.backend.service.UserService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.http.HttpHeaders;


public class UserController
{
    private static daw.backend.repo.DBSessionFactory DBSessionFactory = new DBSessionFactory();
    private static UserDTORepo userDTORepo = new UserDTORepo(DBSessionFactory.getSessionFactory());
    private static UserService userService = new UserService(userDTORepo);

    public static Handler<RoutingContext> getHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");

            userService.getById(id)
                        .onSuccess(optionalUserDTO ->
                        {
                            if(optionalUserDTO.isPresent())
                            {
                                JsonObject userJSON = JsonObject.mapFrom(optionalUserDTO.get()); // for single objects JsonObject must be used
                                context.response()
                                    .setStatusCode(200)
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                    .end(userJSON.encodePrettily());
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
//            List<UserDTO> userArray = userService.getAll();
//            JsonArray userJSONArray = new JsonArray(userArray); // for arrays JsonArray must be used
//
//            context.response()
//                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
//                .end(userJSONArray.encodePrettily());
//        };
//    }

    public static Handler<RoutingContext> createHandler()
    {
        return (RoutingContext context) ->
        {
            RequestBody body = context.body();
            UserDTO user = body.asPojo(UserDTO.class);

            userService.create(user)
                        .onSuccess(userDTO ->
                        {
                            JsonObject userJSON = JsonObject.mapFrom(userDTO);
                            context.response()
                                .setStatusCode(201)
                                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                .end(userJSON.encodePrettily());
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
            UserDTO user = body.asPojo(UserDTO.class);

            userService.update(user)
                        .onSuccess(userDTO ->
                        {
                            JsonObject userJSON = JsonObject.mapFrom(userDTO);
                            context.response()
                                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                .end(userJSON.encodePrettily());
                        })
                        .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }

    public static Handler<RoutingContext> deleteHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");

            userService.delete(id)
                        .onSuccess(voidObj ->
                        {
                            context.response()
                                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_PLAIN_TEXT.value)
                                .end("DELETE user successful");
                        })
                        .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }
}
