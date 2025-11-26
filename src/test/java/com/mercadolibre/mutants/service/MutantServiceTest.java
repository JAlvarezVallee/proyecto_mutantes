package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.entity.DnaRecord;
import com.mercadolibre.mutants.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void returnsCachedResultWhenDnaAlreadyExists() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        );

        // Simulamos hash ya existente en BD → mutante true
        DnaRecord record = new DnaRecord("hash123", true);

        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.of(record));

        boolean result = mutantService.isMutant(dna);

        assertTrue(result);
        // No debería llamar al detector porque ya está cacheado
        verify(mutantDetector, never()).isMutant(anyList());
        // Tampoco debería hacer save
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    void callsDetectorAndSavesWhenDnaIsNewMutant() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        );

        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.isMutant(dna);

        assertTrue(result);

        // Verifica que se llamó al detector
        verify(mutantDetector, times(1)).isMutant(dna);

        // Verifica que se guardó un nuevo registro
        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(dnaRecordRepository, times(1)).save(captor.capture());

        DnaRecord saved = captor.getValue();
        assertTrue(saved.isMutant());
        assertNotNull(saved.getDnaHash());
        assertFalse(saved.getDnaHash().isEmpty());
    }

    @Test
    void callsDetectorAndSavesWhenDnaIsNewHuman() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        );

        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        boolean result = mutantService.isMutant(dna);

        assertFalse(result);
        verify(mutantDetector, times(1)).isMutant(dna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }
}

