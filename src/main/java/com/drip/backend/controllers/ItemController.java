package com.drip.backend.controllers;

import com.drip.backend.models.Item;
import com.drip.backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET req
    @GetMapping
    public List<Item> getAllItems() {
        return repo.findAll();
    }

    // POST req
    @PostMapping
    public Item create(@RequestBody Item item) {
        return repo.save(item);
    }
}
