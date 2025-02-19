package com.social_net.social_net.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social_net.social_net.entities.User;
import com.social_net.social_net.repositories.UserRepository;
import com.social_net.social_net.security.AuthResponse;
import com.social_net.social_net.security.LoginRequest;
import com.social_net.social_net.security.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request){
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .fullname(request.getFullname())
            .email(request.getEmail())
            .bio(request.getBio())
            .profilePicture(request.getProfilePicture())
            .phone(request.getPhone())
            .birthday(request.getBirthday())
            .build();

        userRepository.save(user);

        return AuthResponse
        .builder()
        .token(jwtService.getToken(user))
        .build();
    }

}
