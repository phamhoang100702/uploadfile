package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="tblClient")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

//    @OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="userId")
//    private List<Product> products = new ArrayList<>();




    public Client( String name) {
        this.name = name;
    }
}
