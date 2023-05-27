package com.example.demo.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username",unique = true)
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    @Column(name="email",unique = true)
    private String email;
    private String address;
    private String phone;
    //Dat nuoc
    private String country;
    private String bank;
    private String bankAccount;
    //Khu vuc
    private String state;
    private boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_created")
    private String dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_deleted")
    private String dateDeleted;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "history_purchase")
    private Set<Order> orders = new HashSet<>();


}
