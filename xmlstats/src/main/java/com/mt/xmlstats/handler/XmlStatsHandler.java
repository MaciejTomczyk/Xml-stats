package com.mt.xmlstats.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class XmlStatsHandler extends DefaultHandler {

    private static final String CREATION_DATE = "CreationDate";
    private static final String SCORE = "Score";
    private static final String ROW = "row";
    private LocalDateTime firstPost;
    private LocalDateTime lastPost;
    private Long scoreSum = 0L;
    private Long totalPosts = 0L;
    private Double avgScore = 0.0;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (ROW.equalsIgnoreCase(qName)) {
            LocalDateTime dateTime = LocalDateTime.parse(attributes.getValue(CREATION_DATE));
            Long score = Long.valueOf(attributes.getValue(SCORE));
            updateTotalPosts();
            updateAvgScore(score);
            updateDates(dateTime);
        }
    }

    private void updateTotalPosts() {
        this.totalPosts += 1;
    }

    private void updateAvgScore(@NotNull Long score) {
        this.scoreSum += score;
        this.avgScore = scoreSum.doubleValue() / totalPosts.doubleValue();

    }

    private void updateDates(@NotNull LocalDateTime dateTime) {
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
