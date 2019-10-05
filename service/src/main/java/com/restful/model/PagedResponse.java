package com.restful.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.util.Assert;

public class PagedResponse<T> {
    private List<T> content;
    private UUID nextId;

    public PagedResponse() {
        this(new ArrayList(), (UUID)null);
    }

    public PagedResponse(List<T> content, UUID nextId) {
        Assert.notNull(content, "Content must not be null!");
        this.content = content;
        this.nextId = nextId;
    }

    public List<T> getContent() {
        return this.content;
    }

    public PagedResponse<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public UUID getNextId() {
        return this.nextId;
    }

    public PagedResponse<T> setNextId(UUID nextId) {
        this.nextId = nextId;
        return this;
    }
}
