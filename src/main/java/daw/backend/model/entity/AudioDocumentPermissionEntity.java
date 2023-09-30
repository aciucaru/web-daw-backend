package daw.backend.model.entity;

import jakarta.persistence.*;
import daw.backend.model.DocumentPermissionType;

import java.util.Objects;

@Entity(name = "DocumentUserPermissionEntity")
@Table(name="document_user_permission")
public class AudioDocumentPermissionEntity
{
    @EmbeddedId
    private AudioDocumentPermissionId id;

    @Column(name = "document_key")
    private String documentKey; // hashed with user public key

    @Column(name = "permissionType")
    private DocumentPermissionType documentPermissionType;

    @Column(name = "can_authorize_others")
    private Boolean canAuthorizeOthers;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("documentId")
//    @JoinColumn(name = "document_id_fk")
    private AudioDocumentEntity document;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
//    @JoinColumn(name = "user_id_fk")
    private UserEntity user;

    public AudioDocumentPermissionEntity(String documentKey,
                                         DocumentPermissionType documentPermissionType,
                                         Boolean canAuthorizeOthers,
                                         AudioDocumentEntity document,
                                         UserEntity user)
    {
        this.documentKey = documentKey;
        this.documentPermissionType = documentPermissionType;
        this.canAuthorizeOthers = canAuthorizeOthers;
        this.document = document;
        this.user = user;
    }

    public AudioDocumentPermissionEntity(AudioDocumentEntity document, UserEntity user)
    {
        this.document = document;
        this.user = user;
    }

    public AudioDocumentPermissionEntity() { }

    public AudioDocumentPermissionId getId() { return id; }

    public void setId(AudioDocumentPermissionId id)
    {
        if(id != null)
            this.id = id;
    }

    public String getDocumentKey() { return documentKey; }

    public void setDocumentKey(String documentKey)
    {
        if(documentKey != null)
            this.documentKey = documentKey;
    }

    public DocumentPermissionType getPermissionType() { return documentPermissionType; }

    public void setPermissionType(DocumentPermissionType documentPermissionType)
    {
        if(documentPermissionType != null)
            this.documentPermissionType = documentPermissionType;
    }

    public Boolean getCanAuthorizeOthers() { return canAuthorizeOthers; }

    public void setCanAuthorizeOthers(Boolean canAuthorizeOthers)
    {
        if(canAuthorizeOthers != null)
            this.canAuthorizeOthers = canAuthorizeOthers;
    }

    public AudioDocumentEntity getDocument() { return document; }

    public void setDocument(AudioDocumentEntity document)
    {
        if(document != null)
            this.document = document;
    }

    public UserEntity getUser() { return user; }

    public void setUser(UserEntity user)
    {
        if(user != null)
            this.user = user;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        AudioDocumentPermissionEntity documentPermission = (AudioDocumentPermissionEntity) obj;
        return Objects.equals(document, documentPermission.document) &&
                Objects.equals(user, documentPermission.user);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
//        return Objects.hash(documentKey,
//                            permissionType,
//                            canAuthorizeOthers,
//                            document,
//                            user);

        return Objects.hash(document, user);
    }
}
