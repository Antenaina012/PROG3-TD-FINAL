package org.example.examprog3.controller;

import org.example.examprog3.entity.Federation;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.service.FederationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/federation")
@AllArgsConstructor
public class FederationController {
    private final FederationService federationService;
    @GetMapping
    public ResponseEntity<?> getFederation() {
        try {
            Federation federation = federationService.getFederation();
            return ResponseEntity.ok(federation);
        } catch (NotFoundException e) {
            // Fédération non trouvée → 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Erreur serveur → 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }
}