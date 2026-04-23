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
        String sql = """
            INSERT INTO "transaction" 
            (id_member, id_collectivity, id_cotisation_plan, id_account, amount, payment_mode, description, transaction_type, transaction_date)
            VALUES (?, ?, ?, ?, ?, ?::payment_mode, ?, 'IN', now())
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(transaction.getMemberId()));
            stmt.setInt(2, Integer.parseInt(transaction.getCollectivityId()));

            if (transaction.getMembershipFeeIdentifier() != null) {
                stmt.setInt(3, Integer.parseInt(transaction.getMembershipFeeIdentifier()));
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, Integer.parseInt(transaction.getAccountCreditedIdentifier()));
            stmt.setBigDecimal(5, transaction.getAmount());
            stmt.setString(6, transaction.getPaymentMode().toString());
            stmt.setString(7, transaction.getDescription());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du paiement", e);
        }

    }
    public List<Payment> findAllByCollectivityId(Integer collectivityId) {
        List<Payment> transactions = new ArrayList<>();
        String sql = "SELECT * FROM \"transaction\" WHERE id_collectivity = ? ORDER BY transaction_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, collectivityId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(Payment.builder()
                            .id(String.valueOf(rs.getInt("id")))
                            .memberId(String.valueOf(rs.getInt("id_member")))
                            .collectivityId(String.valueOf(rs.getInt("id_collectivity")))
                            .membershipFeeIdentifier(String.valueOf(rs.getObject("id_cotisation_plan") != null ? rs.getInt("id_cotisation_plan") : null))
                            .accountCreditedIdentifier(String.valueOf(rs.getInt("id_account")))
                            .amount(rs.getBigDecimal("amount"))
                            .paymentMode(PaymentMode.valueOf(rs.getString("payment_mode")))
                            .transactionType(PaymentType.valueOf(rs.getString("transaction_type")))
                            .description(rs.getString("description"))
                            .transactionDate(rs.getTimestamp("transaction_date"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions", e);
        }
        return transactions;
    }
    public List<Payment> findTransactionsByPeriod(Integer id, String from, String to) {
        List<Payment> transactions = new ArrayList<>();
        // Requête SQL filtrant par collectivité ET par période
        String sql = """
        SELECT * FROM "transaction" 
        WHERE id_collectivity = ? 
        AND transaction_date BETWEEN ?::date AND ?::date 
        ORDER BY transaction_date DESC
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, from); // "2026-01-01"
            stmt.setString(3, to);   // "2026-12-31"

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(Payment.builder()
                            .id(String.valueOf(rs.getInt("id")))
                            .memberId(String.valueOf(rs.getInt("id_member")))
                            .collectivityId(String.valueOf(rs.getInt("id_collectivity")))
                            .amount(rs.getBigDecimal("amount"))
                            .description(rs.getString("description"))
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