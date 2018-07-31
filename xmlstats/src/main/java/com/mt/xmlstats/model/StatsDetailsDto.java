package com.mt.xmlstats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class StatsDetailsDto implements Serializable {

    private static final long serialVersionUID = 5843588584354343242L;

    private LocalDateTime firstPost;
    private LocalDateTime lastPost;
    private Long totalPosts = 0L;
    @JsonIgnore
    private Long scoreSum = 0L;
    private Double avgScore = 0.0;


}
