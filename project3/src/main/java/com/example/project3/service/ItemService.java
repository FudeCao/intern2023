package com.example.project3.service;

import com.example.project3.classes.Item;
import com.example.project3.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public Item saveItem(Item item){
        return itemRepository.save(item);
    }

    public Item getItemById(Long id){
        return itemRepository.getById(id);
    }

    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }
}
