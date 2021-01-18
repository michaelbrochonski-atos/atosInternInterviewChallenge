package org.example.cartapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART", schema = "ta")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double total = 0;

    @OneToMany(targetEntity = Product.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    public Cart() {
    }

    public long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
