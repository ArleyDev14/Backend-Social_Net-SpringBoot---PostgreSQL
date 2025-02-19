package com.social_net.social_net.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social_net.social_net.entities.Like;
import com.social_net.social_net.entities.Post;
import com.social_net.social_net.entities.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);
    int countByPost(Post post);
    void deleteByPostAndUser(Post post, User user);
}
