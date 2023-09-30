package daw.backend.model.entity;

import jakarta.persistence.*;
import daw.backend.model.dto.WorkspaceDTO;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity(name = "WorkspaceEntity")
@Table(name="workspace")
public class WorkspaceEntity
{
    @Id @GeneratedValue
    private String id;

    @Column(name ="name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "workspace")
    private Set<AudioDocumentEntity> documents;

    @OneToMany(mappedBy = "allowedWorkspace")
    private Set<UserAllowedWorkspaceEntity> allowedWorkspaces;

    public WorkspaceEntity() { }

    public String getId() { return id; }

    public void setId(String id)
    {
        if(id != null)
            this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name)
    {
        if(name != null)
            this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description)
    {
        if(description != null)
            this.description = description;
    }

    public Set<AudioDocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<AudioDocumentEntity> documents) {
        this.documents = documents;
    }

    public Set<UserAllowedWorkspaceEntity> getAllowedWorkspaces() {
        return allowedWorkspaces;
    }

    public void setAllowedWorkspaces(Set<UserAllowedWorkspaceEntity> allowedWorkspaces) {
        this.allowedWorkspaces = allowedWorkspaces;
    }

    // adds a user to a workspace
    public void addUser(UserEntity user)
    {
        if(user != null)
        {
            UserAllowedWorkspaceEntity userAllowedWorkspace = new UserAllowedWorkspaceEntity(user, this, false);
            allowedWorkspaces.add(userAllowedWorkspace);
            user.getAllowedWorkspaces().add(userAllowedWorkspace);
        }
    }

    public void removeUser(UserEntity user)
    {
        if(user != null)
        {
            Iterator<UserAllowedWorkspaceEntity> iterator = allowedWorkspaces.iterator();

            while(iterator.hasNext())
            {
                UserAllowedWorkspaceEntity allowedWorkspace = iterator.next();

                if(allowedWorkspace.getAllowedWorkspace().equals(this) &&
                    allowedWorkspace.getWorkspaceUser().equals(user))
                {
                    iterator.remove(); // should be after all other lines?

                    allowedWorkspace.getWorkspaceUser()
                                    .getAllowedWorkspaces()
                                    .remove(allowedWorkspace);
                    allowedWorkspace.setWorkspaceUser(null);
                    allowedWorkspace.setAllowedWorkspace(null);
                }
            }
        }
    }

    public WorkspaceDTO mapToDTO()
    {
        return new WorkspaceDTO(this.id, this.name, this.description);
    }

    public void mapFromDTO(WorkspaceDTO workspaceDTO)
    {
        if(workspaceDTO != null)
        {
            setId(workspaceDTO.id());
            setName(workspaceDTO.name());
            setDescription(workspaceDTO.description());
        }
    }

    @Override
    public boolean equals(Object obj) // should be different implementation?
    {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        WorkspaceEntity workspace = (WorkspaceEntity) obj;
        return this.id.equals(workspace.id);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
        return Objects.hash(name, description);
    }
}
