package com.mt.xmlstats.mapper;

import com.mt.xmlstats.handler.XmlStatsHandler;
import com.mt.xmlstats.model.StatsDetailsDto;
import com.mt.xmlstats.model.StatsDto;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class StatsMapper {

    public StatsDto mapToDto(XmlStatsHandler handler) {
        return StatsDto.builder()
                .date(now())
                .details(StatsDetailsDto.builder()
                        .avgScore(handler.getAvgScore())
                        .firstPost(handler.getFirstPost())
                        .lastPost(handler.getLastPost())
                        .totalPosts(handler.getTotalPosts())
                        .build())
                .build();

    }
}
