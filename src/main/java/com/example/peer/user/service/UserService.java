package com.example.peer.user.service;

import com.example.peer.user.dto.UserDto;
import com.example.peer.user.dto.UpdateUserRequest;
import com.example.peer.user.repository.UserRepository;
import com.example.peer.user.entity.User;
import com.example.peer.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return new UserDto(user);
    }

    public UserDto updateUserInfo(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        // Update user info
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getProfileImageUrl() != null) {
            user.setProfileImageUrl(request.getProfileImageUrl());
        }
        // Update other fields as needed

        User updatedUser = userRepository.save(user);
        return new UserDto(updatedUser);
    }
}