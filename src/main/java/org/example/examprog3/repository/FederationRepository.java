package org.example.examprog3.repository;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.entity.Federation;
import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class FederationRepository {
    private final Connection connection;

    public Federation findCurrent() {
        String query = "SELECT id, name, headquarter_location FROM federation LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Federation(rs.getString("id"), rs.getString("name"), rs.getString("headquarter_location"));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }
}