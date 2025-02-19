package com.social_net.social_net.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String fullname;
    private String email;
    private String password;
    private String bio;
    private String profilePicture;
    private Long phone;
    private String birthday;

}
