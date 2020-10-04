package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.usersystem.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
