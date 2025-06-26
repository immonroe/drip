package com.drip.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class representing an item in the database.
 * JPA will map this class to a table automatically.
 */
@Entity // Marks this class as a database entity (table)
public class Item {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates unique IDs (usually serial)
    private Long id;

    // These fields will be mapped to columns in the database
    private String name;
    private String description;

    /**
     * No-args constructor — required by JPA/Hibernate
     */
    public Item() {
    }

    /**
     * Constructor used to create new Item objects with name and description
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    // ID setter is omitted because it's auto-generated

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
