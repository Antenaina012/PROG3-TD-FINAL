package org.example.examprog3.Service;


import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Federation;
import org.example.examprog3.exeption.NotFoundException;
import org.example.examprog3.repository.FederationRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FederationService {
    private final FederationRepository federationRepository;

    public Federation getFederation() {
        return federationRepository.findFederation()
                .orElseThrow(() -> new NotFoundException("Federation not found"));
    }
}