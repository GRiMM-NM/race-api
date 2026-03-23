package com.takima.race.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.entities.Runner;

@Repository
public interface RunnerRepository extends JpaRepository<Runner, Long> {
    Runner findByFirstName(String firstName);
}