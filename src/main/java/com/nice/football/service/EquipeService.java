package com.nice.football.service;

import com.nice.football.DTO.EquipeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing football teams (Equipes).
 */
public interface EquipeService {

    /**
     * Retrieves a paginated list of all equipes.
     *
     * @param pageable pagination and sorting information
     * @return a page of {@link EquipeDTO}
     */
    Page<EquipeDTO> getAllEquipes(Pageable pageable);

    /**
     * Creates a new equipe.
     *
     * @param equipeDTO the equipe data to create
     * @return the created {@link EquipeDTO}
     */
    EquipeDTO createEquipe(EquipeDTO equipeDTO);
}
