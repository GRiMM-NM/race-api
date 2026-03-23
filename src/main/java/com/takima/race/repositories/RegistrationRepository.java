package com.takima.race.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.entities.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByRunner_IdAndRace_Id(Long runnerId, Long raceId);
    long countByRace_Id(Long raceId);
    List<Registration> findByRace_Id(Long raceId);
}
