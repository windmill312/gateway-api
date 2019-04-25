package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotEmpty;

public class LoginCustomerRequest {

    @NotEmpty
    private String identifier;

    @NotEmpty
    private String password;

    @JsonCreator
    public LoginCustomerRequest(
            @JsonProperty("identifier") String identifier,
            @JsonProperty("password") String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identifier", identifier)
                .append("password", "[PROTECTED]")
                .toString();
    }
}
