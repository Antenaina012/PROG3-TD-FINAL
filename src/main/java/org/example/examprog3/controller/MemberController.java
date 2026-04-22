package org.example.examprog3.controller;

import lombok.AllArgsConstructor;
import org.example.examprog3.Service.MemberService;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.exception.InsufficientSponsorCount;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService service;

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
}