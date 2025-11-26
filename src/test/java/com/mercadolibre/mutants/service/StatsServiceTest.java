package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.dto.StatsResponse;
import com.mercadolibre.mutants.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void calculatesStatsWithMutantsAndHumans() {
        when(dnaRecordRepository.countByMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByMutant(false)).thenReturn(100L);

        StatsResponse stats = statsService.getStats();

        assertEquals(40L, stats.getCount_mutant_dna());
        assertEquals(100L, stats.getCount_human_dna());
        assertEquals(0.4, stats.getRatio(), 0.0001);
    }

    @Test
    void ratioIsZeroWhenNoHumans() {
        when(dnaRecordRepository.countByMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats();

        assertEquals(10L, stats.getCount_mutant_dna());
        assertEquals(0L, stats.getCount_human_dna());
        assertEquals(0.0, stats.getRatio(), 0.0001);
    }
}
