package daw.backend.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.Cache;
import daw.backend.model.dto.UserDTO;

import java.util.Objects;
import java.util.Set;

@Entity(name = "UserEntity")
@Table(name="user")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity
{
    @Id @GeneratedValue
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "user")
    private Set<AudioDocumentPermissionEntity> documentPermissions;

    @OneToMany(mappedBy = "workspaceUser")
    private Set<UserAllowedWorkspaceEntity> allowedWorkspaces;

    public UserEntity() { }

    public String getId() { return id; }

    public void setId(String id)
    {
        if(id != null)
            this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username)
    {
        if(username != null)
            this.username = username;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName)
    {
        if(firstName != null)
            this.firstName = firstName;
    }

    public String getSurname() { return surname; }

    public void setSurname(String surname)
    {
        if(surname != null)
            this.surname = surname;
    }

    public Set<AudioDocumentPermissionEntity> getDocumentPermissions() { return documentPermissions; }

    public void setDocumentPermissions(Set<AudioDocumentPermissionEntity> documentPermissions)
    {
        if(documentPermissions != null)
            this.documentPermissions = documentPermissions;
    }

    public Set<UserAllowedWorkspaceEntity> getAllowedWorkspaces() { return allowedWorkspaces; }

    public void setAllowedWorkspaces(Set<UserAllowedWorkspaceEntity> allowedWorkspaces)
    {
        if(allowedWorkspaces != null)
            this.allowedWorkspaces = allowedWorkspaces;
    }

    public UserDTO mapToDTO()
    {
        return new UserDTO(this.id, this.username, this.firstName, this.surname);
    }

    public void mapFromDTO(UserDTO userDTO)
    {
        if(userDTO != null)
        {
            setId(userDTO.id());
            setUsername(userDTO.username());
            setFirstName(userDTO.firstName());
            setSurname(userDTO.surname());
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        UserEntity user = (UserEntity) obj;
        return this.id.equals(user.id);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
        return Objects.hash(username, firstName, surname);
    }
}
