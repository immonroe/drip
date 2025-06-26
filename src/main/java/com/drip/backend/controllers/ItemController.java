package com.drip.backend.controllers;

import com.drip.backend.models.Item;
import com.drip.backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling HTTP requests related to Item resources.
 * Annotated with @RestController to indicate it's an API controller
 * that returns JSON instead of rendering views.
 */
@RestController

// Base path for all routes in this controller
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository repo;

    // Constructor-based dependency injection for the ItemRepository
    @Autowired
    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    /**
     * GET /api/items aka YOINK
     * Retrieves all items from the database.
     */
    @GetMapping
    public List<Item> getAllItems() {
        return repo.findAll();
    }

    /**
     * POST /api/items
     * Creates a new item using the request body and saves it to the database.
     */
    @PostMapping
    public Item create(@RequestBody Item item) {
        return repo.save(item);
    }

    /**
     * PUT /api/items/{id}
     * Updates an existing item by its ID.
     * If the item exists, it updates the name and description.
     * Otherwise, it returns a 404 Not Found response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Optional<Item> optionalItem = repo.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            Item saved = repo.save(item);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /api/items/{id}
     * Deletes the item with the given ID if it exists.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }
}
