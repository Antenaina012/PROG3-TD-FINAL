package org.example.examprog3.repository;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.MembershipFee;
import org.example.examprog3.entity.dto.CreateMembershipFee;
import org.example.examprog3.entity.enums.Frequency;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MembershipFeeRepository {
    private final Connection connection;

    public List<MembershipFee> saveAll(String collectivityId, List<CreateMembershipFee> fees) {
        // Requête optimisée - liste explicite des colonnes incluant tous les champs
        String sql = "INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency, eligible_from, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        List<MembershipFee> savedFees = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (CreateMembershipFee fee : fees) {
                String id = "FEE-" + UUID.randomUUID().toString().substring(0, 8);
                pstmt.setString(1, id);
                pstmt.setString(2, collectivityId);
                pstmt.setString(3, fee.getLabel());
                pstmt.setDouble(4, fee.getAmount());
                pstmt.setString(5, fee.getFrequency().name());
                
                // Handle nullable eligibleFrom field
                if (fee.getEligibleFrom() != null) {
                    pstmt.setDate(6, java.sql.Date.valueOf(fee.getEligibleFrom()));
                } else {
                    pstmt.setNull(6, java.sql.Types.DATE);
                }
                
                // Default status is ACTIVE
                pstmt.setString(7, "ACTIVE");
                
                pstmt.addBatch();

                savedFees.add(MembershipFee.builder()
                        .id(id)
                        .collectivityId(collectivityId)
                        .label(fee.getLabel())
                        .amount(fee.getAmount())
                        .frequency(fee.getFrequency())
                        .eligibleFrom(fee.getEligibleFrom())
                        .status(org.example.examprog3.entity.enums.ActivityStatus.ACTIVE)
                        .build());
            }
            pstmt.executeBatch();
            return savedFees;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création des frais", e);
        }
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        // Requête optimisée - liste explicite des colonnes
        String sql = "SELECT id, collectivity_id, label, amount, frequency, eligible_from, status FROM membership_fee WHERE collectivity_id = ?";
        List<MembershipFee> fees = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, collectivityId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MembershipFee fee = MembershipFee.builder()
                        .id(rs.getString("id"))
                        .collectivityId(rs.getString("collectivity_id"))
                        .label(rs.getString("label"))
                        .amount(rs.getDouble("amount"))
                        .frequency(Frequency.valueOf(rs.getString("frequency")))
                        .build();
                
                // Handle nullable fields
                java.sql.Date eligibleFrom = rs.getDate("eligible_from");
                if (eligibleFrom != null) {
                    fee.setEligibleFrom(eligibleFrom.toLocalDate());
                }
                
                String status = rs.getString("status");
                if (status != null) {
                    fee.setStatus(org.example.examprog3.entity.enums.ActivityStatus.valueOf(status));
                }
                
                fees.add(fee);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des frais d'adhésion", e);
        }
        return fees;
    }
}