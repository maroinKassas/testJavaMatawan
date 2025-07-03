package com.nice.football.controller;

import com.nice.football.DTO.EquipeDTO;
import com.nice.football.service.EquipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing football teams.
 */
@RestController
@RequestMapping("/equipes")
public class EquipeController {
    private final EquipeService equipeService;

    /**
     * Constructor injecting the EquipeService dependency.
     *
     * @param equipeService the service handling team operations
     */
    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    /**
     * Retrieves a paginated and sorted list of teams.
     *
     * @param page   the page number (default is 0)
     * @param size   the number of items per page (default is 5)
     * @param sortBy the field to sort by (default is "name")
     * @return a page of EquipeDTO objects
     */
    @GetMapping
    public Page<EquipeDTO> getAllEquipes(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size,
                                         @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return equipeService.getAllEquipes(pageable);
    }

    /**
     * Creates a new team with the provided information.
     *
     * @param equipeDTO the team data to create
     * @return the created team with its generated ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipeDTO createEquipe(@RequestBody EquipeDTO equipeDTO) {
        return equipeService.createEquipe(equipeDTO);
    }
}

