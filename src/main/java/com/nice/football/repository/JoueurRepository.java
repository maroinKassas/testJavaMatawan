package com.nice.football.repository;

import com.nice.football.entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    Optional<Joueur> findByName(String name);
}

