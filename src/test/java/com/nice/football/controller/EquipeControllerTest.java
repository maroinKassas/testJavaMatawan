package com.nice.football.controller;

import com.nice.football.DTO.EquipeDTO;
import com.nice.football.service.EquipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipeControllerTest {

    @Mock
    private EquipeService equipeService;

    @InjectMocks
    private EquipeController equipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEquipes_shouldReturnPageOfEquipeDTO() {
        EquipeDTO equipe1 = new EquipeDTO();
        equipe1.setName("Team A");
        EquipeDTO equipe2 = new EquipeDTO();
        equipe2.setName("Team B");

        Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));
        Page<EquipeDTO> equipePage = new PageImpl<>(List.of(equipe1, equipe2), pageable, 2);

        when(equipeService.getAllEquipes(pageable)).thenReturn(equipePage);

        Page<EquipeDTO> result = equipeController.getAllEquipes(0, 5, "name");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Team A", result.getContent().get(0).getName());
        assertEquals("Team B", result.getContent().get(1).getName());
        verify(equipeService, times(1)).getAllEquipes(pageable);
    }

    @Test
    void testCreateEquipe_shouldReturnCreatedEquipeDTO() {
        EquipeDTO inputEquipe = new EquipeDTO();
        inputEquipe.setName("New Team");

        EquipeDTO createdEquipe = new EquipeDTO();
        createdEquipe.setName("New Team");

        when(equipeService.createEquipe(inputEquipe)).thenReturn(createdEquipe);

        EquipeDTO result = equipeController.createEquipe(inputEquipe);

        assertNotNull(result);
        assertEquals("New Team", result.getName());
        verify(equipeService, times(1)).createEquipe(inputEquipe);
    }
}
