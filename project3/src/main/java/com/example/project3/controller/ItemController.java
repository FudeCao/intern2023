package com.example.project3.controller;

import com.example.project3.classes.Item;
import com.example.project3.classes.LoginRequest;
import com.example.project3.classes.User;
import com.example.project3.service.ItemService;
import com.example.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiitem")

public class ItemController {
    private final ItemService itemService;
    private UserService userService;

    @Autowired
    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService=userService;
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody LoginRequest loginRequest, @RequestBody Item item) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            if (user.getPosition().equals("manager")||
            user.getPosition().equals("shelf")){
                Item savedItem = itemService.saveItem(item);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Item> updateItem(@PathVariable Long id,@RequestBody LoginRequest loginRequest, @RequestParam Integer change) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            if (user.getPosition().equals("manager") ||
                    (user.getPosition().equals("shelf") &&change>0) ||
                    (user.getPosition().equals("unshelf")&&change<0)){
                Item item=itemService.getItemById(id);
                if (item!=null) {
                    item.setId(id);
                    item.changeNumber(change);
                    Item updatedItem = itemService.saveItem(item);
                    return ResponseEntity.ok(updatedItem);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            if (user.getPosition().equals("manager") ||
                    user.getPosition().equals("unshelf")) {
                Item item = itemService.getItemById(id);
                if (item != null) {
                    itemService.deleteItem(id);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }
}
