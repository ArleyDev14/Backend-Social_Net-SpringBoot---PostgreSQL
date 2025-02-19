package com.social_net.social_net.services;

import com.social_net.social_net.entities.User;
import com.social_net.social_net.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User no encontrado" + username));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }
}