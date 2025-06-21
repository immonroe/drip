package com.drip.backend.controllers;

import com.drip.backend.models.Item;
import com.drip.backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// marks class as handling HTTP req + returns JSON
@RestController

// prefix for all endpoints
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository repo;

    @Autowired
    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    // GET - yoink all items
    @GetMapping
    public List<Item> getAllItems() {
        return repo.findAll();
    }

    // POST - create new obj/item
    @PostMapping
    public Item create(@RequestBody Item item) {
        return repo.save(item);
    }

    // PUT - update item (select by unique ID)
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

    // DELETE - remove item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
