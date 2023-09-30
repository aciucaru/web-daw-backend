package daw.backend.model.entity;

import jakarta.persistence.*;

import java.util.Objects;


@Entity(name = "UserAllowedWorkspaceEntity")
@Table(name="user_allowed_workspace")
public class UserAllowedWorkspaceEntity
{
    @EmbeddedId
    private UserAllowedWorkspaceId id;

    @Column(name = "can_authorize_others")
    private Boolean canAuthorizeOthers;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
//    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    private UserEntity workspaceUser;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("workspaceId")
//    @JoinColumn(name = "workspace_id_fk", referencedColumnName = "id")
    private WorkspaceEntity allowedWorkspace;

    public UserAllowedWorkspaceEntity() { }

    public UserAllowedWorkspaceEntity(UserEntity user, WorkspaceEntity workspace, Boolean canAuthorizeOthers)
    {
        if(user != null && workspace != null)
        {
            this.workspaceUser = user;
            this.allowedWorkspace = workspace;
            this.id = new UserAllowedWorkspaceId(user.getId(), workspace.getId());
        }

        if(canAuthorizeOthers != null)
            this.canAuthorizeOthers = canAuthorizeOthers;
        else
            this.canAuthorizeOthers = false;
    }

    public UserAllowedWorkspaceId getId() { return id; }

    public void setId(UserAllowedWorkspaceId id)
    {
        if(id != null)
            this.id = id;
    }

    public Boolean getCanAuthorizeOthers() { return canAuthorizeOthers; }

    public void setCanAuthorizeOthers(Boolean canAuthorizeOthers)
    {
        if(canAuthorizeOthers != null)
            this.canAuthorizeOthers = canAuthorizeOthers;
    }

    public UserEntity getWorkspaceUser() { return workspaceUser; }

    public void setWorkspaceUser(UserEntity workspaceUser)
    {
        if(workspaceUser != null)
            this.workspaceUser = workspaceUser;
    }

    public WorkspaceEntity getAllowedWorkspace() { return allowedWorkspace; }

    public void setAllowedWorkspace(WorkspaceEntity allowedWorkspace)
    {
        if(allowedWorkspace != null)
            this.allowedWorkspace = allowedWorkspace;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        UserAllowedWorkspaceEntity userAllowedWorkspace = (UserAllowedWorkspaceEntity) obj;
        return Objects.equals(workspaceUser, userAllowedWorkspace.workspaceUser) &&
                Objects.equals(allowedWorkspace, userAllowedWorkspace.allowedWorkspace);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
//        return Objects.hash(canAuthorizeOthers, workspaceUser, allowedWorkspace);
        return Objects.hash(workspaceUser, allowedWorkspace);
    }
}
