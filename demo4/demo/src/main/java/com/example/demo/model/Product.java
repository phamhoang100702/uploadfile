package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

// POJO
@Entity
@Data
@NoArgsConstructor
@Table(name="tblProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true,unique = true,length = 300)
    private String productName;
    private int manufacturedYear;
    private Double price;
    private String url;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Client client;

    public Product(String productName, int manufacturedYear, Double price, String url) {
        this.productName = productName;
        this.manufacturedYear = manufacturedYear;
        this.price = price;
        this.url = url;
    }
    public Product(Client client,String productName, int manufacturedYear, Double price, String url) {
        this.productName = productName;
        this.manufacturedYear = manufacturedYear;
        this.price = price;
        this.url = url;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + manufacturedYear +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

}
