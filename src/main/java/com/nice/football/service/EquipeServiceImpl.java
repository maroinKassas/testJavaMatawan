package com.nice.football.service;

import com.nice.football.DTO.EquipeDTO;
import com.nice.football.DTO.JoueurDTO;
import com.nice.football.entity.Equipe;
import com.nice.football.entity.Joueur;
import com.nice.football.mapper.EquipeMapper;
import com.nice.football.mapper.JoueurMapper;
import com.nice.football.repository.EquipeRepository;
import com.nice.football.repository.JoueurRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Equipes (football teams).
 */
@Service
public class EquipeServiceImpl implements EquipeService {

    private final EquipeRepository equipeRepository;
    private final JoueurRepository joueurRepository;
    private final EquipeMapper equipeMapper;
    private final JoueurMapper joueurMapper;

    public EquipeServiceImpl(EquipeRepository equipeRepository,
                             JoueurRepository joueurRepository,
                             EquipeMapper equipeMapper,
                             JoueurMapper joueurMapper) {
        this.equipeRepository = equipeRepository;
        this.joueurRepository = joueurRepository;
        this.equipeMapper = equipeMapper;
        this.joueurMapper = joueurMapper;
    }

    /**
     * Retrieves a paginated list of all equipes.
     *
     * @param pageable pagination and sorting information
     * @return a page of EquipeDTOs
     */
    @Override
    public Page<EquipeDTO> getAllEquipes(Pageable pageable) {
        return equipeRepository.findAll(pageable)
                .map(equipeMapper::toEquipeDTO);
    }

    /**
     * Creates a new equipe and associates any provided joueurs.
     * If a joueur with the same name already exists, they will be assigned to the new equipe.
     * Otherwise, new joueurs will be created and assigned.
     *
     * @param equipeDTO the equipe data to create
     * @return the created EquipeDTO
     */
    @Override
    @Transactional
    public EquipeDTO createEquipe(EquipeDTO equipeDTO) {
        Equipe equipe = equipeMapper.toEquipeEntity(equipeDTO);
        List<JoueurDTO> joueurDTOList = equipeDTO.getJoueursDTOList();

        if (joueurDTOList != null) {
            List<Joueur> joueurs = joueurDTOList.stream().map(joueurDTO ->
                    joueurRepository.findByName(joueurDTO.getName())
                            .map(existingJoueur -> {
                                existingJoueur.setEquipe(equipe);
                                return existingJoueur;
                            })
                            .orElseGet(() -> {
                                Joueur newJoueur = joueurMapper.toJoueurEntity(joueurDTO);
                                newJoueur.setEquipe(equipe);
                                return newJoueur;
                            })
            ).collect(Collectors.toList());

            equipe.setJoueurs(joueurs);
        }

        Equipe savedEquipe = equipeRepository.save(equipe);
        return equipeMapper.toEquipeDTO(savedEquipe);
    }
}