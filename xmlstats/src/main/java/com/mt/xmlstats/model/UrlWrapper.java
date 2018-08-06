package com.mt.xmlstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonInclude
public class UrlWrapper implements Serializable {

    private static final long serialVersionUID = 44234324326773452L;

    @JsonProperty(required = true)
    @NotNull
    private String url;

}
