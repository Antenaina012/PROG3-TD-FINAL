package org.example.examprog3.repository;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.FinancialAccount;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.Structure;
import org.example.examprog3.entity.enums.CollectivityOccupation;
import org.example.examprog3.entity.enums.Gender;
import org.example.examprog3.entity.enums.PaymentMode;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;

@Repository
@AllArgsConstructor
public class CollectivityRepository {
    private final Connection connection;

    public Collectivity saveAll(Collectivity collectivity, List<String> memberIds,
                             String presidentId, String vicePresidentId,
                             String treasurerId, String secretaryId) {
        // Mise à jour : unique_number, location, speciality
        String insertCollectivitySql = """
            INSERT INTO collectivity (id, unique_number, name, location, speciality, federation_approval, creation_date)
            VALUES (?, ?, ?, ?, ?, ?, now())
            RETURNING id
        """;

        // Mise à jour : table 'membership' et colonnes id_collectivity -> collectivity_id
        String insertMemberSql = """
            INSERT INTO membership (member_id, collectivity_id, role, begin_date)
            VALUES (?, ?, ?::role_type, CURRENT_DATE)
        """;

        try {
            connection.setAutoCommit(false);
            String collectivityId;
            try (PreparedStatement stmt = connection.prepareStatement(insertCollectivitySql)) {
                stmt.setString(1, collectivity.getId());
                stmt.setString(2, collectivity.getNumber());
                stmt.setString(3, collectivity.getName());
                stmt.setString(4, collectivity.getLocation());
                stmt.setString(5, collectivity.getSpeciality());
                stmt.setBoolean(6, collectivity.isFederationApproval());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    collectivityId = rs.getString("id");
                } else {
                    throw new SQLException("Failed to insert collectivity");
                }
            }

            try (PreparedStatement memberStmt = connection.prepareStatement(insertMemberSql)) {
                for (String memberId : memberIds) {
                    memberStmt.setString(1, memberId);
                    memberStmt.setString(2, collectivityId);
                    // On caste en ::role_type pour PostgreSQL
                    memberStmt.setString(3, determineOccupation(memberId, presidentId, vicePresidentId, treasurerId, secretaryId));
                    memberStmt.addBatch();
                }
                memberStmt.executeBatch();
            }
            connection.commit();
            return findById(collectivityId);
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) { /* ignored */ }
            throw new RuntimeException(e);
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { /* ignored */ }
        }
    }

    public Collectivity findById(String id) {
        String sql = "SELECT * FROM collectivity WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Collectivity collectivity = Collectivity.builder()
                            .id(rs.getString("id"))
                            .name(rs.getString("name"))
                            .number(rs.getString("unique_number")) // Changement nom colonne
                            .location(rs.getString("location"))
                            .speciality(rs.getString("speciality"))
                            .federationApproval(rs.getBoolean("federation_approval"))
                            .build();

                    fetchMembersAndStructure(collectivity);
                    return collectivity;
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    private void fetchMembersAndStructure(Collectivity collectivity) {
        // Mise à jour : table membership, colonnes role et collectivity_id
        String sql = """
            SELECT m.*, ms.role FROM membership ms
            JOIN member m ON ms.member_id = m.id
            WHERE ms.collectivity_id = ? AND ms.end_date IS NULL
        """;
        List<Member> members = new ArrayList<>();
        Structure structure = Structure.builder().build();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, collectivity.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Member member = Member.builder()
                        .id(rs.getString("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .gender(Gender.valueOf(rs.getString("gender"))) // Attention type ENUM
                        .build();
                members.add(member);

                String role = rs.getString("role");
                if (role != null) {
                    switch (CollectivityOccupation.valueOf(role)) {
                        case PRESIDENT -> structure.setPresident(member);
                        case VICE_PRESIDENT -> structure.setVicePresident(member);
                        case TREASURER -> structure.setTreasurer(member);
                        case SECRETARY -> structure.setSecretary(member);
                    }
                }
            }
            collectivity.setMembers(members);
            collectivity.setStructure(structure);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<FinancialAccount> findAccountsWithBalance(String collectivityId, String atDate) {
        // Mise à jour : table financial_account et colonnes amount
        List<FinancialAccount> accounts = new ArrayList<>();
        String sql = """
            SELECT a.id, a.label, a.type,
            COALESCE(SUM(t.amount), 0) as balance
            FROM financial_account a
            LEFT JOIN transaction t ON a.id = t.account_id AND t.transaction_date <= ?::timestamp
            WHERE a.collectivity_id = ?
            GROUP BY a.id, a.label, a.type
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, atDate + " 23:59:59");
            stmt.setString(2, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accounts.add(FinancialAccount.builder()
                        .id(rs.getString("id"))
                        .label(rs.getString("label"))
                        .type(PaymentMode.valueOf(rs.getString("type")))
                        .balance(rs.getBigDecimal("balance"))
                        .build());
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return accounts;
    }

    public void updateIdentity(String id, String number, String name) {
        // Mise à jour : unique_number
        String sql = "UPDATE collectivity SET unique_number = ?, name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, number);
            stmt.setString(2, name);
            stmt.setString(3, id);
            if (stmt.executeUpdate() == 0) throw new SQLException("Update failed");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT count(*) FROM collectivity WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private String determineOccupation(String memberId, String pres, String vice, String treas, String sec) {
        if (memberId.equals(pres)) return "PRESIDENT";
        if (memberId.equals(vice)) return "VICE_PRESIDENT";
        if (memberId.equals(treas)) return "TREASURER";
        if (memberId.equals(sec)) return "SECRETARY";
        return hasMinimumSeniority(memberId) ? "CONFIRMED" : "JUNIOR";
    }

    private boolean hasMinimumSeniority(String memberId) {
        String sql = "SELECT enrolment_date FROM member WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp enrolmentDate = rs.getTimestamp("enrolment_date");
                if (enrolmentDate == null) return false;
                // Calcul des 6 mois d'ancienneté requis pour être "CONFIRMED"
                return ChronoUnit.MONTHS.between(enrolmentDate.toLocalDateTime(), LocalDateTime.now()) >= 6;
            }
            return false;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    // Dans CollectivityRepository.java
    public boolean existsById(String id) {
        String sql = "SELECT count(*) FROM collectivity WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'ID collectivité", e);
        }
    }
}