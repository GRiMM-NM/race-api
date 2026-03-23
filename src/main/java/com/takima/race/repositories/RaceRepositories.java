package com.takima.race.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.entities.Race;

@Repository
public interface  RaceRepositories extends JpaRepository<Race, Long> {
    Race findByName(String name);
}
