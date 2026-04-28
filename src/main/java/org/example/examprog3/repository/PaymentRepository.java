package org.example.examprog3.repository;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Payment;
import org.example.examprog3.entity.enums.PaymentMode;
import org.example.examprog3.entity.enums.PaymentType;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PaymentRepository {
    private final Connection connection;

    public void saveTransaction(Payment transaction) {
        // Mise à jour : table "transaction", colonnes sans "id_" et avec cast ::account_type
        String sql = """
            INSERT INTO "transaction" 
            (member_id, collectivity_id, account_id, amount, label, transaction_date)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getMemberId());
            stmt.setString(2, transaction.getCollectivityId());
            stmt.setString(3, transaction.getAccountCreditedIdentifier());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setString(5, transaction.getDescription()); // Le label du PDF

            // On utilise la date fournie dans le PDF (ex: 01/01/2026)
            // ou l'heure actuelle si absente
            stmt.setTimestamp(6, transaction.getTransactionDate() != null ?
                    new Timestamp(transaction.getTransactionDate().getTime()) : new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du paiement : " + e.getMessage(), e);
        }
    }

    public List<Payment> findAllByCollectivityId(String collectivityId) {
        List<Payment> transactions = new ArrayList<>();
        // Jointure avec financial_account pour récupérer le payment_mode
        String sql = """
            SELECT t.*, a.type as payment_mode 
            FROM "transaction" t
            JOIN financial_account a ON t.account_id = a.id
            WHERE t.collectivity_id = ? 
            ORDER BY t.transaction_date DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(Payment.builder()
                            .id(rs.getString("id"))
                            .memberId(rs.getString("member_id"))
                            .collectivityId(rs.getString("collectivity_id"))
                            .accountCreditedIdentifier(rs.getString("account_id"))
                            .amount(rs.getBigDecimal("amount"))
                            .paymentMode(PaymentMode.valueOf(rs.getString("payment_mode")))
                            .description(rs.getString("label"))
                            .transactionDate(rs.getTimestamp("transaction_date"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions", e);
        }
        return transactions;
    }

    public List<Payment> findTransactionsByPeriod(String id, String from, String to) {
        List<Payment> transactions = new ArrayList<>();
        String sql = """
            SELECT * FROM "transaction" 
            WHERE collectivity_id = ? 
            AND transaction_date BETWEEN ?::timestamp AND ?::timestamp 
            ORDER BY transaction_date DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, from + " 00:00:00");
            stmt.setString(3, to + " 23:59:59");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(Payment.builder()
                            .id(rs.getString("id"))
                            .memberId(rs.getString("member_id"))
                            .collectivityId(rs.getString("collectivity_id"))
                            .amount(rs.getBigDecimal("amount"))
                            .description(rs.getString("label"))
                            .transactionDate(rs.getTimestamp("transaction_date"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL lors de la recherche par période", e);
        }
        return transactions;
    }
}