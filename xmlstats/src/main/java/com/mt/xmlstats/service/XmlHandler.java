package com.mt.xmlstats.service;

import com.mt.xmlstats.model.Stats;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
class XmlHandler extends DefaultHandler {

    private static final String CREATION_DATE = "CreationDate";
    private static final String SCORE = "Score";
    private static final String ROW = "row";
    private final Stats stats = new Stats(now());

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (ROW.equalsIgnoreCase(qName)) {
            LocalDateTime dateTime = LocalDateTime.parse(attributes.getValue(CREATION_DATE));
            Long score = Long.valueOf(attributes.getValue(SCORE));
            stats.updateTotalPosts();
            stats.updateDates(dateTime);
            stats.updateAvgScore(score);
        }
    }
}
