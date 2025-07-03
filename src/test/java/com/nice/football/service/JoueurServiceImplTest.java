package com.nice.football.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nice.football.DTO.JoueurDTO;
import com.nice.football.entity.Equipe;
import com.nice.football.entity.Joueur;
import com.nice.football.mapper.JoueurMapper;
import com.nice.football.repository.EquipeRepository;
import com.nice.football.repository.JoueurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

class JoueurServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private JoueurRepository joueurRepository;

    @Mock
    private JoueurMapper joueurMapper;

    @InjectMocks
    private JoueurServiceImpl joueurService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllJoueurs_returnsPageOfJoueurDTO() {
        Joueur joueur1 = new Joueur();
        joueur1.setId(1L);
        Joueur joueur2 = new Joueur();
        joueur2.setId(2L);

        JoueurDTO joueurDTO1 = new JoueurDTO();
        joueurDTO1.setId(1L);
        JoueurDTO joueurDTO2 = new JoueurDTO();
        joueurDTO2.setId(2L);

        List<Joueur> joueurs = Arrays.asList(joueur1, joueur2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Joueur> page = new PageImpl<>(joueurs, pageable, joueurs.size());

        when(joueurRepository.findAll(pageable)).thenReturn(page);
        when(joueurMapper.toJoueurDTO(joueur1)).thenReturn(joueurDTO1);
        when(joueurMapper.toJoueurDTO(joueur2)).thenReturn(joueurDTO2);

        Page<JoueurDTO> result = joueurService.getAllJoueurs(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(joueurDTO1, result.getContent().get(0));
        assertEquals(joueurDTO2, result.getContent().get(1));

        verify(joueurRepository).findAll(pageable);
        verify(joueurMapper, times(2)).toJoueurDTO(any(Joueur.class));
    }

    @Test
    void testCreateJoueur_success() {
        Long equipeId = 10L;

        JoueurDTO joueurDTO = new JoueurDTO();
        joueurDTO.setEquipeId(equipeId);
        joueurDTO.setName("Nom Joueur");

        Equipe equipe = new Equipe();
        equipe.setId(equipeId);

        Joueur joueurEntity = new Joueur();
        joueurEntity.setName("Nom Joueur");

        Joueur savedJoueur = new Joueur();
        savedJoueur.setId(1L);
        savedJoueur.setName("Nom Joueur");
        savedJoueur.setEquipe(equipe);

        JoueurDTO savedDTO = new JoueurDTO();
        savedDTO.setId(1L);
        savedDTO.setName("Nom Joueur");

        when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));
        when(joueurMapper.toJoueurEntity(joueurDTO)).thenReturn(joueurEntity);
        when(joueurRepository.save(joueurEntity)).thenReturn(savedJoueur);
        when(joueurMapper.toJoueurDTO(savedJoueur)).thenReturn(savedDTO);

        JoueurDTO result = joueurService.createJoueur(joueurDTO);

        assertNotNull(result);
        assertEquals(savedDTO.getId(), result.getId());
        assertEquals(savedDTO.getName(), result.getName());

        verify(equipeRepository).findById(equipeId);
        verify(joueurMapper).toJoueurEntity(joueurDTO);
        verify(joueurRepository).save(joueurEntity);
        verify(joueurMapper).toJoueurDTO(savedJoueur);
        assertEquals(equipe, joueurEntity.getEquipe());
    }

    @Test
    void testCreateJoueur_throwsWhenEquipeNotFound() {
        Long equipeId = 999L;

        JoueurDTO joueurDTO = new JoueurDTO();
        joueurDTO.setEquipeId(equipeId);

        when(equipeRepository.findById(equipeId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            joueurService.createJoueur(joueurDTO);
        });

        assertEquals("Equipe not found with id " + equipeId, exception.getMessage());
        verify(equipeRepository).findById(equipeId);
        verifyNoMoreInteractions(joueurMapper, joueurRepository);
    }
}
