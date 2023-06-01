package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.model.request.ChangePasswordRequest;
import com.example.demo.model.request.UpdateProfileRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    @Operation(summary="Lấy ra user bằng username")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    @Operation(summary="Lấy danh sách user")
    public ResponseEntity<?> getListUser(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

//    @PutMapping("uploadImage/{username}/{imageId}")
//    public ResponseEntity<?> setImageForUser(@PathVariable("username") String username, @PathVariable("imageId") long imageId){
//        User user = userService.setImageForUser(username,imageId);
//        return ResponseEntity.ok(user);
//    }

    @PutMapping("/update")
    public ResponseEntity<User> updateProfile(@RequestBody UpdateProfileRequest request){
        User user = userService.updateUser(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{username}")
    @Operation(summary="Tìm user bằng id và cập nhật user đó")
    public ResponseEntity<?> updateUser(@PathVariable String username, @Valid @RequestBody UpdateProfileRequest request){
        User user = userService.updateUser(username, request);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/enable/{id}")
    @Operation(summary="Kích hoạt user bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
        userService.enableUser(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }

    @PutMapping("/changePassword/{username}")
    @Operation(summary = "Tìm user bằng id và thay đổi password")
    public ResponseEntity<?> changePassword(@PathVariable String username, @Valid @RequestBody ChangePasswordRequest request){
        userService.changePassword(username, request);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }

    @PutMapping("/resetPassword/{username}")
    @Operation(summary = "Tìm user bằng id và reset password")
    public ResponseEntity<?> resetPassword(@PathVariable String username){
        String code = userService.resetPassword(username);
        return ResponseEntity.ok(code);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa danh mục bằng id")
    public ResponseEntity<?> delete(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
}
