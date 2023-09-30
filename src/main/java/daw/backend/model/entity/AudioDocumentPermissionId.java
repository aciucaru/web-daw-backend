package daw.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AudioDocumentPermissionId implements Serializable
{
    @Column(name = "document_id_fk")
    private String documentId;

    @Column(name = "user_id_fk")
    private String userId;

    public AudioDocumentPermissionId() { }

    public AudioDocumentPermissionId(String documentId, String userId)
    {
        if(documentId != null)
            this.documentId = documentId;

        if(userId != null)
            this.userId = userId;
    }

    public String getDocumentId() { return documentId; }

    public void setDocumentId(String documentId)
    {
        if(documentId != null)
            this.documentId = documentId;
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

        AudioDocumentPermissionId idObj = (AudioDocumentPermissionId) obj;
        return Objects.equals(documentId, idObj.documentId) &&
                Objects.equals(userId, idObj.userId);
    }

    @Override
    public int hashCode()
    {
        // do not use 'id' for hashCode, use all other properties
        return Objects.hash(documentId, userId);
    }
}
