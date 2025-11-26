package com.mercadolibre.mutants.controller;

import com.mercadolibre.mutants.dto.DnaRequest;
import com.mercadolibre.mutants.dto.StatsResponse;
import com.mercadolibre.mutants.service.MutantService;
import com.mercadolibre.mutants.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    public MutantController(MutantService mutantService, StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    @Operation(summary = "Detecta si un ADN corresponde a un mutante")
    @ApiResponse(responseCode = "200", description = "Es mutante")
    @ApiResponse(responseCode = "403", description = "No es mutante")
    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        boolean mutant = mutantService.isMutant(request.getDna());
        return mutant
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Devuelve estadísticas de mutantes vs humanos")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> stats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
