package com.nice.football.mapper;

import com.nice.football.DTO.JoueurDTO;
import com.nice.football.entity.Joueur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for converting between {@link Joueur} entities and {@link JoueurDTO} DTOs.
 * This interface is implemented by MapStruct at compile time and managed as a Spring bean
 * using {@code componentModel = "spring"}.
 */
@Mapper(componentModel = "spring")
public interface JoueurMapper {

    /**
     * Maps a {@link Joueur} entity to a {@link JoueurDTO}.
     * Maps the nested equipe's ID to equipeId in the DTO.
     *
     * @param joueur the Joueur entity
     * @return the mapped JoueurDTO
     */
    @Mapping(source = "equipe.id", target = "equipeId")
    JoueurDTO toJoueurDTO(Joueur joueur);

    /**
     * Maps a {@link JoueurDTO} to a {@link Joueur} entity.
     * Ignores the 'equipe' field, which must be set manually elsewhere.
     *
     * @param joueurDTO the JoueurDTO
     * @return the mapped Joueur entity
     */
    @Mapping(target = "equipe", ignore = true)
    Joueur toJoueurEntity(JoueurDTO joueurDTO);

    /**
     * Maps a list of {@link Joueur} entities to a list of {@link JoueurDTO}s.
     *
     * @param joueurs list of Joueur entities
     * @return list of JoueurDTOs
     */
    List<JoueurDTO> toJoueurDTOList(List<Joueur> joueurs);

    /**
     * Maps a list of {@link JoueurDTO}s to a list of {@link Joueur} entities.
     *
     * @param joueurDTOList list of JoueurDTOs
     * @return list of Joueur entities
     */
    List<Joueur> toJoueurEntitiesList(List<JoueurDTO> joueurDTOList);
}
