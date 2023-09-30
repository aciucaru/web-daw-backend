package daw.backend.repo;

import daw.backend.model.dto.AudioDocumentDTO;
import daw.backend.model.entity.AudioDocumentEntity;
import io.vertx.core.Future;
import jakarta.persistence.criteria.*;
import org.hibernate.reactive.stage.Stage;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class AudioDocumentDTORepo
{
    private Stage.SessionFactory sessionFactory;

    public AudioDocumentDTORepo(Stage.SessionFactory sessionFactory)
    {
        if(sessionFactory != null)
            this.sessionFactory = sessionFactory;
    }

    public Future<Optional<AudioDocumentDTO>> getById(String id)
    {
        CompletionStage<AudioDocumentEntity> result = sessionFactory.withTransaction( (session, transaction) ->
                                                                                        session.find(AudioDocumentEntity.class, id)
                                                                                    );
        Future<Optional<AudioDocumentDTO>> future = Future.fromCompletionStage(result)
                                                        .map(audioDocumentEntity -> Optional.ofNullable(audioDocumentEntity))
                                                        .map(audioDocumentEntity ->
                                                            audioDocumentEntity.map(audioDocEntity -> audioDocEntity.mapToDTO())
                                                        );
        return future;
    }

    public Future<AudioDocumentDTO> create(AudioDocumentDTO audioDocumentDTO)
    {
        AudioDocumentEntity audioDocumentEntity = new AudioDocumentEntity();
        audioDocumentEntity.mapFromDTO(audioDocumentDTO);

        CompletionStage<Void> result = sessionFactory.withTransaction( (session, transaction) -> session.persist(audioDocumentEntity));
        Future<AudioDocumentDTO> future = Future.fromCompletionStage(result)
                                                .map(voidObj -> audioDocumentEntity.mapToDTO());
        return future;
    }

    public Future<AudioDocumentDTO> update(AudioDocumentDTO newAudioDocumentDTO)
    {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaUpdate<AudioDocumentEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(AudioDocumentEntity.class);
        Root<AudioDocumentEntity> root = criteriaUpdate.from(AudioDocumentEntity.class);

        criteriaUpdate.set("data", newAudioDocumentDTO.data());

        Predicate predicate = criteriaBuilder.equal(root.get("id"), newAudioDocumentDTO.id());
        criteriaUpdate.where(predicate);

        CompletionStage<Integer> result = sessionFactory.withTransaction( (session, transaction) ->
                                                            session.createQuery(criteriaUpdate).executeUpdate()
                                                        );
        Future<AudioDocumentDTO> future = Future.fromCompletionStage(result).map(intResult -> newAudioDocumentDTO);
        return future;
    }

    public Future<Void> delete(String id)
    {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaDelete<AudioDocumentEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(AudioDocumentEntity.class);
        Root<AudioDocumentEntity> root = criteriaDelete.from(AudioDocumentEntity.class);

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
