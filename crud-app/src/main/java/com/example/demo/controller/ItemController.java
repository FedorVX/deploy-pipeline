package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.ItemService;
import com.example.demo.service.InstanceIdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final InstanceIdService instanceIdService;

    public ItemController(ItemService itemService, InstanceIdService instanceIdService) {
        this.itemService = itemService;
        this.instanceIdService = instanceIdService;
    }

    @GetMapping
    public List<Item> getAllItems() {
        List<Item> items = itemService.getAllItems();
        items.forEach(this::enrichItem);
        return items;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(item -> ResponseEntity.ok(enrichItem(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        return enrichItem(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemService.updateItem(id, itemDetails)
                .map(item -> ResponseEntity.ok(enrichItem(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemService.deleteItem(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Item enrichItem(Item item) {
        item.setInstanceId(instanceIdService.getInstanceId());
        return item;
    }
}
