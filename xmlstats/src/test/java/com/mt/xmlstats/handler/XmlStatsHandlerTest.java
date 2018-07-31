package com.mt.xmlstats.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.Attributes;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Xml handler")
class XmlStatsHandlerTest {

    private static final String CREATION_DATE = "CreationDate";
    private static final String SCORE = "Score";
    private static final String ROW = "row";


    @InjectMocks
    private XmlStatsHandler xmlStatsHandler = new XmlStatsHandler();

    @Test
    @DisplayName("should parse record and update stats")
    void startElement() {
        //given
        var attributes = mock(Attributes.class);

        doReturn(LocalDateTime.MAX.toString()).when(attributes).getValue(CREATION_DATE);
        doReturn("2").when(attributes).getValue(SCORE);

        //when
        xmlStatsHandler.startElement("", "", ROW, attributes);

        //then
        assertEquals(2, xmlStatsHandler.getScoreSum(), 123123123L);
        verify(attributes, times(1)).getValue(CREATION_DATE);
        verify(attributes, times(1)).getValue(SCORE);
    }

    @Test
    @DisplayName("should not do anything with wrong qName")
    void startElementWrongQName() {
        //given
        var attributes = mock(Attributes.class);

        //when
        xmlStatsHandler.startElement("", "", "wrongValue", attributes);

        //then
        verify(attributes, times(0)).getValue(CREATION_DATE);
        verify(attributes, times(0)).getValue(SCORE);
    }
}