package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long price;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_expiry")
    private String expiry;

    private int quantity;
    private long total;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_deleted")
    private String dateDeleted;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
