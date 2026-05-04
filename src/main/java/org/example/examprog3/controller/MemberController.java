package org.example.examprog3.controller;

import java.util.List;

import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.entity.dto.CreateMemberPayment;
import org.example.examprog3.entity.dto.MemberPaymentResponse;
import org.example.examprog3.exception.InsufficientSponsorCount;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.exception.PaymentException;
import org.example.examprog3.service.MemberService;
import org.example.examprog3.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService service;
    private final PaymentService paymentService;


    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody List<CreateMember> members) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createMembers(members));

        } catch (PaymentException | InsufficientSponsorCount ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

        } catch (NotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + ex.getMessage());
        }
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<?> addPayments(
            @PathVariable("id") String memberId,
            @RequestBody List<CreateMemberPayment> payments) {
        try {
            List<MemberPaymentResponse> savedPayments = paymentService.processMemberPayments(memberId, payments);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPayments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur : " + e.getMessage());
        }
    }
}