package com.social_net.social_net.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social_net.social_net.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
