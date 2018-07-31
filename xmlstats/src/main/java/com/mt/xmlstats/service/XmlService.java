package com.mt.xmlstats.service;

import com.mt.xmlstats.handler.XmlStatsHandler;
import com.mt.xmlstats.mapper.StatsMapper;
import com.mt.xmlstats.model.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.helpers.DefaultHandler;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Function;

@Service
public class XmlService {

    @Autowired
    private StatsMapper statsMapper;

    @Autowired
    private ParserService parserService;

    public Optional<StatsDto> getXmlStats(@NotNull String url) {
        return processXml(url, new XmlStatsHandler(), mapToXmlHandler, mapToDto);
    }

    /**
     * Default method to process XML based on given handler. Method requires creating casting and mapping functions
     *
     * @param url           (points to the path of the xml file)
     * @param handler       (handler processing the file)
     * @param handlerCaster (function mapping Default Handler to desired type)
     * @param mapper        (function mapping data from handler to desired result type)
     * @param <R>           response type
     * @param <T>           handler target class
     * @return returns the result of the provided mapping function
     */
    private <R, T> Optional<R> processXml(@NotNull String url, DefaultHandler handler,
                                          Function<DefaultHandler, T> handlerCaster, Function<T, R> mapper) {
        return parserService.prepareParser()
                .map(parser -> parserService.parseXml(parser, url, handler))
                .filter(Optional::isPresent)
                .map(i -> handlerCaster.apply(i.get()))
                .map(mapper);
    }

    private final Function<DefaultHandler, XmlStatsHandler> mapToXmlHandler = i -> (XmlStatsHandler) i;
    private final Function<XmlStatsHandler, StatsDto> mapToDto = handler -> statsMapper.mapToDto(handler);

}
