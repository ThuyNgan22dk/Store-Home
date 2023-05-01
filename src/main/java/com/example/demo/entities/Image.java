package com.example.demo.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "image")
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private long size;
    @Lob
    @Column(length = 5000000)
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}
