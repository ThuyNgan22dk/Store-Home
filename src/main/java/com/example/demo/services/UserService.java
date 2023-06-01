package com.example.demo.services;

import com.example.demo.entities.Image;
import com.example.demo.entities.User;
import com.example.demo.model.request.ChangePasswordRequest;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdateProfileRequest;

import java.util.List;

public interface UserService {
    void register(CreateUserRequest request);

    User setImageForUser(User user, Image image);
    User getUserByUsername(String username);

    List<User> getAllUsers();

    User setImageForUser(User user, Image image);

    User updateUser(UpdateProfileRequest request);

    User updateUser(String username, UpdateProfileRequest request);

    void enableUser(long id);

    void deleteUser(long id) ;

    void changePassword(String username, ChangePasswordRequest request);

    String resetPassword(String username);
}
