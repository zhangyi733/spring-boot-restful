package com.restful.model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID uuid;
    private String firstName;
    private String lastName;
    private float gpa;
    private Instant timestamp;
    @LastModifiedBy
    @Column(name = "modified_by")
    private String updatedBy;
    @LastModifiedDate
    @Column(name = "modified_on")
    private Instant updatedTime;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    @Column(name = "created_on", updatable = false)
    private Instant createdTime;
    @Column(columnDefinition = "integer auto_increment") // Only needed for integration testing
    @Generated(GenerationTime.ALWAYS)
    private Long seqId;

    public Student() {
    }

    public Student(UUID uuid, String firstName, String lastName, float gpa, Instant timestamp) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
        this.timestamp = timestamp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Student setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Student setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Student setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public float getGpa() {
        return gpa;
    }

    public Student setGpa(float gpa) {
        this.gpa = gpa;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Student setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
