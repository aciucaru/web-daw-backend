package daw.backend.model.dto;

import daw.backend.model.DocumentPermissionType;

public record AudioDocumentPermissionDTO(Integer id,
                                         String documentKey, // hashed with user public key
                                         DocumentPermissionType documentPermissionType,
                                         Boolean canAuthorizeOthers,
                                         String documentIdFk,
                                         String userIdFk)
{
}
