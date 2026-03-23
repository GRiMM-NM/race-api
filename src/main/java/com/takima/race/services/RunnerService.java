package com.takima.race.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.entities.Race;
import com.takima.race.entities.Registration;
import com.takima.race.entities.Runner;
import com.takima.race.repositories.RunnerRepository;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;

    public RunnerService(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
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
        Runner runner = getById(runnerId);
        return runner.getRegistrations().stream()
                .map(Registration::getRace)
                .filter(race -> race != null)
                .collect(Collectors.toList());
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
