package com.github.windmill312.gateway.redis.model;

public enum Topic {

    DELETE_PRINCIPAL_10("queue#deletePrincipal10"),
    DELETE_PRINCIPAL_20("queue#deletePrincipal20"),
    DELETE_PRINCIPAL_30("queue#deletePrincipal30");

    private String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
