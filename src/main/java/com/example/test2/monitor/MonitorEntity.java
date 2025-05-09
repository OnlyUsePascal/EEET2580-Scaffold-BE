package com.example.test2.monitor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "monitor")

public class MonitorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    
    private Long ID;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private double price;

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MonitorEntity() {
    }

    public MonitorEntity(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

}
