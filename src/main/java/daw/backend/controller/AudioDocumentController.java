package daw.backend.controller;

import daw.backend.controller.constants.HttpHeaderValue;
import daw.backend.model.dto.AudioDocumentDTO;
import daw.backend.repo.DBSessionFactory;
import daw.backend.repo.AudioDocumentDTORepo;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;

import daw.backend.service.AudioDocumentService;


public class AudioDocumentController
{
    private static daw.backend.repo.DBSessionFactory DBSessionFactory = new DBSessionFactory();
    private static AudioDocumentDTORepo documentDTORepo = new AudioDocumentDTORepo(DBSessionFactory.getSessionFactory());
    private static AudioDocumentService documentService = new AudioDocumentService(documentDTORepo);

    public static Handler<RoutingContext> getHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");

            documentService.getById(id)
                            .onSuccess(optionalDocumentDTO ->
                            {
                                if(optionalDocumentDTO.isPresent())
                                {
                                    JsonObject documentJSON = JsonObject.mapFrom(optionalDocumentDTO.get());
                                    context.response()
                                        .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                        .end(documentJSON.encodePrettily());
                                }
                                else
                                    context.response().setStatusCode(404).end();
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }

    public static Handler<RoutingContext> createHandler()
    {
        return (RoutingContext context) ->
        {
            RequestBody body = context.body();
            AudioDocumentDTO document = body.asPojo(AudioDocumentDTO.class);

            documentService.create(document)
                            .onSuccess(documentDTO ->
                            {
                                JsonObject sheetDocumentJSON = JsonObject.mapFrom(documentDTO);
                                context.response()
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                    .end(sheetDocumentJSON.encodePrettily());
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
            AudioDocumentDTO document = body.asPojo(AudioDocumentDTO.class);

            documentService.update(id, document)
                            .onSuccess(documentDTO ->
                            {
                                JsonObject sheetDocumentJSON = JsonObject.mapFrom(documentDTO);
                                context.response()
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_JSON.value)
                                    .end(sheetDocumentJSON.encodePrettily());
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }

    public static Handler<RoutingContext> deleteHandler()
    {
        return (RoutingContext context) ->
        {
            String id = context.pathParam("id");
            documentService.delete(id)
                            .onSuccess(voidObj ->
                            {
                                context.response()
                                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValue.CONTENT_PLAIN_TEXT.value)
                                    .end("DELETE document successful");
                            })
                            .onFailure(error -> context.response().setStatusCode(500).end());
        };
    }
}
