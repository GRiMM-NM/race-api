package com.takima.race.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "race")
public class Race {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate date;
    private String location;
    private Integer maxParticipants;

    @OneToMany(mappedBy= "race")
    private List<Registration> registrations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Race races = (Race) o;
        return Objects.equals(id, races.id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public Integer getMaxParticipants() { return maxParticipants; }
    public List<Registration> getRegistrations() { return registrations; }
    
    // Setters 
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setMaxParticipants(Integer maxParticipant) { this.maxParticipants = maxParticipant; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; } 

}
