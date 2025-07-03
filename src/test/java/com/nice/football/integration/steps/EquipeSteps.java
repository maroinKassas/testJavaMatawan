package com.nice.football.integration.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.football.DTO.EquipeDTO;
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
public class EquipeSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<EquipeDTO> createResponse;
    private ResponseEntity<String> listResponse;

    @Given("no equipes exist")
    public void clear_equipes() {
        restTemplate.delete("/equipes");
    }

    @When("I create an equipe with name {string}, acronym {string}, budget {long}")
    public void create_equipe(String name, String acronym, long budget) {
        EquipeDTO dto = new EquipeDTO();
        dto.setName(name);
        dto.setAcronym(acronym);
        dto.setBudget(BigDecimal.valueOf(budget));
        createResponse = restTemplate.postForEntity("/equipes", dto, EquipeDTO.class);
    }

    @Then("the equipe creation response status should be {int}")
    public void verify_creation_status(int status) {
        assertEquals(status, createResponse.getStatusCode().value());
    }

    @Then("the returned equipe should have id and name {string} and acronym {string} and budget {long}")
    public void verify_created_equipe(String name, String acronym, long budget) {
        EquipeDTO body = createResponse.getBody();
        assertNotNull(body.getId());
        assertEquals(name, body.getName());
        assertEquals(acronym, body.getAcronym());
        assertEquals(BigDecimal.valueOf(budget), body.getBudget());
    }

    @Given("there are at least {int} equipes")
    public void ensure_min_equipes(int count) {
        for (int i = 0; i < count; i++) {
            EquipeDTO dto = new EquipeDTO();
            dto.setName("Team" + i);
            dto.setAcronym("T" + i);
            dto.setBudget(BigDecimal.valueOf(1000 + i));
            restTemplate.postForEntity("/equipes", dto, EquipeDTO.class);
        }
    }

    @When("I list equipes page {int} size {int} sorted by {string}")
    public void list_equipes(int page, int size, String sort) {
        String url = String.format("/equipes?page=%d&size=%d&sortBy=%s", page, size, sort);
        listResponse = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response should contain {int} equipe")
    public void verify_list_count(int expected) throws Exception {
        assertEquals(200, listResponse.getStatusCode().value());
        JsonNode content = objectMapper.readTree(listResponse.getBody()).get("content");
        assertEquals(expected, content.size());
    }
}