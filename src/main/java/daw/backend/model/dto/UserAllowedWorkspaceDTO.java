package daw.backend.model.dto;

public record UserAllowedWorkspaceDTO(Integer id,
                                    Boolean canAuthorizeOthers,
                                    String userIdFk,
                                    String workspaceIdFk)
{
}
