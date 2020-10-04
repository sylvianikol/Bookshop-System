package com.softuni.springintroex.entities.usersystem;

import com.softuni.springintroex.entities.BaseEntity;
import com.softuni.springintroex.validators.ExtendedEmailValidator;
import com.softuni.springintroex.validators.ValidPassword;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String fullName;

    private String username;
    private String password;
    private String email;
    private LocalDateTime registeredOn;
    private LocalDateTime lastTimeLoggedIn;
    private int age;
    private boolean isDeleted;

    private Town homeTown;
    private Town residenceTown;

    private Set<User> usersWhoAreFriendsOfUser;
    private Set<User> usersWhoChoseUserAsFriend;

    private Set<Album> albums;

    public User() {
        this.usersWhoAreFriendsOfUser = new HashSet<>();
        this.usersWhoChoseUserAsFriend = new HashSet<>();
        this.albums = new HashSet<>();
    }

    public User(String username, String password,
                String email, LocalDateTime registeredOn, int age) {
        this();
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setRegisteredOn(registeredOn);
        this.setAge(age);
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Transient
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    @NotNull
    @Length(min = 4, max = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    @NotNull
    @ValidPassword
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    @NotNull
    @ExtendedEmailValidator
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "registered_on")
    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Column(name = "last_time_logged_in")
    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Column
    @Min(1)
    @Max(120)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "is_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToOne
    @JoinColumn(name = "home_town")
    public Town getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    @ManyToOne
    @JoinColumn(name = "residence_town")
    public Town getResidenceTown() {
        return residenceTown;
    }

    public void setResidenceTown(Town residenceTown) {
        this.residenceTown = residenceTown;
    }

    @ManyToMany
    public Set<User> getUsersWhoAreFriendsOfUser() {
        return usersWhoAreFriendsOfUser;
    }

    public void setUsersWhoAreFriendsOfUser(Set<User> friends) {
        this.usersWhoAreFriendsOfUser = friends;
    }

    @ManyToMany(mappedBy = "usersWhoAreFriendsOfUser", cascade = CascadeType.ALL)
    public Set<User> getUsersWhoChoseUserAsFriend() {
        return usersWhoChoseUserAsFriend;
    }

    public void setUsersWhoChoseUserAsFriend(Set<User> usersWhoChoseUserAsFriend) {
        this.usersWhoChoseUserAsFriend = usersWhoChoseUserAsFriend;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
