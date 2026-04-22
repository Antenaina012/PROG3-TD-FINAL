package org.example.examprog3.Service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.dto.CollectivityResponse;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.repository.CollectivityRepository;
import org.example.examprog3.validator.CollectivityValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CollectivityService {
    private final CollectivityRepository repository;
    private final CollectivityValidator validator;
    private final CollectivityRepository repository;

    public Collectivity attributeIdentification(Long id, String name, String number) {
        Collectivity collectivity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collectivité introuvable"));

        if (collectivity.getName() != null || collectivity.getNumber() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "L'identification a déjà été attribuée et ne peut plus être modifiée."
            );
        }

        collectivity.setName(name);
        collectivity.setNumber(number);

        return repository.save(collectivity);
    }

    public List<CollectivityResponse> createCollectivities(List<CreateCollectivity> createCollectivities) throws BadRequestException {
        List<Collectivity> collectivitiesToSave = new ArrayList<>();
        List<List<Integer>> memberIdsList = new ArrayList<>();
        List<Integer> presidentIds = new ArrayList<>();
        List<Integer> vicePresidentIds = new ArrayList<>();
        List<Integer> treasurerIds = new ArrayList<>();
        List<Integer> secretaryIds = new ArrayList<>();

        for (CreateCollectivity request : createCollectivities) {
            validator.validateCollectivityCreation(request);

            Collectivity collectivity = Collectivity.builder()
                    .number(generateCollectivityNumber())
                    .name(generateCollectivityName(request.getLocation()))
                    .speciality("Agriculture")
                    .federationApproval(request.isFederationApproval())
                    .authorizationDate(Instant.now())
                    .location(request.getLocation())
                    .build();

            collectivitiesToSave.add(collectivity);
            memberIdsList.add(request.getMemberIds());
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
        return "Collectivité de " + locationName;
    }
}