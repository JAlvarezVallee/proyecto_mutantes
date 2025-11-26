package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.entity.DnaRecord;
import com.mercadolibre.mutants.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

@Service
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public MutantService(MutantDetector mutantDetector,
                         DnaRecordRepository dnaRecordRepository) {
        this.mutantDetector = mutantDetector;
        this.dnaRecordRepository = dnaRecordRepository;
    }

    @Transactional
    public boolean isMutant(List<String> dna) {
        String hash = calculateDnaHash(dna);

        return dnaRecordRepository.findByDnaHash(hash)
                .map(DnaRecord::isMutant)           // cache
                .orElseGet(() -> {
                    boolean mutant = mutantDetector.isMutant(dna);
                    dnaRecordRepository.save(new DnaRecord(hash, mutant));
                    return mutant;
                });
    }

    private String calculateDnaHash(List<String> dna) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String joined = String.join("-", dna);
            byte[] digest = md.digest(joined.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            // no debería pasar
            throw new IllegalStateException("No se pudo inicializar SHA-256", e);
        }
    }
}
