package com.takima.race.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.entities.Runner;
import com.takima.race.services.RunnerService;

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
    public Runner create(@RequestBody Runner runner) {
        return runnerService.create(runner);
    }
}
