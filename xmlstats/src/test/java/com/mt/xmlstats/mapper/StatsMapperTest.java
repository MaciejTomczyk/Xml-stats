package com.mt.xmlstats.mapper;

import com.mt.xmlstats.model.Stats;
import com.mt.xmlstats.model.StatsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Stats mapper")
class StatsMapperTest {

    private static final long DELTA = 123123213L;
    private final StatsMapper statsMapper = new StatsMapper();

    @Test
    @DisplayName("should map stats to statsDto")
    void mapToDto() {
        //given
        var avgScore = 2.0;
        var totalPosts = 666L;
        var scoreSum = 1332L;
        var date = LocalDateTime.of(2000, 1, 1, 1, 1);
        var stats = new Stats(date);
        stats.setFirstPost(date);
        stats.setLastPost(date);
        stats.setAvgScore(avgScore);
        stats.setTotalPosts(totalPosts);
        stats.setScoreSum(scoreSum);

        //when
        StatsDto result = statsMapper.mapToDto(stats);

        //then
        assertEquals(avgScore, result.getDetails().getAvgScore(), DELTA);
        assertEquals(totalPosts, result.getDetails().getTotalPosts(), DELTA);
        assertEquals(scoreSum, result.getDetails().getScoreSum(), DELTA);
        assertEquals(date, result.getDate());
        assertEquals(date, result.getDetails().getFirstPost());
        assertEquals(date, result.getDetails().getLastPost());
    }
}