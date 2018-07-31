package com.mt.xmlstats.service;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.of;

@Service
@CommonsLog
public class ParserService {


    <T extends DefaultHandler> Optional<T> parseXml(@NotNull SAXParser saxParser, @NotNull String url, @NotNull T handler) {
        try {
            saxParser.parse(url, handler);
            return of(handler);
        } catch (SAXException | IOException e) {
            log.warn("Error while parsing xml at: " + url);
            //possibly throw adequate exception
        }
        return Optional.empty();
    }

    Optional<SAXParser> prepareParser() {
        try {
            var factory = SAXParserFactory.newInstance();
            return of(factory.newSAXParser());
        } catch (ParserConfigurationException | SAXException e) {
            log.warn("Could not create SAXParser");
        }
        return Optional.empty();
    }
}
