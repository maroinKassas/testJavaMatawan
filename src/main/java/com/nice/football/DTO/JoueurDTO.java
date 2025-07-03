package com.nice.football.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object representing a football player (Joueur).
 */
public class JoueurDTO {

    /** Unique identifier of the player. */
    private Long id;
    /** Name of the player. Cannot be blank. */
    @NotBlank
    private String name;
    /** Position of the player on the field. Cannot be blank. */
    @NotBlank
    private String position;
    /** ID of the team (equipe) the player belongs to. */
    private Long equipeId;

    /** Gets the player ID. */
    public Long getId() { return id; }
    /** Sets the player ID. */
    public void setId(Long id) { this.id = id; }

    /** Gets the player's name. */
    public String getName() { return name; }
    /** Sets the player's name. */
    public void setName(String name) { this.name = name; }

    /** Gets the player's position. */
    public String getPosition() { return position; }
    /** Sets the player's position. */
    public void setPosition(String position) { this.position = position; }

    /** Gets the team ID the player belongs to. */
    public Long getEquipeId() { return equipeId; }
    /** Sets the team ID the player belongs to. */
    public void setEquipeId(Long equipeId) { this.equipeId = equipeId; }
}
