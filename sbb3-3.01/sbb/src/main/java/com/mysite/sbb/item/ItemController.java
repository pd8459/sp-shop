package com.mysite.sbb.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/mens-category")
    public String mensCategory(Model model) {
        Iterable<Item> items = itemService.getItemsByCategory("Men");
        model.addAttribute("items", items);
        return "men-category"; // men-category.html
    }

    @GetMapping("/womens-category")
    public String womensCategory(Model model) {
        Iterable<Item> items = itemService.getItemsByCategory("Women");
        model.addAttribute("items", items);
        return "womens-category"; // womens-category.html
    }
}
