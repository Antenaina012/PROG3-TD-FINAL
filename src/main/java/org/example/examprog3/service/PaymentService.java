package org.example.examprog3.service;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Payment;
import org.example.examprog3.entity.dto.CreateMemberPayment;
import org.example.examprog3.entity.enums.PaymentType;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.repository.MemberRepository;
import org.example.examprog3.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

    public void processPayments(String collectivityId, List<Payment> payments) {
        if (collectivityRepository.findById(collectivityId) == null) {
            throw new NotFoundException("Collectivité introuvable ID: " + collectivityId);
        }

        for (Payment payment : payments) {
            // Plus de conversion Integer.valueOf, on passe l'ID tel quel
            validateAndPrepareTransaction(collectivityId, payment);
            transactionRepository.saveTransaction(payment);
        }
    }

    public List<Payment> getTransactionsByPeriod(String id, String from, String to) {
        // Le repository accepte maintenant un String pour l'ID
        return transactionRepository.findTransactionsByPeriod(id, from, to);
    }

    private void validateAndPrepareTransaction(String collectivityId, Payment payment) {
        // Validation du montant
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement supérieur à zéro");
        }

        // Validation du mode de paiement
        if (payment.getPaymentMode() == null) {
            throw new IllegalArgumentException("Le mode de paiement est obligatoire (CASH, BANK_TRANSFER, MOBILE_BANKING)");
        }

        // Forcer l'ID de la collectivité (String)
        payment.setCollectivityId(collectivityId);
        payment.setTransactionType(PaymentType.IN); // Toujours 'IN' pour un encaissement
    }

    public List<Payment> processMemberPayments(String memberId, List<CreateMemberPayment> dtos) {
        // 1. Vérifier si le membre existe
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException("Membre non trouvé");
        }

        List<Payment> savedPayments = new ArrayList<>();
        for (CreateMemberPayment dto : dtos) {
            Payment payment = Payment.builder()
                    .memberId(memberId)
                    .amount(dto.getAmount())
                    .paymentMode(dto.getPaymentMode())
                    .accountCreditedIdentifier(dto.getAccountCreditedIdentifier())
                    .membershipFeeIdentifier(dto.getMembershipFeeIdentifier())
                    .transactionDate(new Timestamp(System.currentTimeMillis()))
                    .build();

            // 2. Sauvegarder (ceci doit insérer dans la table "transaction")
            paymentRepository.saveTransaction(payment);
            savedPayments.add(payment);
        }
        return savedPayments;
    }
}