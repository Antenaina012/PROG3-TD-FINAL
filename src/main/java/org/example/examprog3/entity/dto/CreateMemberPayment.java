package org.example.examprog3.entity.dto;

import lombok.Data;
import org.example.examprog3.entity.enums.Bank;
import org.example.examprog3.entity.enums.PaymentMode;

import java.math.BigDecimal;

@Data
public class CreateMemberPayment {
    private String membershipFeeIdentifier; // ID du frais créé précédemment
    private String accountCreditedIdentifier; // ID du compte de la collectivité
    private BigDecimal amount;
    private PaymentMode paymentMode;
    // On ajoute les infos de virement/mobile si nécessaire
    private String mobileNumber;
    private Bank bankName;
}