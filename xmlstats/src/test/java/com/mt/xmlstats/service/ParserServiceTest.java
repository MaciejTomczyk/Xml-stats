package com.mt.xmlstats.service;

import com.mt.xmlstats.mapper.StatsMapper;
import com.mt.xmlstats.model.StatsDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SAXParserFactory.class)
public class ParserServiceTest {


    @Mock
    private StatsMapper statsMapper;

    @InjectMocks
    private ParserService parserService = new ParserService();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(SAXParserFactory.class);
    }

    @Test
    public void readFile() throws Exception {
        //given
        var url = "url";

        SAXParserFactory mockSaxFactory = mock(SAXParserFactory.class);
        SAXParser saxParser = mock(SAXParser.class);
        DefaultHandler defaultHandler = mock(DefaultHandler.class);

        when(SAXParserFactory.newInstance()).thenReturn(mockSaxFactory);
        when(mockSaxFactory.newSAXParser()).thenReturn(saxParser);
        doNothing().when(saxParser).parse(anyString(), any(DefaultHandler.class));
        when(statsMapper.mapToDto(any())).thenReturn(new StatsDto());

        //when
        var result = parserService.parseXml(saxParser, url, defaultHandler);

        //then
        assertNotNull(result);
        verify(saxParser, times(1)).parse(anyString(), any(DefaultHandler.class));
    }

    @Test
    public void readFileThrowException() throws Exception {
        //given
        var url = "url";

        SAXParserFactory mockSaxFactory = mock(SAXParserFactory.class);
        SAXParser saxParser = mock(SAXParser.class);
        DefaultHandler defaultHandler = mock(DefaultHandler.class);


        when(SAXParserFactory.newInstance()).thenReturn(mockSaxFactory);
        when(mockSaxFactory.newSAXParser()).thenReturn(saxParser);
        doThrow(SAXException.class).when(saxParser).parse(anyString(), any(DefaultHandler.class));

        //when
        var result = parserService.parseXml(saxParser, url, defaultHandler);

        //then
        assertEquals(Optional.empty(), result);
        verify(statsMapper, times(0)).mapToDto(any());
        verify(saxParser, times(1)).parse(anyString(), any(DefaultHandler.class));
    }

    @Test
    public void prepareParser() throws ParserConfigurationException, SAXException {
        //given

        SAXParserFactory mockSaxFactory = mock(SAXParserFactory.class);
        SAXParser saxParser = mock(SAXParser.class);

        when(SAXParserFactory.newInstance()).thenReturn(mockSaxFactory);
        when(mockSaxFactory.newSAXParser()).thenReturn(saxParser);

        //when
        Optional<SAXParser> result = parserService.prepareParser();

        //then
        assertNotNull(result);
    }

    @Test
    public void prepareParserThrowException() throws ParserConfigurationException, SAXException {
        //given

        SAXParserFactory mockSaxFactory = mock(SAXParserFactory.class);

        when(SAXParserFactory.newInstance()).thenReturn(mockSaxFactory);
        when(mockSaxFactory.newSAXParser()).thenThrow(SAXException.class);

        //when
        Optional<SAXParser> result = parserService.prepareParser();

        //then
        assertThat(result, is(Optional.empty()));
    }
}