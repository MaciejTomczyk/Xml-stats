package com.mt.xmlstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto implements Serializable {

    private static final long serialVersionUID = 8766454343243244343L;

    private LocalDateTime date;
    private Stats details;
}
