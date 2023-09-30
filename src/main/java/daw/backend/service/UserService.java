package daw.backend.service;

import daw.backend.model.dto.UserDTO;
import daw.backend.repo.UserDTORepo;
import io.vertx.core.Future;

import java.util.Optional;

public class UserService
{
    UserDTORepo userDTORepo;

    public UserService(UserDTORepo userDTORepo)
    {
        if(userDTORepo != null)
            this.userDTORepo = userDTORepo;
    }

    public Future<Optional<UserDTO>> getById(String id)
    {
        return userDTORepo.getById(id);
    }

//    public Future<List<UserDTO>> getAll() // ????
//    {
//        return userDTORepo.getAll();
//    }

    public Future<UserDTO> create(UserDTO user)
    {
        return userDTORepo.create(user);
    }

    public Future<UserDTO> update(UserDTO newUserDTO)
    {
        return userDTORepo.update(newUserDTO);
    }

    // variant with Principal authorization
//    public Future<UserDTO> update(UserDTO newUserDTO)
//    {
//        Future<UserDTO> future =
//            userDTORepo.getById(newUserDTO.id())
//                        .compose(result ->
//                            {
//                                if(result.isEmpty())
//                                    return Future.failedFuture(new RuntimeException());
//                                else
//                                {
//                                    UserDTO userDTO = result.get();
//                                    return userDTORepo.update(newUserDTO); // correct
//                                }
//                            }
//                        );
//        return future;
//    }

    public Future<Void> delete(String id)
    {
        return userDTORepo.delete(id);
    }

    // variant with Principal authorization
//    public Future<Void> delete(String id)
//    {
//        Future<Void> future =
//            userDTORepo.getById(id)
//                        .compose(result ->
//                            {
//                                if(result.isEmpty())
//                                    return Future.failedFuture(new RuntimeException());
//                                else
//                                {
//                                    UserDTO userDTO = result.get();
//                                    return userDTORepo.delete(id);
//                                }
//                            }
//                        );
//    }
}
