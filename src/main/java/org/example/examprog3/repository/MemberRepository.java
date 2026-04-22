package org.example.examprog3.repository;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.enums.Gender;
import org.example.examprog3.entity.enums.MemberOccupation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final Connection connection;

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String query = "SELECT id, first_name, last_name, birth_date, gender, join_date, occupation, id_collectivity " +
                "FROM member";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                members.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching members", e);
        }
        return members;
    }

    public List<Member> findAllById(List<String> ids) {
        List<Member> members = new ArrayList<>();
        // Colonnes explicites au lieu de SELECT *
        String query = "SELECT id, first_name, last_name, birth_date, gender, join_date, occupation, id_collectivity " +
                "FROM member WHERE id = ANY(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Array array = connection.createArrayOf("VARCHAR", ids.toArray());
            statement.setArray(1, array);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                members.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching members", e);
        }
        return members;
    }

    public void save(Member member) {
        String query = "INSERT INTO member (id, first_name, last_name, birth_date, gender, join_date, occupation, id_collectivity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getId());
            statement.setString(2, member.getFirstName());
            statement.setString(3, member.getLastName());
            statement.setDate(4, Date.valueOf(member.getBirthDate()));
            statement.setString(5, member.getGender().name());
            statement.setDate(6, Date.valueOf(member.getJoinDate()));
            statement.setString(7, member.getOccupation().name());
            statement.setString(8, member.getCollectivity() != null ? member.getCollectivity().getId() : null);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while saving member", e);
        }
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId(rs.getString("id"));
        m.setFirstName(rs.getString("first_name"));
        m.setLastName(rs.getString("last_name"));
        m.setBirthDate(rs.getDate("birth_date").toLocalDate());
        m.setGender(Gender.valueOf(rs.getString("gender")));
        m.setJoinDate(rs.getDate("join_date").toLocalDate());
        m.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));
        return m;
    }
}