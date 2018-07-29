package com.mt.xmlstats.mapper;

import com.mt.xmlstats.model.Stats;
import com.mt.xmlstats.model.StatsDto;
import org.springframework.stereotype.Service;

@Service
public class StatsMapper {

    public StatsDto mapToDto(Stats stats) {
        return StatsDto.builder()
                .date(stats.getDate())
                .details(stats)
                .build();

    }
}
