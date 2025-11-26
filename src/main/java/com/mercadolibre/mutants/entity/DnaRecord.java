package com.mercadolibre.mutants.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dna_record", uniqueConstraints = {
        @UniqueConstraint(columnNames = "dna_hash")
})
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna_hash", nullable = false, length = 64)
    private String dnaHash;

    @Column(name = "is_mutant", nullable = false)
    private boolean mutant;

    protected DnaRecord() { }

    public DnaRecord(String dnaHash, boolean mutant) {
        this.dnaHash = dnaHash;
        this.mutant = mutant;
    }

    public Long getId() {
        return id;
    }

    public String getDnaHash() {
        return dnaHash;
    }

    public boolean isMutant() {
        return mutant;
    }
}
