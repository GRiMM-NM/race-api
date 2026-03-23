package com.takima.race.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registration")
public class Registration {
    
    @Id@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "runner_id", nullable= false)
    @JsonIgnore
    private Runner runner;

    @ManyToOne
    @JoinColumn(name = "race_id", nullable=false)
    @JsonIgnore
    private Race race;

    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    //Getters
    public Long getId() { return id; }
    public Runner getRunner() { return runner; }
    public Race getRace() { return race; }
    public Long getRunnerId() { return runner != null ? runner.getId() : null; }
    public Long getRaceId() { return race != null ? race.getId() : null; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    
    //Setters
    public void setId(Long id) { this.id = id; }
    public void setRunner(Runner runner) { this.runner = runner; }
    public void setRace(Race race) { this.race = race; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
}
