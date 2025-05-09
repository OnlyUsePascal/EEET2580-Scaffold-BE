package com.example.test2.monitor;

import jakarta.persistence.*;

@Entity
@Table(name = "monitor")
public class Monitor {

    /*
    1. Name
    @NotBlank(message = "First name is required")
    @Pattern(
            regexp = "^[A-Z][a-z]*$",
            message = "First name must be one capitalized word with alphabetic characters only (e.g., Tom)"
    )

    2. Email must end with .com or .vn
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Pattern(
            regexp = "^[\\w.-]+@[\\w.-]+\\.(com|vn)$",
            message = "Email must end with .com or .vn"
    )

    3. DOB in the past
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;
    private int price;

    public Monitor() {
    }

    public Monitor(int id, String name, String brand, int price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
