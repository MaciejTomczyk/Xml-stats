package com.mt.xmlstats.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Stats object")
class StatsTest {

    private static final long DELTA = 123123123L;
    private final LocalDateTime dateTime = LocalDateTime.of(2000, 1, 1, 1, 1);
    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = new Stats(dateTime);
    }


    @Test
    @DisplayName("should update total posts")
    void updateTotalPosts() {
        //given
        stats.setTotalPosts(5L);

        //when
        stats.updateTotalPosts();

        //then
        assertEquals(6L, stats.getTotalPosts(), DELTA);
    }

    @Test
    @DisplayName("should update average score")
    void updateAvgScore() {
        //given
        stats.setScoreSum(5L);
        stats.setTotalPosts(2L);

        //when

        stats.updateAvgScore(5L);

        //then
        assertEquals(10.0, stats.getAvgScore(), DELTA);
    }

    @Test
    @DisplayName("should update firstPost date")
    void updateDatesFirstPost() {
        //given
        LocalDateTime newDateTime = LocalDateTime.of(2015, 1, 1, 1, 1);
        stats.setFirstPost(LocalDateTime.MAX);
        //when
        stats.updateDates(newDateTime);

        //then
        assertEquals(newDateTime, stats.getFirstPost());
    }

    @Test
    @DisplayName("should update lastPost date")
    void updateDatesLastPost() {
        //given
        LocalDateTime newDateTime = LocalDateTime.of(2015, 1, 1, 1, 1);
        stats.setLastPost(LocalDateTime.MIN);
        //when
        stats.updateDates(newDateTime);

        //then
        assertEquals(newDateTime, stats.getLastPost());
    }
}