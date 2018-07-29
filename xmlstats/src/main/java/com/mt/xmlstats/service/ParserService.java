package com.mt.xmlstats.service;

import com.mt.xmlstats.mapper.StatsMapper;
import com.mt.xmlstats.model.StatsDto;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.of;

@Service
@CommonsLog
public class ParserService {

    @Autowired
    private StatsMapper statsMapper;

    public Optional<StatsDto> parseXml(@NotNull String url) {

        var factory = SAXParserFactory.newInstance();
        try {
            var saxParser = factory.newSAXParser();
            var handler = new XmlHandler();
            saxParser.parse(url, handler);
            return of(handler.getStats()).map(i -> statsMapper.mapToDto(i));


        } catch (SAXException | ParserConfigurationException | IOException e) {
            log.warn("Error while parsing xml at: " + url);
            //or throw adequate business exception
        }
        return Optional.empty();
    }
}
