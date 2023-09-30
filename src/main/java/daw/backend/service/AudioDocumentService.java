package daw.backend.service;

import daw.backend.model.dto.AudioDocumentDTO;
import daw.backend.repo.AudioDocumentDTORepo;
import io.vertx.core.Future;

import java.util.Optional;

public class AudioDocumentService
{
    private AudioDocumentDTORepo audioDocumentDTORepo;

    public AudioDocumentService(AudioDocumentDTORepo audioDocumentDTORepo)
    {
        if(audioDocumentDTORepo != null)
            this.audioDocumentDTORepo = audioDocumentDTORepo;
    }

    public Future<Optional<AudioDocumentDTO>> getById(String id)
    {
        return audioDocumentDTORepo.getById(id);
    }

//    public Future<List<SheetDocumentDTO>> getAll()
//    {
//        return sheetDocumentRepo.getAll();
//    }

    public Future<AudioDocumentDTO> create(AudioDocumentDTO document)
    {
        return audioDocumentDTORepo.create(document);
    }

    public Future<AudioDocumentDTO> update(String id, AudioDocumentDTO document)
    {
        return audioDocumentDTORepo.update(document);
    }

    public Future<Void> delete(String id)
    {
        return audioDocumentDTORepo.delete(id);
    }
}
