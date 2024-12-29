package com.mysite.sbb.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("items", itemService.findAll()); // 모든 아이템 가져오기
        return "main"; // main.html 반환
    }

    @GetMapping("/items/add")
    public String addItemForm() {
        return "add-item"; // add-item.html 반환
    }

    @PostMapping("/items/add")
    public String addItem(@ModelAttribute Item item) {
        itemService.save(item); // 아이템 저장
        return "redirect:/"; // 메인 페이지로 리다이렉트
    }
    @GetMapping("/mens-category")
    public String mensCategory(Model model) {
        model.addAttribute("items", itemService.findByCategory("Men")); // 남성 카테고리 아이템 가져오기
        return "men-category"; // men-category.html 반환
    }

    @GetMapping("/womens-category")
    public String womensCategory(Model model) {
        model.addAttribute("items", itemService.findByCategory("Women")); // 여성 카테고리 아이템 가져오기
        return "womens-category"; // women-category.html 반환
    }

    // 아이템 상세 페이지
    @GetMapping("/item/{id}")
    public String viewItem(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id); // 아이템 정보 가져오기
        model.addAttribute("item", item); // 아이템 상세 정보 모델에 추가
        return "item-detail"; // item-detail.html 반환
    }
}
