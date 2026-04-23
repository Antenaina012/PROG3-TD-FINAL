package org.example.examprog3.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.FinancialAccount;
import org.example.examprog3.entity.dto.CollectivityResponse;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.validator.CollectivityValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor // Gère l'injection de repository et validator automatiquement
public class CollectivityService {

    private final CollectivityRepository repository;
    private final CollectivityValidator validator;

    public Collectivity assignIdentity(Integer id, String newNumber, String newName) {
        Collectivity collectivity = repository.findById(id);

        if (collectivity == null) {
            throw new RuntimeException("Collectivité introuvable ID: " + id);
        }

        if (isIdentityFixed(collectivity)) {
            throw new IllegalStateException("L'identité est déjà fixée et ne peut plus être modifiée.");
        }

        if (repository.existsByName(newName)) {
            throw new IllegalArgumentException("Le nom '" + newName + "' est déjà utilisé.");
        }

        repository.updateIdentity(id, newNumber, newName);

        return repository.findById(id);
    }

    private boolean isIdentityFixed(Collectivity c) {
        return (c.getNumber() != null && !c.getNumber().isBlank()) ||
                (c.getName() != null && !c.getName().isBlank());
    }

    public List<CollectivityResponse> createCollectivities(List<CreateCollectivity> createRequests) throws BadRequestException {
        List<Collectivity> collectivitiesToSave = new ArrayList<>();
        List<List<Integer>> memberIdsList = new ArrayList<>();
        List<Integer> presidentIds = new ArrayList<>();
        List<Integer> vicePresidentIds = new ArrayList<>();
        List<Integer> treasurerIds = new ArrayList<>();
        List<Integer> secretaryIds = new ArrayList<>();

        for (CreateCollectivity request : createRequests) {
            // Validation personnalisée (vérifie les IDs de membres, etc.)
            validator.validateCollectivityCreation(request);

            Collectivity collectivity = Collectivity.builder()
                    .number(generateCollectivityNumber())
                    .name(generateCollectivityName(request.getLocation()))
                    .speciality("Agriculture")
                    .federationApproval(request.isFederationApproval())
                    .authorizationDate(Date.from(Instant.now()))
                    .location(request.getLocation())
                    .build();

            collectivitiesToSave.add(collectivity);
            memberIdsList.add(request.getMemberIds());

            // Extraction des IDs de la structure
            presidentIds.add(request.getStructure().getPresidentId());
            vicePresidentIds.add(request.getStructure().getVicePresidentId());
            treasurerIds.add(request.getStructure().getTreasurerId());
            secretaryIds.add(request.getStructure().getSecretaryId());
        }

        List<Collectivity> savedCollectivities = repository.saveAll(
                collectivitiesToSave,
                memberIdsList,
                presidentIds,
                vicePresidentIds,
                treasurerIds,
                secretaryIds
        );

        return savedCollectivities.stream()
                .map(this::buildResponse)
                .toList();
    }

    private CollectivityResponse buildResponse(Collectivity collectivity) {
        return CollectivityResponse.builder()
                .id(String.valueOf(collectivity.getId()))
                .location(collectivity.getLocation())
                .structure(collectivity.getStructure())
                .members(collectivity.getMembers())
                .build();
    }

    private String generateCollectivityNumber() {
        return "COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateCollectivityName(String locationName) {
        return "Collectivité de " + locationName + " " + UUID.randomUUID().toString().substring(0, 4);
    }

    public Collectivity getById(Integer id) {
        Collectivity collectivity = repository.findById(id);
        if (collectivity == null) {
            // Optionnel : Tu peux créer une classe ResourceNotFoundException pour un retour 404 propre
            throw new RuntimeException("Collectivité non trouvée pour l'ID : " + id);
        }
        return collectivity;
    }

    public List<FinancialAccount> getFinancialAccountsWithBalance(Integer id, String atDate) {
        this.getById(id);

        return repository.findAccountsWithBalance(id, atDate);
    }
}