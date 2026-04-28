package org.example.examprog3.repository;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.MemberCollectivity;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.entity.enums.CollectivityOccupation;
import org.example.examprog3.entity.enums.Gender;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.util.*;

@Repository
@AllArgsConstructor
public class MemberRepository {

    private final Connection connection;

    public List<Member> findByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));

        // Mise à jour : table 'membership' et colonnes id_... -> ..._id
        String sql = """
        SELECT
            m.id AS m_id, m.first_name, m.last_name, m.birth_date, m.enrolment_date,
            m.address, m.email, m.phone_number, m.profession, m.gender,
            ms.role AS occupation, ms.begin_date AS start_date, ms.end_date,
            c.id AS c_id, c.name, c.unique_number AS number, c.speciality, c.location
        FROM member m
        LEFT JOIN membership ms ON m.id = ms.member_id
        LEFT JOIN collectivity c ON ms.collectivity_id = c.id
        WHERE m.id IN (%s)
        """.formatted(placeholders);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < ids.size(); i++) {
                stmt.setString(i + 1, ids.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            Map<String, Member> map = new LinkedHashMap<>(); // LinkedHashMap pour garder l'ordre du PDF

            while (rs.next()) {
                String id = rs.getString("m_id");
                Member member = map.computeIfAbsent(id, k -> {
                    try { return mapBasicMember(rs); }
                    catch (SQLException e) { throw new RuntimeException(e); }
                });

                if (rs.getString("c_id") != null) {
                    member.getMemberCollectivities().add(mapMemberCollectivity(rs, member));
                }
            }
            return new ArrayList<>(map.values());
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Member> saveAll(List<Member> members, List<CreateMember> dtos) {
        // Ajout des colonnes de paiement pour éviter les erreurs 500
        String insertMemberSql = """
        INSERT INTO member(id, first_name, last_name, birth_date, gender, enrolment_date, address, email, phone_number, profession)
        VALUES (?, ?, ?, ?, ?::gender_type, ?, ?, ?, ?, ?)
        """;

        String insertMsSql = """
        INSERT INTO membership(member_id, collectivity_id, role, begin_date)
        VALUES (?, ?, ?::role_type, ?)
        """;

        String insertRefSql = """
        INSERT INTO referee(sponsor_id, sponsored_id)
        VALUES (?, ?)
        """;

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement memberStmt = connection.prepareStatement(insertMemberSql);
                 PreparedStatement msStmt = connection.prepareStatement(insertMsSql);
                 PreparedStatement refStmt = connection.prepareStatement(insertRefSql)) {

                for (int i = 0; i < members.size(); i++) {
                    Member member = members.get(i);
                    CreateMember dto = dtos.get(i);

                    // 1. Insertion Membre
                    memberStmt.setString(1, member.getId());
                    memberStmt.setString(2, member.getFirstName());
                    memberStmt.setString(3, member.getLastName());
                    memberStmt.setDate(4, Date.valueOf(member.getBirthDate()));
                    memberStmt.setString(5, member.getGender().name());
                    // On utilise la date d'enrôlement du DTO pour les tests de parrainage
                    memberStmt.setTimestamp(6, Timestamp.from(member.getEnrolmentDate() != null ? member.getEnrolmentDate() : Instant.now()));
                    memberStmt.setString(7, member.getAddress());
                    memberStmt.setString(8, member.getEmail());
                    memberStmt.setString(9, member.getPhoneNumber());
                    memberStmt.setString(10, member.getProfession());
                    memberStmt.executeUpdate();

                    // 2. Liaison Collectivité (Membership)
                    msStmt.setString(1, member.getId());
                    msStmt.setString(2, dto.getCollectivityIdentifier());
                    msStmt.setString(3, dto.getOccupation() != null ? dto.getOccupation().name() : "JUNIOR");
                    msStmt.setTimestamp(4, Timestamp.from(Instant.now()));
                    msStmt.executeUpdate();

                    // 3. Parrainages (Referees)
                    if (dto.getReferees() != null) {
                        for (String sponsorId : dto.getReferees()) {
                            refStmt.setString(1, sponsorId);
                            refStmt.setString(2, member.getId());
                            refStmt.addBatch();
                        }
                        refStmt.executeBatch();
                    }
                }
                connection.commit();
                return members;
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("Save members failed: " + e.getMessage(), e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Member mapBasicMember(ResultSet rs) throws SQLException {
        return Member.builder()
                .id(rs.getString("m_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .birthDate(rs.getDate("birth_date").toLocalDate())
                .enrolmentDate(rs.getTimestamp("enrolment_date").toInstant())
                .address(rs.getString("address"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .profession(rs.getString("profession"))
                .gender(Gender.valueOf(rs.getString("gender")))
                .memberCollectivities(new ArrayList<>())
                .build();
    }

    private MemberCollectivity mapMemberCollectivity(ResultSet rs, Member member) throws SQLException {
        Collectivity c = Collectivity.builder()
                .id(rs.getString("c_id"))
                .name(rs.getString("name"))
                .number(rs.getString("number"))
                .location(rs.getString("location"))
                .build();

        return MemberCollectivity.builder()
                .startDate(rs.getTimestamp("start_date").toInstant())
                .endDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toInstant() : null)
                .occupation(CollectivityOccupation.valueOf(rs.getString("occupation")))
                .member(member)
                .collectivity(c)
                .build();
    }
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id); // Utilisation directe du String (ex: C1-M1)

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence du membre : " + id, e);
        }
        return false;
    }
}