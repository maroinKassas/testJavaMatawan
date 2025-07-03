package com.nice.football.mapper;

import com.nice.football.DTO.EquipeDTO;
import com.nice.football.entity.Equipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between {@link Equipe} entity and {@link EquipeDTO}.
 * This interface is automatically implemented by MapStruct at compile time.
 * <p>
 * The mapper is registered as a Spring bean using {@code componentModel = "spring"}.
 * It also uses {@link JoueurMapper} to map nested joueur objects.
 */
@Mapper(componentModel = "spring", uses = {JoueurMapper.class})
public interface EquipeMapper {

    /**
     * Converts an {@link Equipe} entity to an {@link EquipeDTO}.
     * Maps the list of joueurs to joueursDTOList.
     *
     * @param entity the Equipe entity
     * @return the mapped EquipeDTO
     */
    @Mapping(target = "joueursDTOList", source = "joueurs")
    EquipeDTO toEquipeDTO(Equipe entity);

    /**
     * Converts an {@link EquipeDTO} to an {@link Equipe} entity.
     * Maps the joueursDTOList to the list of joueurs.
     *
     * @param dto the EquipeDTO
     * @return the mapped Equipe entity
     */
    @Mapping(target = "joueurs", source = "joueursDTOList")
    Equipe toEquipeEntity(EquipeDTO dto);
}
