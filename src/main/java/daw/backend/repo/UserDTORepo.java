package daw.backend.repo;

import daw.backend.model.dto.UserDTO;
import daw.backend.model.entity.UserEntity;
import io.vertx.core.Future;
import jakarta.persistence.criteria.*;
import org.hibernate.reactive.stage.Stage;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class UserDTORepo
{
    private Stage.SessionFactory sessionFactory;

    public UserDTORepo(Stage.SessionFactory sessionFactory)
    {
        if(sessionFactory != null)
            this.sessionFactory = sessionFactory;
    }

    public Future<Optional<UserDTO>> getById(String id)
    {
        CompletionStage<UserEntity> userResult = sessionFactory.withTransaction( (session, transaction) ->
                                                                    session.find(UserEntity.class, id)
                                                                );
        Future<Optional<UserDTO>> future = Future.fromCompletionStage(userResult)
                                                .map(userEntity -> Optional.ofNullable(userEntity))
                                                .map(optionalUserEntity ->
                                                        optionalUserEntity.map(userEntity -> userEntity.mapToDTO() )
                                                );

        return future;
    }

//    public Future<List<UserDTO>> getAll()
//    {
//        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
//
//        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
//        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
//
//        Predicate predicate = criteriaBuilder.equal(root.get("id"), "anyId****????");
//        criteriaQuery.where(predicate);
//
//        CompletionStage<List<UserEntity>> result = sessionFactory.withTransaction( (session, transaction) ->
//                                                        session.createQuery(criteriaQuery).getResultList()
//                                                    );
//        Future<List<UserDTO>> future =
//            Future.fromCompletionStage(result)
//                    .map( userEntityList ->
//                            userEntityList.stream()
//                                            .map(userEntity -> userEntity.mapToDTO())
//                                            .collect(Collectors.toList())
//                    );
//        return future;
//    }

    public Future<UserDTO> create(UserDTO userDTO)
    {
        UserEntity userEntity = new UserEntity();
        userEntity.mapFromDTO(userDTO);

        CompletionStage<Void> result = sessionFactory.withTransaction( (session, transaction) ->
                                                            session.persist(userEntity)
                                                        );
        Future<UserDTO> future = Future.fromCompletionStage(result)
                                        .map(voidObj -> userEntity.mapToDTO());
        return future;
    }

    public Future<UserDTO> update(UserDTO newUserDTO)
    {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaUpdate<UserEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(UserEntity.class);
        Root<UserEntity> root = criteriaUpdate.from(UserEntity.class);

        criteriaUpdate.set("username", newUserDTO.username());
        criteriaUpdate.set("firstName", newUserDTO.firstName());
        criteriaUpdate.set("surname", newUserDTO.surname());

        Predicate predicate = criteriaBuilder.equal(root.get("id"), newUserDTO.id());
        criteriaUpdate.where(predicate);

        CompletionStage<Integer> result = sessionFactory.withTransaction( (session, transaction) ->
                                                            session.createQuery(criteriaUpdate).executeUpdate()
                                                        );
        Future<UserDTO> future = Future.fromCompletionStage(result)
                                        .map(intResult -> newUserDTO);
        return future;
    }

    public Future<Void> delete(String id)
    {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaDelete<UserEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(UserEntity.class);
        Root<UserEntity> root = criteriaDelete.from(UserEntity.class);

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
