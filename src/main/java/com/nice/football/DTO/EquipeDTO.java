package com.nice.football.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object representing a football team (Equipe).
 */
public class EquipeDTO {
    /** Unique identifier of the team. */
    private Long id;
    /** Name of the team. Cannot be blank. */
    @NotBlank
    private String name;
    /** Acronym of the team. Cannot be blank. Maximum 3 characters. */
    @NotBlank
    @Size(max = 5)
    private String acronym;
    /** Budget of the team. Cannot be null. */
    @NotNull
    private BigDecimal budget;
    /** List of players (joueurs) associated with this team. */
    private List<JoueurDTO> joueursDTOList;

    /** Gets the team ID. */
    public Long getId() { return id; }
    /** Sets the team ID. */
    public void setId(Long id) { this.id = id; }

    /** Gets the team name. */
    public String getName() { return name; }
    /** Sets the team name. */
    public void setName(String name) { this.name = name; }

    /** Gets the team acronym. */
    public String getAcronym() { return acronym; }
    /** Sets the team acronym. */
    public void setAcronym(String acronym) { this.acronym = acronym; }

    /** Gets the team's budget. */
    public BigDecimal getBudget() { return budget; }
    /** Sets the team's budget. */
    public void setBudget(BigDecimal budget) { this.budget = budget; }

    /** Gets the list of players belonging to this team. */
    public List<JoueurDTO> getJoueursDTOList() { return joueursDTOList; }
    /** Sets the list of players for this team. */
    public void setJoueursDTOList(List<JoueurDTO> joueursDTOList) { this.joueursDTOList = joueursDTOList; }
}
