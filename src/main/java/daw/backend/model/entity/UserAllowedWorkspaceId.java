package daw.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserAllowedWorkspaceId implements Serializable
{
    @Column(name = "workspace_id_fk")
    private String workspaceId;

    @Column(name = "user_id_fk")
    private String userId;

    public UserAllowedWorkspaceId() { }

    public UserAllowedWorkspaceId(String userId, String workspaceId)
    {
        if(userId != null)
            this.userId = userId;

        if(workspaceId != null)
            this.workspaceId = workspaceId;
    }

    public String getWorkspaceId() { return workspaceId; }

    public void setWorkspaceId(String workspaceId)
    {
        if(workspaceId != null)
            this.workspaceId = workspaceId;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId)
    {
        if(userId != null)
            this.userId = userId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        UserAllowedWorkspaceId idObj = (UserAllowedWorkspaceId) obj;
        return Objects.equals(userId, idObj.userId) &&
                Objects.equals(workspaceId, idObj.workspaceId);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
        return Objects.hash(userId, workspaceId);
    }
}
