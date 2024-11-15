package global.kajisaab.common.db;

import global.kajisaab.common.dto.LabelValuePair;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class DbEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "deleted", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean deleted;

    @Column(name = "created_by")
    @TypeDef(type = DataType.JSON)
    private LabelValuePair createdBy;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "last_modified_by")
    @TypeDef(type = DataType.JSON)
    private LabelValuePair lastModifiedBy;

    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;

    @PrePersist
    public void createdTimeStamps() {
        createdOn = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return !deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LabelValuePair getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(LabelValuePair createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LabelValuePair getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(LabelValuePair lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }
}
