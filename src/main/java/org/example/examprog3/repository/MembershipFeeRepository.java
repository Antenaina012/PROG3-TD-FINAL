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
        String sql = "INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency) VALUES (?, ?, ?, ?, ?)";
        List<MembershipFee> savedFees = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (CreateMembershipFee fee : fees) {
                String id = "FEE-" + UUID.randomUUID().toString().substring(0, 8);
                pstmt.setString(1, id);
                pstmt.setString(2, collectivityId);
                pstmt.setString(3, fee.getLabel());
                pstmt.setDouble(4, fee.getAmount());
                pstmt.setString(5, fee.getFrequency().name());
                pstmt.addBatch();

                savedFees.add(MembershipFee.builder()
                        .id(id)
                        .collectivityId(collectivityId)
                        .label(fee.getLabel())
                        .amount(fee.getAmount())
                        .frequency(fee.getFrequency())
                        .build());
            }
            pstmt.executeBatch();
            return savedFees;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création des frais", e);
        }
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        String sql = "SELECT * FROM membership_fee WHERE collectivity_id = ?";
        List<MembershipFee> fees = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, collectivityId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fees.add(MembershipFee.builder()
                        .id(rs.getString("id"))
                        .label(rs.getString("label"))
                        .amount(rs.getDouble("amount"))
                        .frequency(Frequency.valueOf(rs.getString("frequency")))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fees;
    }
}