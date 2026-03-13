package com.takima.race.races.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.races.entities.Race;
import com.takima.race.races.services.RaceServices;

@RestController
@RequestMapping("/races")
public class RaceController {
    private final RaceServices racesServices;

    public RaceController(RaceServices racesServices) {
        this.racesServices = racesServices;
    }

    @GetMapping
    public List<Race> getAll() {
        return racesServices.getAll();
    }

    @GetMapping("/{id}")
    public Race getById(@PathVariable Long id) {
        return racesServices.getById(id);
    }

    @PostMapping
    public Race create(@RequestBody Race race) {
        return racesServices.create(race);
    }

    @PutMapping("/{id}")
    public Race update(@PathVariable Long id, @RequestBody Race race) {
        return racesServices.update(id, race);
    }
}
