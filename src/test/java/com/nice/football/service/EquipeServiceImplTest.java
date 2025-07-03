package com.nice.football.service;

import com.nice.football.DTO.EquipeDTO;
import com.nice.football.DTO.JoueurDTO;
import com.nice.football.entity.Equipe;
import com.nice.football.entity.Joueur;
import com.nice.football.mapper.EquipeMapper;
import com.nice.football.mapper.JoueurMapper;
import com.nice.football.repository.EquipeRepository;
import com.nice.football.repository.JoueurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private JoueurRepository joueurRepository;

    @Mock
    private EquipeMapper equipeMapper;

    @Mock
    private JoueurMapper joueurMapper;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEquipes_shouldReturnPageOfEquipeDTO() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));

        Equipe equipeEntity = new Equipe();
        equipeEntity.setId(1L);
        equipeEntity.setName("Nice");

        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setId(1L);
        equipeDTO.setName("Nice");

        Page<Equipe> equipePage = new PageImpl<>(List.of(equipeEntity), pageable, 1);

        when(equipeRepository.findAll(pageable)).thenReturn(equipePage);
        when(equipeMapper.toEquipeDTO(equipeEntity)).thenReturn(equipeDTO);

        Page<EquipeDTO> result = equipeService.getAllEquipes(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Nice", result.getContent().get(0).getName());

        verify(equipeRepository, times(1)).findAll(pageable);
        verify(equipeMapper, times(1)).toEquipeDTO(equipeEntity);
    }

    @Test
    void testCreateEquipe_withNewAndExistingJoueurs_shouldSaveEquipeWithJoueurs() {
        JoueurDTO joueurDTO1 = new JoueurDTO();
        joueurDTO1.setName("Joueur1");

        JoueurDTO joueurDTO2 = new JoueurDTO();
        joueurDTO2.setName("Joueur2");

        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setName("EquipeTest");
        equipeDTO.setJoueursDTOList(List.of(joueurDTO1, joueurDTO2));

        Equipe equipeEntity = new Equipe();
        equipeEntity.setName("EquipeTest");

        when(equipeMapper.toEquipeEntity(equipeDTO)).thenReturn(equipeEntity);

        Joueur existingJoueur = new Joueur();
        existingJoueur.setName("Joueur1");

        when(joueurRepository.findByName("Joueur1")).thenReturn(Optional.of(existingJoueur));
        when(joueurRepository.findByName("Joueur2")).thenReturn(Optional.empty());

        Joueur newJoueurEntity = new Joueur();
        newJoueurEntity.setName("Joueur2");

        when(joueurMapper.toJoueurEntity(joueurDTO2)).thenReturn(newJoueurEntity);

        Equipe savedEquipe = new Equipe();
        savedEquipe.setName("EquipeTest");
        savedEquipe.setJoueurs(List.of(existingJoueur, newJoueurEntity));

        when(equipeRepository.save(equipeEntity)).thenReturn(savedEquipe);

        EquipeDTO savedEquipeDTO = new EquipeDTO();
        savedEquipeDTO.setName("EquipeTest");

        when(equipeMapper.toEquipeDTO(savedEquipe)).thenReturn(savedEquipeDTO);

        EquipeDTO result = equipeService.createEquipe(equipeDTO);

        assertEquals("EquipeTest", result.getName());

        assertEquals(savedEquipe.getJoueurs().size(), 2);
        assertTrue(savedEquipe.getJoueurs().contains(existingJoueur));
        assertTrue(savedEquipe.getJoueurs().contains(newJoueurEntity));
        assertEquals(equipeEntity, existingJoueur.getEquipe());
        assertEquals(equipeEntity, newJoueurEntity.getEquipe());

        verify(equipeMapper).toEquipeEntity(equipeDTO);
        verify(joueurRepository, times(1)).findByName("Joueur1");
        verify(joueurRepository, times(1)).findByName("Joueur2");
        verify(joueurMapper).toJoueurEntity(joueurDTO2);
        verify(equipeRepository).save(equipeEntity);
        verify(equipeMapper).toEquipeDTO(savedEquipe);
    }

    @Test
    void testCreateEquipe_withNullJoueurs_shouldSaveEquipeSansJoueurs() {
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setName("EquipeSansJoueurs");
        equipeDTO.setJoueursDTOList(null);

        Equipe equipeEntity = new Equipe();
        equipeEntity.setName("EquipeSansJoueurs");

        when(equipeMapper.toEquipeEntity(equipeDTO)).thenReturn(equipeEntity);

        Equipe savedEquipe = new Equipe();
        savedEquipe.setName("EquipeSansJoueurs");

        when(equipeRepository.save(equipeEntity)).thenReturn(savedEquipe);

        EquipeDTO savedEquipeDTO = new EquipeDTO();
        savedEquipeDTO.setName("EquipeSansJoueurs");

        when(equipeMapper.toEquipeDTO(savedEquipe)).thenReturn(savedEquipeDTO);

        EquipeDTO result = equipeService.createEquipe(equipeDTO);

        assertEquals("EquipeSansJoueurs", result.getName());

        verify(equipeMapper).toEquipeEntity(equipeDTO);
        verify(equipeRepository).save(equipeEntity);
        verify(equipeMapper).toEquipeDTO(savedEquipe);
        verifyNoInteractions(joueurRepository);
        verifyNoInteractions(joueurMapper);
    }
}
