package com.social_net.social_net.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social_net.social_net.entities.Comment;
import com.social_net.social_net.entities.Post;
import com.social_net.social_net.entities.User;
import com.social_net.social_net.repositories.CommentRepository;
import com.social_net.social_net.repositories.PostRepository;
import com.social_net.social_net.repositories.UserRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment addComment(Long postId, String username, String content) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post no encontrado"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post no encontrado"));
        return commentRepository.findByPost(post);
    }
}
