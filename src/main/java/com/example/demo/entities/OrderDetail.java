package com.example.demo.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long price;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_expiry")
    private String expiry;
    private int quantity;

    @ManyToOne
    @JoinColumn(name ="cart_id")
    private Cart cart;

//    private String promotionCode;
    private long subTotal;

    @ManyToOne
    @JoinColumn(name ="order_id")
    private Order order;
}

