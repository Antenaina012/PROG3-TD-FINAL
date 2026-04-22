package org.example.examprog3.repository;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.entity.Collectivity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectivityRepository {
    private final Connection connection;

    public List<Collectivity> findAll() {
        List<Collectivity> collectivities = new ArrayList<>();
        String query = "SELECT id, name, location, specialty, creation_date FROM collectivity";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Collectivity c = new Collectivity();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setLocation(rs.getString("location"));
                c.setSpecialty(rs.getString("specialty"));
                c.setCreationDate(rs.getDate("creation_date").toLocalDate());
                collectivities.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching collectivities", e);
        }
        return collectivities;
    }

    public Collectivity save(Collectivity collectivity) {
        String query = "INSERT INTO collectivity (id, name, location, specialty, creation_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, collectivity.getId());
            statement.setString(2, collectivity.getName());
            statement.setString(3, collectivity.getLocation());
            statement.setString(4, collectivity.getSpecialty());
            statement.setDate(5, Date.valueOf(collectivity.getCreationDate()));

            statement.executeUpdate();
            return collectivity;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving collectivity", e);
        }
    }
}