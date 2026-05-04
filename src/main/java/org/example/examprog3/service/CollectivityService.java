package org.example.examprog3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.FinancialAccount;
import org.example.examprog3.entity.MembershipFee;
import org.example.examprog3.entity.dto.CollectivityResponse;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.entity.dto.CreateMembershipFee;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.repository.MembershipFeeRepository;
import org.example.examprog3.validator.CollectivityValidator;
import org.example.examprog3.validator.MembershipFeeValidator;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CollectivityService {

    private final CollectivityRepository repository;
    private final CollectivityValidator validator;
    private final MembershipFeeRepository feeRepository;
    private final MembershipFeeValidator feeValidator;

    public Collectivity assignIdentity(String id, String newNumber, String newName) {
        // Utilisation directe du String id
        Collectivity collectivity = repository.findById(id);

        if (collectivity == null) {
            throw new NotFoundException("Collectivity not found with ID: " + id);
        }

        if (isIdentityFixed(collectivity)) {
            throw new IllegalStateException("Identity is already fixed and cannot be modified");
        }

        if (repository.existsByName(newName)) {
            throw new IllegalArgumentException("Name '" + newName + "' is already in use");
        }

        // Plus de Integer.valueOf(id)
        repository.updateIdentity(id, newNumber, newName);

        return repository.findById(id);
    }

    private boolean isIdentityFixed(Collectivity c) {
        return (c.getNumber() != null && !c.getNumber().isBlank()) ||
                (c.getName() != null && !c.getName().isBlank());
    }

    public List<CollectivityResponse> createCollectivities(List<CreateCollectivity> createRequests) throws BadRequestException {
        List<CollectivityResponse> responses = new ArrayList<>();

        for (CreateCollectivity request : createRequests) {
            // 1. Validation
            validator.validateCollectivityCreation(request);

            // 2. Construction de l'objet Collectivity (Données du PDF)
            Collectivity collectivity = Collectivity.builder()
                    .id(request.getId())
                    .number(request.getNumber())
                    .name(request.getName())
                    .location(request.getLocation())
                    .speciality(request.getSpeciality())
                    .federationApproval(request.isFederationApproval())
                    .build();

            // 3. Appel du SAVE individuel (IMPORTANT)
            // Ici on passe UN id de président, pas une liste.
            Collectivity saved = repository.saveAll(
                    collectivity,
                    request.getMembers(), // C'est une List<String> pour un seul membre
                    request.getStructure().getPresidentId(), // C'est un String
                    request.getStructure().getVicePresidentId(), // C'est un String
                    request.getStructure().getTreasurerId(), // C'est un String
                    request.getStructure().getSecretaryId() // C'est un String
            );

            responses.add(buildResponse(saved));
        }

        return responses;
    }

    private CollectivityResponse buildResponse(Collectivity collectivity) {
        return CollectivityResponse.builder()
                .id(collectivity.getId())
                .name(collectivity.getName())
                .number(collectivity.getNumber())
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

    public Collectivity getById(String id) {
        // Nettoyage : plus de conversion Integer -> String
        Collectivity collectivity = repository.findById(id);
        if (collectivity == null) {
            throw new NotFoundException("Collectivity not found with ID: " + id);
        }
        return collectivity;
    }

    public List<FinancialAccount> getFinancialAccountsWithBalance(String id, String atDate) {
        // Validation de l'existence de la collectivité
        this.getById(id);

        return repository.findAccountsWithBalance(id, atDate);
    }

    public List<MembershipFee> createMembershipFees(String collectivityId, List<CreateMembershipFee> fees) throws BadRequestException {
        // 1. Vérifier si la collectivité existe
        if (!repository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found with ID: " + collectivityId);
        }
        // 2. Valider les données
        feeValidator.validate(fees);
        // 3. Sauvegarder
        return feeRepository.saveAll(collectivityId, fees);
    }

    public List<MembershipFee> getMembershipFees(String collectivityId) {
        return feeRepository.findByCollectivityId(collectivityId);
    }
}