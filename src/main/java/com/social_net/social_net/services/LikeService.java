package com.social_net.social_net.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social_net.social_net.entities.Like;
import com.social_net.social_net.entities.Post;
import com.social_net.social_net.entities.User;
import com.social_net.social_net.repositories.LikeRepository;
import com.social_net.social_net.repositories.PostRepository;
import com.social_net.social_net.repositories.UserRepository;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public void toggleLike(Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post no encontrado"));

        Optional<Like> existingLike = likeRepository.findByPostAndUser(post, user);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get()); // Quitar like
        } else {
            Like newLike = new Like();
            newLike.setPost(post);
            newLike.setUser(user);
            likeRepository.save(newLike); // Agregar like
        }
    }

    public int getLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post no encontrado"));
        return likeRepository.countByPost(post);
    }
}