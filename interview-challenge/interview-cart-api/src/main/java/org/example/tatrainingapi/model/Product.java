package org.example.tatrainingapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT", schema = "ta")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Description is mandatory")
    private String description;

    //@NotBlank(message = "Weight is mandatory")
    private double weight;

    //@NotBlank(message = "Value is mandatory")
    private double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Cart cart;

    public Product() {
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Double.compare(product.weight, weight) == 0 &&
                Double.compare(product.value, value) == 0 &&
                Objects.equals(description, product.description) &&
                (cart != null && product.cart != null) ? Objects.equals(cart.getId(), product.cart.getId())
                : Objects.equals(cart, product.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, weight, value, cart != null ? cart.getId() : null);
    }
}
