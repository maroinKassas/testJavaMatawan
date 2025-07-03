package com.nice.football.service;

import com.nice.football.DTO.JoueurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing Joueurs (players).
 */
public interface JoueurService {

    /**
     * Retrieves a paginated list of all joueurs (players).
     *
     * @param pageable the pagination and sorting information
     * @return a page of JoueurDTO objects
     */
    Page<JoueurDTO> getAllJoueurs(Pageable pageable);

    /**
     * Creates a new joueur (player).
     *
     * @param joueurDTO the player data to create
     * @return the created JoueurDTO
     */
    JoueurDTO createJoueur(JoueurDTO joueurDTO);
}
