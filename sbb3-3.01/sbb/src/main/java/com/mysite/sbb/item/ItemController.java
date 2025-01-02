package com.mysite.sbb.item;

import com.mysite.sbb.cart.CartService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ItemController {

    private final ItemService itemService;
    private final CartService cartService;
    private final UserService userService;

    public ItemController(ItemService itemService, CartService cartService, UserService userService) {
        this.itemService = itemService;
        this.cartService = cartService;
        this.userService = userService;
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

    // 장바구니에 아이템 추가
    @PostMapping("/item/{id}/add-to-cart")
    public String addToCart(@PathVariable Long id, @RequestParam int quantity, Principal principal) {
        // 현재 로그인된 사용자를 가져옵니다.
        String username = principal.getName();
        SiteUser user = userService.getUser(username);

        // 아이템을 id로 조회
        Item item = itemService.findById(id); // id로 아이템 조회

        // 장바구니에 아이템 추가
        cartService.addItemToCart(user, item, quantity);  // 아이템 객체와 수량 전달

        // 장바구니 페이지로 리다이렉트
        return "redirect:/cart";  // 장바구니 페이지로 리다이렉트
    }

    @PostMapping("/update")
    public String updateCartItemQuantity(@RequestParam("itemId") Long itemId, @RequestParam("quantity") int quantity, Principal principal) {
        // Principal에서 현재 로그인된 사용자 정보 가져오기
        SiteUser user = userService.getUser(principal.getName());  // 로그인된 사용자

        // 카트의 상품 수량 변경
        cartService.updateCartItemQuantity(user, itemId, quantity);  // 카트 상품 수량 변경

        // 카트 페이지로 리다이렉트
        return "redirect:/cart";  // username은 필요 없으므로 제거
    }



}
