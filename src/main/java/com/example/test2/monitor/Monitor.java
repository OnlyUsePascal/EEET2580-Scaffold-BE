package com.example.test2.monitor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "monitor")
public class Monitor {
    @Id

    private int id;
    private String name;
    private String brand;
    private int price;
}
