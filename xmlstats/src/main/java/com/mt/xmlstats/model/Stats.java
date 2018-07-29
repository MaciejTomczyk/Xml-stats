package com.mt.xmlstats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Data
@JsonInclude
public class Stats implements Serializable {

    private static final long serialVersionUID = 5843588584354343242L;

    @JsonIgnore
    private LocalDateTime date;

    private LocalDateTime firstPost = null;

    private LocalDateTime lastPost = null;

    private Long totalPosts = 0L;

    @JsonIgnore
    private Long scoreSum = 0L;

    @SuppressWarnings("FieldCanBeLocal")
    private Double avgScore = 0.0;

    public Stats(LocalDateTime requestDateTime) {
        this.date = requestDateTime;
    }

    public void updateTotalPosts() {
        totalPosts++;
    }

    public void updateAvgScore(Long score) {
        scoreSum += score;
        avgScore = scoreSum.doubleValue() / totalPosts.doubleValue();

    }

    public void updateDates(LocalDateTime dateTime) {
        Stream.of(firstPost, dateTime)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Function.identity()))
                .ifPresent(i -> firstPost = i);
        Stream.of(lastPost, dateTime)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Function.identity()))
                .ifPresent(i -> lastPost = i);
    }
}
