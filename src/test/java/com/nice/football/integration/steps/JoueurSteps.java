package com.nice.football.integration.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.football.DTO.EquipeDTO;
import com.nice.football.DTO.JoueurDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JoueurSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Long storedEquipeId;
    private ResponseEntity<JoueurDTO> createResponse;
    private ResponseEntity<String> listResponse;

    @Given("an equipe exists with name {string} and acronym {string} and budget {long}")
    public void create_base_equipe(String name, String acronym, long budget) {
        EquipeDTO dto = new EquipeDTO();
        dto.setName(name);
        dto.setAcronym(acronym);
        dto.setBudget(BigDecimal.valueOf(budget));
        storedEquipeId = restTemplate.postForEntity("/equipes", dto, EquipeDTO.class).getBody().getId();
    }

    @When("I create a joueur with name {string}, position {string}, equipeId stored")
    public void create_joueur(String name, String position) {
        JoueurDTO dto = new JoueurDTO();
        dto.setName(name);
        dto.setPosition(position);
        dto.setEquipeId(storedEquipeId);
        createResponse = restTemplate.postForEntity("/joueurs", dto, JoueurDTO.class);
    }

    @Then("the joueur creation response status should be {int}")
    public void verify_creation_status(int status) {
        assertEquals(status, createResponse.getStatusCode().value());
    }

    @Then("the returned joueur should have id and name {string} and position {string} and equipeId stored")
    public void verify_created_joueur(String name, String position) {
        JoueurDTO body = createResponse.getBody();
        assertNotNull(body.getId());
        assertEquals(name, body.getName());
        assertEquals(position, body.getPosition());
        assertEquals(storedEquipeId, body.getEquipeId());
    }

    @Given("there are at least {int} joueurs")
    public void ensure_min_joueurs(int count) {
        for (int i = 0; i < count; i++) {
            JoueurDTO dto = new JoueurDTO();
            dto.setName("Player" + i);
            dto.setPosition("Midfielder");
            dto.setEquipeId(storedEquipeId);
            restTemplate.postForEntity("/joueurs", dto, JoueurDTO.class);
        }
    }

    @When("I list joueurs page {int} size {int} sorted by {string}")
    public void list_joueurs(int page, int size, String sort) {
        String url = String.format("/joueurs?page=%d&size=%d&sortBy=%s", page, size, sort);
        listResponse = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response should contain {int} joueur")
    public void verify_list_count(int expected) throws Exception {
        assertEquals(200, listResponse.getStatusCode().value());
        JsonNode content = objectMapper.readTree(listResponse.getBody()).get("content");
        assertEquals(expected, content.size());
    }
}