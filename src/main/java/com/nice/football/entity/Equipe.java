package com.nice.football.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a football team (Equipe).
 */
@Entity
@Table(name = "equipes")
public class Equipe {

    /** Unique identifier of the team. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Name of the team. Cannot be null. */
    @Column(nullable = false)
    private String name;
    /** Acronym of the team (max 5 characters). Cannot be null. */
    @Column(nullable = false, length = 5)
    private String acronym;
    /**
     * List of players associated with the team.
     * Cascade type ALL: operations on the team affect its players.
     * Orphan removal: removes players if they are no longer linked to a team.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "equipe_id")
    private List<Joueur> joueurs = new ArrayList<>();
    /** Budget allocated to the team. Cannot be null. */
    @Column(nullable = false)
    private BigDecimal budget;
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

    /** Gets the list of players associated with the team. */
    public List<Joueur> getJoueurs() { return joueurs; }
    /** Sets the list of players associated with the team. */
    public void setJoueurs(List<Joueur> joueurs) { this.joueurs = joueurs; }

    /** Gets the team's budget. */
    public BigDecimal getBudget() { return budget; }
    /** Sets the team's budget. */
    public void setBudget(BigDecimal budget) { this.budget = budget; }
}
