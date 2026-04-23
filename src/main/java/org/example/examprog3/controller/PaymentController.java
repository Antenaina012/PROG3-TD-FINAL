package org.example.examprog3.controller;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Payment;
import org.example.examprog3.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/collectivities")
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/{id}/payments")
    public ResponseEntity<?> addPayments(
            @PathVariable("id") Integer collectivityId,
            @RequestBody List<Payment> payments) { // Changé en List
        try {
            // On délègue le traitement de la liste au service
            paymentService.processPayments(collectivityId, payments);
            return ResponseEntity.status(HttpStatus.CREATED).body("Paiements enregistrés avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur : " + e.getMessage());
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable("id") Integer collectivityId,
            @RequestParam("from") String from, // Paramètre obligatoire
            @RequestParam("to") String to) {   // Paramètre obligatoire
        try {
            List<Payment> transactions = paymentService.getTransactionsByPeriod(collectivityId, from, to);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paramètres de date invalides ou manquants");
        }
    }
}