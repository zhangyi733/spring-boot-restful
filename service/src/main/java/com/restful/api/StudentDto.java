package com.restful.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class StudentDto {

    @JsonProperty(value = "student-id")
    private UUID uuid;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private float gpa;
    private Instant timestamp;

    public UUID getUuid() {
        return uuid;
    }

    public StudentDto setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public StudentDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public StudentDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public float getGpa() {
        return gpa;
    }

    public StudentDto setGpa(float gpa) {
        this.gpa = gpa;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public StudentDto setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
