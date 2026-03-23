package com.takima.race.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.entities.Race;
import com.takima.race.repositories.RaceRepositories;

@Service
public class RaceServices {
    private final RaceRepositories racesRepositories;

    public RaceServices(RaceRepositories racesRepositories) {
        this.racesRepositories = racesRepositories;
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
        Race race = getById(raceId);
        return race.getRegistrations().size();
    }
}
