package org.example.examprog3.Service;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Transaction;
import org.example.examprog3.entity.enums.TransactionType;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private final TransactionRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;

    public void processPayments(Integer collectivityId, List<Transaction> payments) {
        if (collectivityRepository.findById(collectivityId) == null) {
            throw new NotFoundException("Collectivity not found with ID: " + collectivityId);
        }

        for (Transaction payment : payments) {
            validateAndPrepareTransaction(collectivityId, payment);
            transactionRepository.saveTransaction(payment);
        }
    }

    public List<Transaction> getTransactionsByPeriod(Integer id, String from, String to) {
        return transactionRepository.findTransactionsByPeriod(id, from, to);
    }

    private void validateAndPrepareTransaction(Integer collectivityId, Transaction payment) {
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (payment.getPaymentMode() == null) {
            throw new IllegalArgumentException("Payment mode is required (CASH, BANK_TRANSFER, MOBILE_BANKING)");
        }

        payment.setCollectivityId(collectivityId);
        payment.setTransactionType(TransactionType.IN); // Toujours 'IN' pour un encaissement
    }

    public void processPayment(Integer collectivityId, Transaction payment) {
        if (collectivityRepository.findById(collectivityId) == null) {
            throw new NotFoundException("Collectivity not found");
        }
        validateAndPrepareTransaction(collectivityId, payment);
        transactionRepository.saveTransaction(payment);
    }
}
