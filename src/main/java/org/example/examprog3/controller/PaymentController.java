package org.example.examprog3.controller;

import lombok.AllArgsConstructor;
import org.example.examprog3.Service.PaymentService;
import org.example.examprog3.entity.Transaction;
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
            @RequestBody List<Transaction> payments) { // Changé en List
        try {
            paymentService.processPayments(collectivityId, payments);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payments recorded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: : " + e.getMessage());
        }
    }


    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable("id") Integer collectivityId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {
        try {
            List<Transaction> transactions = paymentService.getTransactionsByPeriod(collectivityId, from, to);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or missing date parameters");
        }
    }
}