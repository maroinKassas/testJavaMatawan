package com.nice.football.controller;

import com.nice.football.DTO.JoueurDTO;
import com.nice.football.service.JoueurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing football players (joueurs).
 */
@RestController
@RequestMapping("/joueurs")
public class JoueurController {
    private final JoueurService joueurService;

    /**
     * Constructor injecting the JoueurService dependency.
     *
     * @param joueurService the service responsible for player operations
     */
    public JoueurController(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    /**
     * Retrieves a paginated and sorted list of players.
     *
     * @param page   the page number to retrieve (default is 0)
     * @param size   the number of items per page (default is 5)
     * @param sortBy the field by which to sort the results (default is "equipe")
     * @return a page of JoueurDTO objects
     */
    @GetMapping
    public Page<JoueurDTO> getAllJoueurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "equipe") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return joueurService.getAllJoueurs(pageable);
    }

    /**
     * Creates a new player using the given data.
     *
     * @param joueurDTO the player information to create
     * @return the created JoueurDTO with its assigned ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JoueurDTO createJoueur(@RequestBody JoueurDTO joueurDTO) {
        return joueurService.createJoueur(joueurDTO);
    }
}
