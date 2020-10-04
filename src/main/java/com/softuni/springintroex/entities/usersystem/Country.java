package com.softuni.springintroex.entities.usersystem;

import com.softuni.springintroex.entities.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    private String name;
    private Set<Town> towns;

    public Country() {
        this.towns = new HashSet<>();
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
