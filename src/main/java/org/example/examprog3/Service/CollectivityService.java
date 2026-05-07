package org.example.examprog3.Service;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.repository.MemberRepository;
import org.example.examprog3.validator.CollectivityValidator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectivityService {
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;
    private final CollectivityValidator collectivityValidator;
    private final Connection connection;

    public List<Collectivity> getAllCollectivities() {
        return collectivityRepository.findAll();
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivity> dtos) {
        List<Collectivity> created = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            for (CreateCollectivity dto : dtos) {
                // Récupérer les membres fondateurs pour valider les 10 requis
                List<Member> members = memberRepository.findAllById(dto.memberIds());
                collectivityValidator.validateCreation(dto, members);

                Collectivity c = new Collectivity();
                c.setId(UUID.randomUUID().toString());
                c.setName(dto.name());
                c.setLocation(dto.location());
                c.setSpecialty(dto.specialty());
                c.setCreationDate(java.time.LocalDate.now());

                collectivityRepository.save(c);
                created.add(c);
            }
            connection.commit();
        } catch (Exception e) {
            try { connection.rollback(); } catch (SQLException se) { se.printStackTrace(); }
            throw new RuntimeException(e.getMessage());
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException se) { se.printStackTrace(); }
        }
        return created;
    }
}