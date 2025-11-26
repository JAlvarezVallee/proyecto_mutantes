package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.dto.StatsResponse;
import com.mercadolibre.mutants.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsService(DnaRecordRepository dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public StatsResponse getStats() {
        long mutants = dnaRecordRepository.countByMutant(true);
        long humans  = dnaRecordRepository.countByMutant(false);
        return new StatsResponse(mutants, humans);
    }
}
