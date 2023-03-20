package br.com.demo.infrastructure.database;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at", columnDefinition = "timestamp with time zone not null")
  private Date updatedAt;

  @PrePersist
  private void onCreate() {
    createdAt = new Date();
  }

  @PreUpdate
  private void onUpdate() {
    updatedAt = new Date();
  }

}
