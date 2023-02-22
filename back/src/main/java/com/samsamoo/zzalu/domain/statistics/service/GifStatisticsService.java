package com.samsamoo.zzalu.domain.statistics.service;

import com.samsamoo.zzalu.domain.statistics.entity.GifStatistics;
import com.samsamoo.zzalu.domain.statistics.repository.GifStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GifStatisticsService {

    private final GifStatisticsRepository gifStatisticsRepository;

    public Optional<GifStatistics> findByGifId(Long id) {
        return gifStatisticsRepository.findByGifId(id);
    }

    public void save(GifStatistics gifStatistics) {
        gifStatisticsRepository.save(gifStatistics);
    }


}
