package com.social_net.social_net.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social_net.social_net.entities.Post;
import com.social_net.social_net.entities.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserOrderByCreatedAtDesc(User user);
}
