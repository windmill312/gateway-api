package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class AddCustomerRequest {

    @NotBlank
    @ApiModelProperty(example = "Customer Name")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "identifier")
    private String identifier;

    @NotBlank
    @ApiModelProperty(example = "qwerTy123")
    private String password;

    @NotBlank
    @ApiModelProperty(example = "123456789")
    private String subsystemCode;

    @NotNull
    private Instant birthDate;

    @JsonCreator
    public AddCustomerRequest(
            @JsonProperty("name") String name,
            @JsonProperty("birthDate") Instant birthDate,
            @JsonProperty("identifier") String identifier,
            @JsonProperty("password") String password,
            @JsonProperty("subsystemCode") String subsystemCode) {
        this.name = name;
        this.birthDate = birthDate;
        this.identifier = identifier;
        this.password = password;
        this.subsystemCode = subsystemCode;
    }

    public String getName() {
        return name;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public String getSubsystemCode() {
        return subsystemCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
