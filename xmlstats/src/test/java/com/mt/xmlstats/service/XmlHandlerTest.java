package com.mt.xmlstats.service;

import com.mt.xmlstats.model.Stats;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.Attributes;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Xml handler")
class XmlHandlerTest {

    private static final String CREATION_DATE = "CreationDate";
    private static final String SCORE = "Score";
    private static final String ROW = "row";

    @Mock
    private Stats stats = mock(Stats.class);

    @InjectMocks
    private XmlHandler xmlHandler = new XmlHandler();

    @BeforeEach
    void setUp() throws IllegalAccessException {
        FieldUtils.writeDeclaredField(xmlHandler, "stats", stats, true);
    }

    @Test
    @DisplayName("should parse record and update stats")
    void startElement() {
        //given
        var attributes = mock(Attributes.class);

        doReturn(LocalDateTime.MAX.toString()).when(attributes).getValue(CREATION_DATE);
        doReturn("2").when(attributes).getValue(SCORE);

        //when
        xmlHandler.startElement("", "", ROW, attributes);

        //then
        verify(attributes, times(1)).getValue(CREATION_DATE);
        verify(attributes, times(1)).getValue(SCORE);
        verify(stats, times(1)).updateTotalPosts();
        verify(stats, times(1)).updateAvgScore(any());
        verify(stats, times(1)).updateDates(any());
    }

    @Test
    @DisplayName("should not do anything with wrong qName")
    void startElementWrongQName() {
        //given
        var attributes = mock(Attributes.class);

        //when
        xmlHandler.startElement("", "", "wrongValue", attributes);

        //then
        verify(attributes, times(0)).getValue(CREATION_DATE);
        verify(attributes, times(0)).getValue(SCORE);
        verify(stats, times(0)).updateTotalPosts();
        verify(stats, times(0)).updateAvgScore(any());
        verify(stats, times(0)).updateDates(any());
    }

    @Test
    @DisplayName("should return stats object")
    void getStats() {
        //given


        //when
        Stats result = xmlHandler.getStats();

        //then
        assertNotNull(result);
    }
}