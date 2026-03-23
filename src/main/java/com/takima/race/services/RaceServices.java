package com.takima.race.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.entities.Race;
import com.takima.race.entities.Registration;
import com.takima.race.entities.Runner;
import com.takima.race.repositories.RaceRepositories;
import com.takima.race.repositories.RegistrationRepository;
import com.takima.race.repositories.RunnerRepository;

@Service
public class RaceServices {
    private final RaceRepositories racesRepositories;
    private final RunnerRepository runnerRepository;
    private final RegistrationRepository registrationRepository;

    public RaceServices(
            RaceRepositories racesRepositories,
            RunnerRepository runnerRepository,
            RegistrationRepository registrationRepository
    ) {
        this.racesRepositories = racesRepositories;
        this.runnerRepository = runnerRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<Race> getAll() {
        return racesRepositories.findAll();
    }

    public Race getById(Long id) {
        return racesRepositories.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Races %s not found", id)
                )
            );
    }

    public Race create(Race race) {
        return racesRepositories.save(race);
    }

    public Race update(Long id, Race raceUpdated ) {
            Race race = getById(id);

            race.setName(raceUpdated.getName());
            race.setDate(raceUpdated.getDate());
            race.setLocation(raceUpdated.getLocation());
            race.setMaxParticipants(raceUpdated.getMaxParticipants());

            return racesRepositories.save(race);
    }

    public int countParticipants(Long raceId) {
        getById(raceId);
        return Math.toIntExact(registrationRepository.countByRace_Id(raceId));
    }

    public void registerRunner(Long raceId, Long runnerId) {
        Race race = getById(raceId);
        Runner runner = runnerRepository.findById(runnerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Runner %s not found", runnerId))
        );

        if (registrationRepository.existsByRunner_IdAndRace_Id(runnerId, raceId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Runner is already registered for this race");
        }

        if (registrationRepository.countByRace_Id(raceId) >= race.getMaxParticipants()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Race is full");
        }

        Registration registration = new Registration();
        registration.setRunner(runner);
        registration.setRace(race);
        registration.setRegistrationDate(LocalDate.now());

        registrationRepository.save(registration);
    }

    public List<Runner> getParticipants(Long raceId) {
        getById(raceId);
        return registrationRepository.findByRace_Id(raceId).stream()
                .map(Registration::getRunner)
                .filter(runner -> runner != null)
                .collect(Collectors.toList());
    }
}
