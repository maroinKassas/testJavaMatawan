package com.nice.football.service;

import com.nice.football.DTO.JoueurDTO;
import com.nice.football.entity.Equipe;
import com.nice.football.entity.Joueur;
import com.nice.football.mapper.JoueurMapper;
import com.nice.football.repository.EquipeRepository;
import com.nice.football.repository.JoueurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the JoueurService interface.
 * Handles business logic for managing players (Joueurs).
 */
@Service
public class JoueurServiceImpl implements JoueurService {

    private final EquipeRepository equipeRepository;
    private final JoueurRepository joueurRepository;
    private final JoueurMapper joueurMapper;

    /**
     * Constructor for dependency injection.
     *
     * @param equipeRepository  the repository for Equipe entities
     * @param joueurRepository  the repository for Joueur entities
     * @param joueurMapper      the mapper to convert between entity and DTO
     */
    public JoueurServiceImpl(EquipeRepository equipeRepository, JoueurRepository joueurRepository, JoueurMapper joueurMapper) {
        this.equipeRepository = equipeRepository;
        this.joueurRepository = joueurRepository;
        this.joueurMapper = joueurMapper;
    }

    /**
     * Retrieves a paginated list of all joueurs (players).
     *
     * @param pageable the pagination and sorting parameters
     * @return a Page of JoueurDTOs
     */
    @Override
    public Page<JoueurDTO> getAllJoueurs(Pageable pageable) {
        return joueurRepository.findAll(pageable)
                .map(joueurMapper::toJoueurDTO);
    }

    /**
     * Creates a new joueur (player) and assigns them to a team (equipe).
     *
     * @param joueurDTO the data of the player to be created
     * @return the created JoueurDTO
     * @throws RuntimeException if the specified team does not exist
     */
    @Override
    public JoueurDTO createJoueur(JoueurDTO joueurDTO) {
        Equipe equipe = equipeRepository.findById(joueurDTO.getEquipeId())
                .orElseThrow(() -> new RuntimeException("Equipe not found with id " + joueurDTO.getEquipeId()));

        Joueur joueur = joueurMapper.toJoueurEntity(joueurDTO);
        joueur.setEquipe(equipe);

        Joueur savedJoueur = joueurRepository.save(joueur);
        return joueurMapper.toJoueurDTO(savedJoueur);
    }
}
