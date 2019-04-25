package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.Instant;
import java.util.UUID;

public class CustomerFullInfo {

    private UUID extId;
    private String name;
    private Instant birthDate;

    public CustomerFullInfo(
            UUID extId,
            String name,
            Instant birthDate) {
        this.extId = extId;
        this.name = name;
        this.birthDate = birthDate;
    }

    @JsonGetter("extId")
    public UUID getExtId() {
        return extId;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("birthDate")
    public Instant getBirthDate() {
        return birthDate;
    }


}
