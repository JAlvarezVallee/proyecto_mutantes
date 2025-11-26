package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.exception.InvalidDnaException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(List<String> dnaList) {
        if (dnaList == null || dnaList.isEmpty()) {
            throw new InvalidDnaException("dna no puede ser nulo ni vacío");
        }

        int n = dnaList.size();
        if (n < SEQUENCE_LENGTH) {
            // no se puede formar secuencia de 4
            return false;
        }

        char[][] matrix = buildMatrix(dnaList, n);

        int sequencesFound = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                char base = matrix[row][col];

                if (base == 0) continue;

                // Horizontal →
                if (col <= n - SEQUENCE_LENGTH && hasSequence(matrix, row, col, 0, 1, base)) {
                    sequencesFound++;
                }

                // Vertical ↓
                if (row <= n - SEQUENCE_LENGTH && hasSequence(matrix, row, col, 1, 0, base)) {
                    sequencesFound++;
                }

                // Diagonal principal ↘
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH &&
                        hasSequence(matrix, row, col, 1, 1, base)) {
                    sequencesFound++;
                }

                // Diagonal secundaria ↙
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1 &&
                        hasSequence(matrix, row, col, 1, -1, base)) {
                    sequencesFound++;
                }

                if (sequencesFound > 1) {
                    // early termination
                    return true;
                }
            }
        }

        return false;
    }

    private char[][] buildMatrix(List<String> dnaList, int n) {
        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            String row = dnaList.get(i);

            if (row == null || row.length() != n) {
                throw new InvalidDnaException("La matriz debe ser NxN");
            }

            for (int j = 0; j < n; j++) {
                char c = row.charAt(j);
                if (!isValidBase(c)) {
                    throw new InvalidDnaException(
                            "Caracter inválido en ADN: " + c + " (solo A,T,C,G)");
                }
                matrix[i][j] = c;
            }
        }
        return matrix;
    }

    private boolean isValidBase(char c) {
        return c == 'A' || c == 'T' || c == 'C' || c == 'G';
    }

    private boolean hasSequence(char[][] m, int row, int col,
                                int dRow, int dCol, char base) {

        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            int r = row + dRow * i;
            int c = col + dCol * i;
            if (m[r][c] != base) {
                return false;
            }
        }
        return true;
    }
}
