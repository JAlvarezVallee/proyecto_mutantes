package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.exception.InvalidDnaException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void detectsMutantWithHorizontalAndDiagonalSequences() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        );

        assertTrue(detector.isMutant(dna));
    }

    @Test
    void detectsMutantWithVerticalSequence() {
        List<String> dna = List.of(
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        );
        // Columna 0: A A A A → una secuencia
        // Agregamos otra secuencia diagonal:
        dna = List.of(
                "ATGCGA",
                "CAGTGA",
                "TTATGA",
                "AGATGA",
                "CCCCTA",
                "TCACTG"
        );

        assertTrue(detector.isMutant(dna));
    }

    @Test
    void returnsFalseWhenOnlyOneSequenceExists() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        );
        // Solo una secuencia horizontal

        assertFalse(detector.isMutant(dna));
    }

    @Test
    void returnsFalseWhenNoSequencesExist() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        );

        assertFalse(detector.isMutant(dna));
    }

    @Test
    void returnsFalseWhenMatrixIsNotSquare() {
        List<String> dna = List.of(
                "ATGC",
                "CAGT",
                "TTAT"   // 3 filas de 4 → no es NxN
        );

        boolean result = detector.isMutant(dna);

        assertFalse(result);
    }


    @Test
    void throwsExceptionWhenContainsInvalidCharacter() {
        List<String> dna = List.of(
                "ATGCGA",
                "CAGTXC", // X inválida
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        );

        assertThrows(InvalidDnaException.class, () -> detector.isMutant(dna));
    }

    @Test
    void returnsFalseWhenMatrixIsSmallerThan4x4() {
        List<String> dna = List.of(
                "ATG",
                "CAG",
                "TTA"
        );
        // No se puede formar secuencia de 4

        assertFalse(detector.isMutant(dna));
    }

    @Test
    void throwsExceptionWhenDnaIsNullOrEmpty() {
        assertThrows(InvalidDnaException.class, () -> detector.isMutant(null));
        assertThrows(InvalidDnaException.class, () -> detector.isMutant(List.of()));
    }
}

