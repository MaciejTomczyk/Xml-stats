package com.mt.xmlstats.mapper;

import com.mt.xmlstats.handler.XmlStatsHandler;
import com.mt.xmlstats.model.StatsDto;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StatsDetailsDto mapper")
class StatsMapperTest {

    private static final long DELTA = 123123213L;
    private final StatsMapper statsMapper = new StatsMapper();

    @Test
    @DisplayName("should map stats to statsDto")
    void mapToDto() throws IllegalAccessException {
        //given
        var avgScore = 2.0;
        var totalPosts = 666L;
        var scoreSum = 1332L;
        var date = LocalDateTime.of(2000, 1, 1, 1, 1);
        var xmlHandler = new XmlStatsHandler();
        FieldUtils.writeDeclaredField(xmlHandler, "firstPost", date, true);
        FieldUtils.writeDeclaredField(xmlHandler, "lastPost", date, true);
        FieldUtils.writeDeclaredField(xmlHandler, "avgScore", avgScore, true);
        FieldUtils.writeDeclaredField(xmlHandler, "totalPosts", totalPosts, true);
        FieldUtils.writeDeclaredField(xmlHandler, "scoreSum", scoreSum, true);

        //when
        StatsDto result = statsMapper.mapToDto(xmlHandler);

        //then
        assertEquals(avgScore, result.getDetails().getAvgScore(), DELTA);
        assertEquals(totalPosts, result.getDetails().getTotalPosts(), DELTA);
        assertNull(result.getDetails().getScoreSum());
        assertNotNull(result.getDate());
        assertEquals(date, result.getDetails().getFirstPost());
        assertEquals(date, result.getDetails().getLastPost());
    }
}