package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String country;
    private String state;
    private String bank;
    private String bankAccount;
    private String address;
    private String phone;
}
