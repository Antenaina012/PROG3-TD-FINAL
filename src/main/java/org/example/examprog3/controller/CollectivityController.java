package org.example.examprog3.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.FinancialAccount;
import org.example.examprog3.entity.MembershipFee;
import org.example.examprog3.entity.Payment;
import org.example.examprog3.entity.dto.CollectivityResponse;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.entity.dto.CreateMembershipFee;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.service.CollectivityService;
import org.example.examprog3.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/collectivities")
public class   CollectivityController {
    private final CollectivityService service;
    private final PaymentService paymentService;

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable("id") String collectivityId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {
        try {
            List<Payment> transactions = paymentService.getTransactionsByPeriod(collectivityId, from, to);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            // Paramètres de date invalides → 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            // Collectivité non trouvée → 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collectivity> getCollectivityById(@PathVariable String id) {
        Collectivity collectivity = service.getById(id);
        return ResponseEntity.ok(collectivity);
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<List<FinancialAccount>> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam(name = "at") String atDate) {
        List<FinancialAccount> accounts = service.getFinancialAccountsWithBalance( id, atDate);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<?> createCollectivities(@RequestBody(required = false) List<CreateCollectivity> createCollectivities){
        try {
            if(createCollectivities == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory body not provided");
            }
            List<CollectivityResponse> collectivities = service.createCollectivities(createCollectivities);
            return ResponseEntity.status(HttpStatus.CREATED).body(collectivities);
        } catch (BadRequestException e) {
            // Erreurs de validation métier → 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            // Membres non trouvés → 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Erreurs inattendues → 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/informations")
    public ResponseEntity<?> updateIdentity(
            @PathVariable String id,
            @RequestBody IdentityRequest request) {
        try {
            Collectivity updated = service.assignIdentity( id, request.getNumber(), request.getName());
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> getMembershipFees(@PathVariable String id) {
        return ResponseEntity.ok(service.getMembershipFees(id));
    }

    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<?> createMembershipFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> fees) {
        try {
            List<MembershipFee> createdFees = service.createMembershipFees(id, fees);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFees);
        } catch (BadRequestException e) {
            // Validation échouée → 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            // Collectivité non trouvée → 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentityRequest {
        private String number;
        private String name;
    }
}