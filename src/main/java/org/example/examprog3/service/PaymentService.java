package org.example.examprog3.service;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Payment;
import org.example.examprog3.entity.enums.PaymentType;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;

    public void processPayments(Integer collectivityId, List<Payment> payments) {
        // 1. Vérification globale de la collectivité
        if (collectivityRepository.findById(collectivityId) == null) {
            throw new NotFoundException("Collectivité introuvable ID: " + collectivityId);
        }

        for (Payment payment : payments) {
            validateAndPrepareTransaction(collectivityId, payment);
            transactionRepository.saveTransaction(payment);
        }
    }

    public List<Payment> getTransactionsByPeriod(Integer id, String from, String to) {
        return transactionRepository.findTransactionsByPeriod(id, from, to);
    }

    private void validateAndPrepareTransaction(Integer collectivityId, Payment payment) {
        // Validation du montant
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement supérieur à zéro");
        }

        // Validation du mode de paiement
        if (payment.getPaymentMode() == null) {
            throw new IllegalArgumentException("Le mode de paiement est obligatoire (CASH, BANK_TRANSFER, MOBILE_BANKING)");
        }

        // Forcer les données de contexte pour la sécurité
        payment.setCollectivityId(collectivityId);
        payment.setTransactionType(PaymentType.IN); // Toujours 'IN' pour un encaissement
    }

    public void processPayment(Integer collectivityId, Payment payment) {
        if (collectivityRepository.findById(collectivityId) == null) {
            throw new NotFoundException("Collectivité introuvable");
        }
        validateAndPrepareTransaction(collectivityId, payment);
        transactionRepository.saveTransaction(payment);
    }
}