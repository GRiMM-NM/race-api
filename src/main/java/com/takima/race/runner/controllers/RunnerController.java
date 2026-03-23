package com.takima.race.runner.controllers;

import com.takima.race.race.entities.Race;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.services.RunnerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/runners")
public class RunnerController {
    private final RunnerService runnerService;

    public RunnerController(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @GetMapping
    public List<Runner> getAll() {
        return runnerService.getAll();
    }

    @GetMapping("/{id}")
    public Runner getById(@PathVariable Long id) {
        return runnerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Runner create(@RequestBody Runner runner) {
        return runnerService.create(runner);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Runner update(@PathVariable Long id, @RequestBody Runner runner) {
        return runnerService.update(id, runner);
    }

    @GetMapping("/{runnerId}/races")
    public List<Race> getRacesByRunnerId(@PathVariable Long runnerId) {
        return runnerService.getRacesByRunnerId(runnerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRunner(@PathVariable Long id) {
        runnerService.deleteRunner(id);
    }
}
