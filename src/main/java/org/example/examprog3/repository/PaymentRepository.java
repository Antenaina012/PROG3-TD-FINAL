package org.example.examprog3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.example.examprog3.entity.Payment;
import org.example.examprog3.entity.enums.PaymentMode;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PaymentRepository {
    private final Connection connection;

    public Payment saveTransaction(Payment transaction) {
        // Mise à jour : table "transaction", colonnes sans "id_" et avec cast ::account_type
        String sql = """
            INSERT INTO "transaction" 
            (id, member_id, collectivity_id, account_id, amount, label, transaction_date)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String generatedId = transaction.getId() != null ? transaction.getId() : "TXN-" + System.currentTimeMillis();
            stmt.setString(1, generatedId);
            stmt.setString(2, transaction.getMemberId());
            stmt.setString(3, transaction.getCollectivityId());
            stmt.setString(4, transaction.getAccountCreditedIdentifier());
            stmt.setBigDecimal(5, transaction.getAmount());
            stmt.setString(6, transaction.getDescription());

            // On utilise la date fournie dans le PDF (ex: 01/01/2026)
            // ou l'heure actuelle si absente
            stmt.setTimestamp(7, transaction.getTransactionDate() != null ?
                    new Timestamp(transaction.getTransactionDate().getTime()) : new Timestamp(System.currentTimeMillis()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                transaction.setId(rs.getString("id"));
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du paiement : " + e.getMessage(), e);
        }
    }

    public List<Payment> findAllByCollectivityId(String collectivityId) {
        List<Payment> transactions = new ArrayList<>();
        // Requête optimisée - liste explicite des colonnes avec jointure
        String sql = """
            SELECT t.id, t.member_id, t.collectivity_id, t.account_id, 
                   t.amount, t.label, t.transaction_date,
                   a.type as payment_mode 
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
        // Requête optimisée - liste explicite des colonnes
        String sql = """
            SELECT id, member_id, collectivity_id, account_id, amount, label, transaction_date
            FROM "transaction" 
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