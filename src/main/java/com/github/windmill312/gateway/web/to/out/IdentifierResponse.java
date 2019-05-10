package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class IdentifierResponse {

    private String identifier;

    public IdentifierResponse(String identifier) {
        this.identifier = identifier;
    }

    @JsonGetter("identifier")
    public String getIdentifier() {
        return identifier;
    }
}
