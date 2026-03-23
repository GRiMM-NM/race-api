package com.takima.race.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Runner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    @OneToMany(mappedBy = "runner")
    private List<Registration> registrations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return Objects.equals(id, runner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    

    // Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }
    public List<Registration> getRegistrations() {return registrations;}

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(Integer age) { this.age = age; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }


}