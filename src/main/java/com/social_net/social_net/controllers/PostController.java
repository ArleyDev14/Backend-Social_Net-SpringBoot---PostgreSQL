package com.social_net.social_net.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.social_net.social_net.entities.Post;
import com.social_net.social_net.entities.User;
import com.social_net.social_net.repositories.PostRepository;
import com.social_net.social_net.repositories.UserRepository;
import com.social_net.social_net.services.PostService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "http://localhost:3002")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/feed/{username}")
    public List<Post> getFeed(@PathVariable String username) {
        return postService.getFeed(username);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, Principal principal) {
    // Buscar el usuario autenticado en la base de datos
    User user = userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Asignar el usuario autenticado al post
    post.setUser(user);
    post.setCreatedAt(LocalDateTime.now());

    // Guardar el post en la base de datos
    Post savedPost = postRepository.save(post);

    return ResponseEntity.ok(savedPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost, Principal principal) {
    String username = principal.getName(); // Usuario autenticado
    Post post = postService.updatePost(postId, updatedPost, username);
    return ResponseEntity.ok(post);
}
    
}


