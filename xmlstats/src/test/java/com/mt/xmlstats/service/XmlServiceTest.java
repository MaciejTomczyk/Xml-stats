package com.mt.xmlstats.service;

import com.mt.xmlstats.handler.XmlStatsHandler;
import com.mt.xmlstats.mapper.StatsMapper;
import com.mt.xmlstats.model.StatsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.parsers.SAXParser;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("Xml service")
class XmlServiceTest {

    @Mock
    private StatsMapper statsMapper;

    @Mock
    private ParserService parserService;

    @InjectMocks
    private XmlService xmlService;


    @Test
    @DisplayName("should return xmlstats stats")
    void getXmlStats() {
        //given
        String url = "url";
        SAXParser saxParser = mock(SAXParser.class);
        XmlStatsHandler xmlStatsHandler = new XmlStatsHandler();
        StatsDto statsDto = mock(StatsDto.class);

        when(parserService.prepareParser()).thenReturn(Optional.of(saxParser));
        when(parserService.parseXml(any(), any(), any())).thenReturn(Optional.of(xmlStatsHandler));
        when(statsMapper.mapToDto(any())).thenReturn(statsDto);

        //when
        Optional<StatsDto> result = xmlService.getXmlStats(url);

        //then
        assertThat(result.orElse(null), is(statsDto));
        verify(parserService, times(1)).prepareParser();
        verify(parserService, times(1)).parseXml(any(), any(), any());
        verify(statsMapper, times(1)).mapToDto(any());
    }

    @Test
    @DisplayName("should fail and not crate parser")
    void getXmlStatsParserError() {
        //given
        String url = "url";

        when(parserService.prepareParser()).thenReturn(Optional.empty());

        //when
        Optional<StatsDto> result = xmlService.getXmlStats(url);

        //then
        assertThat(result, is(Optional.empty()));
        verify(parserService, times(1)).prepareParser();
        verify(parserService, times(0)).parseXml(any(), any(), any());
        verify(statsMapper, times(0)).mapToDto(any());

    }
}