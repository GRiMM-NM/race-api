package com.takima.race.runner.controllers;

import com.takima.race.race.entities.Race;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.services.RunnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RunnerController.class)
class RunnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RunnerService runnerService;

    @Test
    void getRunnerByIdReturnsRunnerWhenItExists() throws Exception {
        Runner runner = new Runner();
        runner.setId(1L);
        runner.setFirstName("Alice");
        runner.setLastName("Martin");
        runner.setEmail("alice@example.com");
        runner.setAge(30);

        when(runnerService.getById(1L)).thenReturn(runner);

        mockMvc.perform(get("/runners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Martin"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void updateRunnerReturnsCreatedWhenRunnerExists() throws Exception {
        Runner response = new Runner();
        response.setId(1L);
        response.setFirstName("Alice");
        response.setLastName("Martin");
        response.setEmail("alice@example.com");
        response.setAge(31);

        when(runnerService.update(org.mockito.ArgumentMatchers.eq(1L), org.mockito.ArgumentMatchers.any(Runner.class)))
                .thenReturn(response);

        mockMvc.perform(put("/runners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Alice",
                                  "lastName": "Martin",
                                  "email": "alice@example.com",
                                  "age": 31
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.age").value(31));
    }

    @Test
    void updateRunnerReturnsNotFoundWhenRunnerDoesNotExist() throws Exception {
        doThrow(new ResponseStatusException(NOT_FOUND, "Runner 99 not found"))
                .when(runnerService).update(org.mockito.ArgumentMatchers.eq(99L), org.mockito.ArgumentMatchers.any(Runner.class));

        mockMvc.perform(put("/runners/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Alice",
                                  "lastName": "Martin",
                                  "email": "alice@example.com",
                                  "age": 31
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRacesByRunnerIdReturnsRaces() throws Exception {
        Race race = new Race();
        race.setId(10L);
        race.setName("Semi-marathon de Paris");
        race.setDate(LocalDate.of(2026, 6, 1));
        race.setLocation("Paris");
        race.setMaxParticipants(500);

        when(runnerService.getRacesByRunnerId(1L)).thenReturn(List.of(race));

        mockMvc.perform(get("/runners/1/races"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].name").value("Semi-marathon de Paris"))
                .andExpect(jsonPath("$[0].location").value("Paris"));
    }

    @Test
    void getRacesByRunnerIdReturnsNotFoundWhenRunnerDoesNotExist() throws Exception {
        doThrow(new ResponseStatusException(NOT_FOUND, "Runner 99 not found"))
                .when(runnerService).getRacesByRunnerId(99L);

        mockMvc.perform(get("/runners/99/races"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRunnerReturnsNoContentWhenRunnerExists() throws Exception {
        doNothing().when(runnerService).deleteRunner(1L);

        mockMvc.perform(delete("/runners/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRunnerReturnsNotFoundWhenRunnerDoesNotExist() throws Exception {
        doThrow(new ResponseStatusException(NOT_FOUND, "Runner 99 not found"))
                .when(runnerService).deleteRunner(99L);

        mockMvc.perform(delete("/runners/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
