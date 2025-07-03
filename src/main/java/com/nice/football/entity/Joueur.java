package com.nice.football.entity;

import jakarta.persistence.*;

/**
 * Entity representing a football player (Joueur).
 */
@Entity
@Table(name = "joueurs")
public class Joueur {

    /** Unique identifier of the player. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Name of the player. Cannot be null. */
    @Column(nullable = false)
    private String name;
    /** Playing position of the player (e.g., Forward, Midfielder). Cannot be null. */
    @Column(nullable = false)
    private String position;
    /**
     * The team (Equipe) this player belongs to.
     * Fetch type is LAZY to avoid loading the team unless explicitly accessed.
     * The association is mandatory (nullable = false).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    private Equipe equipe;

    /** Gets the player ID. */
    public Long getId() { return id; }
    /** Sets the player ID. */
    public void setId(Long id) { this.id = id; }

    /** Gets the player name. */
    public String getName() { return name; }
    /** Sets the player name. */
    public void setName(String name) { this.name = name; }

    /** Gets the player's position. */
    public String getPosition() { return position; }
    /** Sets the player's position. */
    public void setPosition(String position) { this.position = position; }

    /** Gets the team the player belongs to. */
    public Equipe getEquipe() { return equipe; }
    /** Sets the team the player belongs to. */
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }
}
