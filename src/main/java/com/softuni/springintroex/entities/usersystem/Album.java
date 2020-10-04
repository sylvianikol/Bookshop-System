package com.softuni.springintroex.entities.usersystem;

import com.softuni.springintroex.entities.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {

    private String name;
    private String backgroundColor;
    private boolean isPublic;

    private Set<Picture> pictures;
    private User user;

    public Album() {
        this.pictures = new HashSet<>();
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Column
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @ManyToMany(mappedBy = "albums", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
