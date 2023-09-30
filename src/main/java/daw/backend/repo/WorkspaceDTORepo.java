package daw.backend.repo;

import daw.backend.model.dto.WorkspaceDTO;
import daw.backend.model.entity.WorkspaceEntity;
import io.vertx.core.Future;
import jakarta.persistence.criteria.*;
import org.hibernate.reactive.stage.Stage;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class WorkspaceDTORepo
{
    private Stage.SessionFactory sessionFactory;

    public WorkspaceDTORepo(Stage.SessionFactory sessionFactory)
    {
        if(sessionFactory != null)
            this.sessionFactory = sessionFactory;
    }

    public Future<Optional<WorkspaceDTO>> getById(String id)
    {
        CompletionStage<WorkspaceEntity> result = sessionFactory.withTransaction( (session, transaction) ->
                                                                    session.find(WorkspaceEntity.class, id)
                                                                );
        Future<Optional<WorkspaceDTO>> future = Future.fromCompletionStage(result)
                                                        .map(workspaceEntity -> Optional.ofNullable(workspaceEntity))
                                                        .map(optionalWorkspaceEntity ->
                                                            optionalWorkspaceEntity.map(workspaceEntity -> workspaceEntity.mapToDTO())
                                                        );
        return future;
    }

//    public Future<List<WorkspaceDTO>> getAll()
//    {
//        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
//
//        CriteriaQuery<WorkspaceEntity> criteriaQuery = criteriaBuilder.createQuery(WorkspaceEntity.class);
//        Root<WorkspaceEntity> root = criteriaQuery.from(WorkspaceEntity.class);
//
//        Predicate predicate = criteriaBuilder.equal(root.get("id"), "anyID***????");
//        criteriaQuery.where(predicate);
//
//        CompletionStage<List<WorkspaceEntity>> result = sessionFactory.withTransaction( (session, transaction) ->
//                                                        session.createQuery(criteriaQuery).getResultList()
//                                                    );
//        Future<List<WorkspaceDTO>> future =
//            Future.fromCompletionStage(result)
//                    .map( workspaceEntityList ->
//                            workspaceEntityList.stream()
//                                                .map(workspaceEntity -> workspaceEntity.mapToDTO())
//                                                .collect(Collectors.toList())
//                    );
//        return future;
//    }

    public Future<WorkspaceDTO> create(WorkspaceDTO workspaceDTO)
    {
        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.mapFromDTO(workspaceDTO);

        CompletionStage<Void> result = sessionFactory.withTransaction( (session, transaction) ->
                                                        session.persist(workspaceEntity)
                                                    );
        Future<WorkspaceDTO> future = Future.fromCompletionStage(result)
                                            .map(voidObj -> workspaceEntity.mapToDTO());
        return future;
    }

    public Future<WorkspaceDTO> update(WorkspaceDTO newWorkspaceDTO)
    {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaUpdate<WorkspaceEntity> criteriaUpdate =criteriaBuilder.createCriteriaUpdate(WorkspaceEntity.class);
        Root<WorkspaceEntity> root = criteriaUpdate.from(WorkspaceEntity.class);

        criteriaUpdate.set("name", newWorkspaceDTO.name());
        criteriaUpdate.set("description", newWorkspaceDTO.description());

        Predicate predicate = criteriaBuilder.equal(root.get("id"), newWorkspaceDTO.id());
        criteriaUpdate.where(predicate);

        CompletionStage<Integer> result = sessionFactory.withTransaction( (session, transaction) ->
                                                session.createQuery(criteriaUpdate).executeUpdate()
                                            );
        Future<WorkspaceDTO> future = Future.fromCompletionStage(result)
                                            .map(intResult -> newWorkspaceDTO);
        return future;
    }

    public Future<Void> delete(String id)
    {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaDelete<WorkspaceEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(WorkspaceEntity.class);
        Root<WorkspaceEntity> root = criteriaDelete.from(WorkspaceEntity.class);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
        criteriaDelete.where(predicate);

        CompletionStage<Integer> result = sessionFactory.withTransaction( (session, transaction) ->
                                                session.createQuery(criteriaDelete).executeUpdate()
                                            );
        Future<Void> future = Future.fromCompletionStage(result)
                                    .compose(intResult -> Future.succeededFuture());
        return future;
    }
}
