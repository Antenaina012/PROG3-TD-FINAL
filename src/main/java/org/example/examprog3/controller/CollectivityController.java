package org.example.examprog3.controller;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.Service.CollectivityService;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
@RequiredArgsConstructor
public class CollectivityController {
    private final CollectivityService collectivityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Collectivity> getAllCollectivities() {
        return collectivityService.getAllCollectivities();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Collectivity> createCollectivities(@RequestBody List<CreateCollectivity> dtos) {
        return collectivityService.createCollectivities(dtos);
    }
}