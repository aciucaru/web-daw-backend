package daw.backend.model.entity;

import jakarta.persistence.*;
import daw.backend.model.dto.AudioDocumentDTO;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="sheet_document")
public class AudioDocumentEntity
{
    @Id @GeneratedValue
    private String id;

    @Column(name = "data")
    private String data;

    @ManyToOne @JoinColumn(name = "workspace_id_fk", nullable = false)
    private WorkspaceEntity workspace;

    @OneToMany(mappedBy = "document")
    Set<AudioDocumentPermissionEntity> documentPermissions;


    public AudioDocumentEntity() { }

    public String getId() { return id; }
    public String getData() { return data; }

    public void setId(String id)
    {
        if(id != null)
            this.id = id;
    }

    public void setData(String data)
    {
        if(data != null)
            this.data = data;
    }

    public WorkspaceEntity getWorkspace() { return workspace; }

    public void setWorkspace(WorkspaceEntity workspace)
    {
        this.workspace = workspace;
    }

    public Set<AudioDocumentPermissionEntity> getDocumentPermissions() { return documentPermissions; }

    public void setDocumentPermissions(Set<AudioDocumentPermissionEntity> documentPermissions)
    {
        this.documentPermissions = documentPermissions;
    }

    public void addUser(UserEntity user)
    {
        if(user != null)
        {
            AudioDocumentPermissionEntity documentPermission = new AudioDocumentPermissionEntity(this, user);
            documentPermissions.add(documentPermission);
            user.getDocumentPermissions().add(documentPermission);
        }
    }

    public void removeUser(UserEntity user)
    {
        if(user != null)
        {
            Iterator<AudioDocumentPermissionEntity> iterator = documentPermissions.iterator();

            while(iterator.hasNext())
            {
                AudioDocumentPermissionEntity docPermission = iterator.next();

                if(docPermission.getDocument().equals(this) &&
                    docPermission.getUser().equals(user))
                {
                    iterator.remove(); // should be after all other lines?

                    docPermission.getUser()
                                        .getDocumentPermissions()
                                        .remove(docPermission);
                    docPermission.setUser(null);
                    docPermission.setPermissionType(null);
                }
            }
        }
    }

    public AudioDocumentDTO mapToDTO()
    {
        return new AudioDocumentDTO(this.id, this.data);
    }

    public void mapFromDTO(AudioDocumentDTO audioDocumentDTO)
    {
        if(audioDocumentDTO != null)
        {
            setId(audioDocumentDTO.id());
            setData(audioDocumentDTO.data());
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        AudioDocumentEntity document = (AudioDocumentEntity) obj;
        return this.id.equals(document.id);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
        return Objects.hash(data);
    }
}
