package com.mt.xmlstats.controller;

import com.mt.xmlstats.model.StatsDto;
import com.mt.xmlstats.model.UrlWrapper;
import com.mt.xmlstats.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analyze")
public class XmlController {

    @Autowired
    private XmlService xmlService;

    @PostMapping
    public ResponseEntity<StatsDto> analyzeXml(@RequestBody UrlWrapper urlWrapper) {
        return xmlService.getXmlStats(urlWrapper.getUrl())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
