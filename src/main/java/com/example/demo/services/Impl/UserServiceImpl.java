package com.example.demo.services.Impl;

import com.example.demo.entities.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.ChangePasswordRequest;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdateProfileRequest;
import com.example.demo.repositories.ImageRepository;
import org.springframework.data.domain.Sort;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder encoder;
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public LocalDateTime now = LocalDateTime.now();

    @Override
    public void register(CreateUserRequest request) {
        // TODO Auto-generated method stub
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setDateCreated(dtf.format(now));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User setImageForUser(User user, Image image){
        Set<Image> images = new HashSet<>();
        images.add(image);
        user.setImages(images);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        // TODO Auto-generated method stub
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User"));
    }

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        List<User> userList = userRepository.findAll(Sort.by("id").descending());
        List<User> list = new ArrayList<>();
        for(User user: userList){
            if (!user.getUsername().equals("admin")) list.add(user);
        }
        return list;
    }

    private User getUser(User user, UpdateProfileRequest request){
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setCountry(request.getCountry());
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(UpdateProfileRequest request) {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));
        return getUser(user, request);
    }

    @Override
    public User updateUser(String username, UpdateProfileRequest request) {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + username));
        return getUser(user, request);
    }

    @Override
    public void enableUser(long id) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        user.setEnabled(false);
        user.setDateDeleted(dtf.format(now));
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) {
        // TODO Auto-generated method stub
         User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Id: " + username));
         if(!encoder.matches(request.getOldPassword(), user.getPassword())){
           throw new BadRequestException("Old Passrword Not Same");
         }
         user.setPassword(encoder.encode(request.getNewPassword()));
         userRepository.save(user);
    }

    @Override
    public String resetPassword(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Id: " + username));
        String code = String.valueOf(gen());
        user.setPassword(encoder.encode(code));
        userRepository.save(user);
    }
}
