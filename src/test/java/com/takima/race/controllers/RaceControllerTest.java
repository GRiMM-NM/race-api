package com.takima.race.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.entities.Runner;
import com.takima.race.services.RaceServices;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RaceController.class)
class RaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RaceServices raceServices;

    @Test
    void registerRunnerReturnsCreatedWhenRegistrationSucceeds() throws Exception {
        doNothing().when(raceServices).registerRunner(1L, 2L);

        mockMvc.perform(post("/races/1/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "runnerId": 2
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void registerRunnerReturnsConflictWhenAlreadyRegistered() throws Exception {
        doThrow(new ResponseStatusException(CONFLICT, "Runner is already registered for this race"))
                .when(raceServices).registerRunner(1L, 2L);

        mockMvc.perform(post("/races/1/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "runnerId": 2
                                }
                                """))
                .andExpect(status().isConflict());
    }

    @Test
    void getParticipantsReturnsRunnerList() throws Exception {
        Runner runner = new Runner();
        runner.setId(2L);
        runner.setFirstName("Alice");
        runner.setLastName("Martin");
        runner.setEmail("alice@example.com");
        runner.setAge(30);

        when(raceServices.getParticipants(1L)).thenReturn(List.of(runner));

        mockMvc.perform(get("/races/1/registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Alice"));
    }

    @Test
    void getParticipantsReturnsNotFoundWhenRaceDoesNotExist() throws Exception {
        doThrow(new ResponseStatusException(NOT_FOUND, "Races 99 not found"))
                .when(raceServices).getParticipants(99L);

        mockMvc.perform(get("/races/99/registrations"))
                .andExpect(status().isNotFound());
    }
}
