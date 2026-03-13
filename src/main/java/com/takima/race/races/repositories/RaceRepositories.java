package com.takima.race.races.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.races.entities.Race;

@Repository
public interface  RaceRepositories extends JpaRepository<Race, Long> {
    Race findByName(String name);
}
