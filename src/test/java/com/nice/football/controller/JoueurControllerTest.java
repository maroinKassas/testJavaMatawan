package com.nice.football.controller;

import com.nice.football.DTO.JoueurDTO;
import com.nice.football.service.JoueurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JoueurControllerTest {

    @Mock
    private JoueurService joueurService;

    @InjectMocks
    private JoueurController joueurController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllJoueurs_shouldReturnPageOfJoueurDTO() {
        JoueurDTO joueur1 = new JoueurDTO();
        joueur1.setName("Joueur 1");
        JoueurDTO joueur2 = new JoueurDTO();
        joueur2.setName("Joueur 2");

        Pageable pageable = PageRequest.of(0, 5, Sort.by("equipe"));
        Page<JoueurDTO> joueurPage = new PageImpl<>(List.of(joueur1, joueur2), pageable, 2);

        when(joueurService.getAllJoueurs(pageable)).thenReturn(joueurPage);

        Page<JoueurDTO> result = joueurController.getAllJoueurs(0, 5, "equipe");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Joueur 1", result.getContent().get(0).getName());
        assertEquals("Joueur 2", result.getContent().get(1).getName());
        verify(joueurService, times(1)).getAllJoueurs(pageable);
    }

    @Test
    void testCreateJoueur_shouldReturnCreatedJoueurDTO() {
        JoueurDTO inputJoueur = new JoueurDTO();
        inputJoueur.setName("Nouveau Joueur");

        JoueurDTO createdJoueur = new JoueurDTO();
        createdJoueur.setName("Nouveau Joueur");

        when(joueurService.createJoueur(inputJoueur)).thenReturn(createdJoueur);

        JoueurDTO result = joueurController.createJoueur(inputJoueur);

        assertNotNull(result);
        assertEquals("Nouveau Joueur", result.getName());
        verify(joueurService, times(1)).createJoueur(inputJoueur);
    }
}
