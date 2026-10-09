package hu.parlament.common;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, comment = "id")
    private Integer id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, comment = "Létrehozás ideje")
    private Instant createdAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false, comment = "Létrehozó")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false, comment = "Utolsó módosítás ideje")
    private Instant modifiedAt;

    @LastModifiedBy
    @Column(name = "modified_by", comment = "Utolsó módosító")
    private String modifiedBy;
}