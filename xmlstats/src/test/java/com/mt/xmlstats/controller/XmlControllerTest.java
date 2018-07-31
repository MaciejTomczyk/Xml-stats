package com.mt.xmlstats.controller;

import com.mt.xmlstats.model.StatsDto;
import com.mt.xmlstats.model.UrlWrapper;
import com.mt.xmlstats.service.XmlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Xml controller")
class XmlControllerTest {

    @Mock
    private XmlService xmlService;

    @InjectMocks
    private XmlController xmlController;

    @Test
    @DisplayName("should return xmlstats stats")
    void analyzeXmlReturnStats() {
        //given
        var url = "url";
        var statsDto = new StatsDto();
        var urlWrapper = new UrlWrapper();
        urlWrapper.setUrl(url);

        when(xmlService.getXmlStats(url)).thenReturn(Optional.of(statsDto));

        //when
        var result = xmlController.analyzeXml(urlWrapper);

        //then
        assertNotNull(result.getBody());
        verify(xmlService, times(1)).getXmlStats(url);
    }

    @Test
    @DisplayName("should return bad request")
    void analyzeXmlReturnBadRequest() {
        //given
        var url = "url";
        var urlWrapper = new UrlWrapper();
        urlWrapper.setUrl(url);

        when(xmlService.getXmlStats(any())).thenReturn(Optional.empty());

        //when
        var result = xmlController.analyzeXml(urlWrapper);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(xmlService, times(1)).getXmlStats(url);

    }

}