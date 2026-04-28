package org.example.examprog3.repository;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Federation;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.Structure;
import org.example.examprog3.entity.enums.FederationOccupation;
import org.example.examprog3.entity.enums.Gender;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FederationRepository {
    private final Connection connection;

    public Optional<Federation> findFederation() {
        // Mise à jour des noms de colonnes et tables
        String sql = """
            SELECT 
                f.id as federation_id,
                f.cotisation_percentage,
                mf.member_id,
                mf.role as occupation,
                m.first_name,
                m.last_name,
                m.birth_date,
                m.enrolment_date,
                m.address,
                m.email,
                m.phone_number,
                m.profession,
                m.gender
            FROM federation f
            LEFT JOIN federation_membership mf ON f.id = mf.federation_id AND mf.end_date IS NULL
            LEFT JOIN member m ON mf.member_id = m.id
            ORDER BY f.id
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            Federation federation = null;
            Structure structure = Structure.builder().build();

            while (rs.next()) {
                if (federation == null) {
                    federation = Federation.builder()
                            .id(rs.getString("federation_id"))
                            .contributionPercentage(rs.getDouble("cotisation_percentage"))
                            .build();
                }

                String memberId = rs.getString("member_id");
                if (memberId != null) {
                    Member member = mapResultSetToMember(rs);
                    String occupation = rs.getString("occupation");

                    if (occupation != null) {
                        // Utilisation du try-catch ou switch pour mapper les rôles
                        try {
                            switch (FederationOccupation.valueOf(occupation)) {
                                case PRESIDENT -> structure.setPresident(member);
                                case VICE_PRESIDENT -> structure.setVicePresident(member);
                                case TREASURER -> structure.setTreasurer(member);
                                case SECRETARY -> structure.setSecretary(member);
                            }
                        } catch (IllegalArgumentException e) {
                            // Log ou gestion si le rôle en base ne match pas l'Enum Java
                        }
                    }
                }
            }

            if (federation != null) {
                federation.setStructure(structure);
                return Optional.of(federation);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch federation", e);
        }
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        return Member.builder()
                .id(rs.getString("member_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .birthDate(rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null)
                .enrolmentDate(rs.getTimestamp("enrolment_date") != null ? rs.getTimestamp("enrolment_date").toInstant() : null)
                .address(rs.getString("address"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .profession(rs.getString("profession"))
                // Gestion sécurisée de l'Enum Gender
                .gender(rs.getString("gender") != null ? Gender.valueOf(rs.getString("gender")) : null)
                .build();
    }
}