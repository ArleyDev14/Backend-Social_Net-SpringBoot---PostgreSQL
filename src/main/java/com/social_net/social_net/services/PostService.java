package com.social_net.social_net.services;

import org.springframework.stereotype.Service;

import com.social_net.social_net.entities.Post;
import com.social_net.social_net.entities.User;
import com.social_net.social_net.repositories.PostRepository;
import com.social_net.social_net.repositories.UserRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getFeed(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return postRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post updatedPost, String username) {
    Post existingPost = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

    if (!existingPost.getUser().getUsername().equals(username)) {
        throw new RuntimeException("No tienes permisos para editar esta publicación");
    }

    existingPost.setContent(updatedPost.getContent());
    existingPost.setImageUrl(updatedPost.getImageUrl());
    return postRepository.save(existingPost);
    }

}

