package com.softuni.springintroex.entities.usersystem;

import com.softuni.springintroex.entities.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {

    private String name;
    private Country country;

    private Set<User> bornUsers;
    private Set<User> residents;

    public Town() {
        this.bornUsers = new HashSet<>();
        this.residents = new HashSet<>();
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @OneToMany(mappedBy = "homeTown", cascade = CascadeType.ALL)
    public Set<User> getBornUsers() {
        return bornUsers;
    }

    public void setBornUsers(Set<User> bornUsers) {
        this.bornUsers = bornUsers;
    }

    @OneToMany(mappedBy = "residenceTown", cascade = CascadeType.ALL)
    public Set<User> getResidents() {
        return residents;
    }

    public void setResidents(Set<User> residents) {
        this.residents = residents;
    }
}
