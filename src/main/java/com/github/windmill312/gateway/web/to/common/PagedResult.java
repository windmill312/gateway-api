package com.github.windmill312.gateway.web.to.common;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public class PagedResult<T> {
    private final long totalCount;
    private final List<T> items;

    public PagedResult(long totalCount, List<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }

    @JsonGetter("totalCount")
    public long getTotalCount() {
        return totalCount;
    }

    @JsonGetter("items")
    public List<T> getItems() {
        return items;
    }
}
