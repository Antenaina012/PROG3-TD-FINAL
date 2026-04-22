package org.example.examprog3.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.examprog3.Service.CollectivityService;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.dto.CollectivityResponse;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.exeption.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/collectivities")
public class CollectivityController {
    private final CollectivityService service;

    @PostMapping
    public ResponseEntity<?> createCollectivities(@RequestBody(required = false) List<CreateCollectivity> createCollectivities){
        try{
            if(createCollectivities == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory body not provided");
            }
            List<CollectivityResponse> collectivities = service.createCollectivities(createCollectivities);
            return ResponseEntity.status(HttpStatus.CREATED).body(collectivities);
        }catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/identity")
    public ResponseEntity<?> updateIdentity(
            @PathVariable Integer id,
            @RequestBody IdentityRequest request) {
        try {
            Collectivity updated = service.assignIdentity(id, request.getNumber(), request.getName());
            return ResponseEntity.ok(updated);

        } catch (IllegalStateException e) {
            // 403 Forbidden : Identité déjà fixée (Immuabilité)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            // 400 Bad Request : Nom déjà utilisé ou données manquantes
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (NotFoundException e) {
            // 404 Not Found : Collectivité inexistante
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'attribution : " + e.getMessage());
        }
}