package org.example.examprog3.Service;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.entity.Federation;
import org.example.examprog3.repository.FederationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FederationService {
    private final FederationRepository federationRepository;

    public Federation getFederationDetails() {
        return federationRepository.findCurrent();
    }

    // Ici tu ajouteras les méthodes pour les statistiques (Taux d'assiduité, etc.)
}
