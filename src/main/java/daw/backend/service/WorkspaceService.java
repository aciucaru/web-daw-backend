package daw.backend.service;

import daw.backend.model.dto.WorkspaceDTO;
import daw.backend.repo.WorkspaceDTORepo;
import io.vertx.core.Future;

import java.util.Optional;

public class WorkspaceService
{
    private WorkspaceDTORepo workspaceDTORepo;

    public WorkspaceService(WorkspaceDTORepo workspaceDTORepo)
    {
        if(workspaceDTORepo != null)
            this.workspaceDTORepo = workspaceDTORepo;
    }

    public Future<Optional<WorkspaceDTO>> getById(String id)
    {
        return workspaceDTORepo.getById(id);
    }

//    public Future<List<WorkspaceDTO>> getAll()
//    {
//        return workspaceDTORepo.getAll();
//    }

    public Future<WorkspaceDTO> create(WorkspaceDTO workspace)
    {
        return workspaceDTORepo.create(workspace);
    }

    public Future<WorkspaceDTO> update(String id, WorkspaceDTO newWorkspace)
    {
        return workspaceDTORepo.update(newWorkspace);
    }

    public Future<Void> delete(String id)
    {
        return workspaceDTORepo.delete(id);
    }
}
