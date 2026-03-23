package com.takima.race.services;

import com.takima.race.race.entities.Race;
import com.takima.race.race.repositories.RaceRepository;
import com.takima.race.registration.entities.Registration;
import com.takima.race.registration.repositories.RegistrationRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.entities.Runner;
import com.takima.race.repositories.RunnerRepository;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;
    private final RegistrationRepository registrationRepository;
    private final RaceRepository raceRepository;

    public RunnerService(
            RunnerRepository runnerRepository,
            RegistrationRepository registrationRepository,
            RaceRepository raceRepository
    ) {
        this.runnerRepository = runnerRepository;
        this.registrationRepository = registrationRepository;
        this.raceRepository = raceRepository;
    }

    public List<Runner> getAll() {
        return runnerRepository.findAll();
    }

    public Runner getById(Long id) {
        return runnerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Runner %s not found", id)
                )
        );
    }

    public Runner create(Runner runner) {
        validateEmail(runner.getEmail());
        return runnerRepository.save(runner);
    }

    public Runner update(Long id, Runner runner) {
        Runner existingRunner = getById(id);
        validateEmail(runner.getEmail());

        existingRunner.setFirstName(runner.getFirstName());
        existingRunner.setLastName(runner.getLastName());
        existingRunner.setEmail(runner.getEmail());
        existingRunner.setAge(runner.getAge());

        return runnerRepository.save(existingRunner);
    }

    public List<Race> getRacesByRunnerId(Long runnerId) {
        getById(runnerId);

        List<Long> raceIds = registrationRepository.findByRunnerId(runnerId).stream()
                .map(Registration::getRaceId)
                .collect(Collectors.toList());

        if (raceIds.isEmpty()) {
            return List.of();
        }

        return raceRepository.findByIdIn(raceIds);
    }

    public void deleteRunner(Long id) {
        if (!runnerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Runner %s not found", id)
            );
        }

        runnerRepository.deleteById(id);
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
    }
}
