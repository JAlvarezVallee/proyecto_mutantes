package com.mercadolibre.mutants.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class DnaRequest {

    @NotNull(message = "dna no puede ser nulo")
    @NotEmpty(message = "dna no puede estar vacío")
    private List<String> dna;

    public DnaRequest() {
    }

    public DnaRequest(List<String> dna) {
        this.dna = dna;
    }

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }
}
