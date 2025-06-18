package com.drip.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Entity marks class as table in db
@Entity
public class Item {
    // Id marks primary key
    @Id
    // kind of straight forward via naming but it generates an ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    // Constructors - needed for JPA
    public Item() {
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters - standard Java beans
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
