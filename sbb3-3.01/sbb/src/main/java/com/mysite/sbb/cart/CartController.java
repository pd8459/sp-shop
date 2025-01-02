package com.mysite.sbb.cart;

import com.mysite.sbb.cart.CartService;
import com.mysite.sbb.item.Item;
import com.mysite.sbb.item.ItemService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ItemService itemService;

    

    @GetMapping
    public String getCartPage(Model model, Principal principal) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);
        model.addAttribute("cartItems", cartService.getUserCart(user));
        return "cart";
    }

    // 카트에 상품 추가
    @PostMapping("/add")
    public String addItemToCart(@RequestParam("itemId") Long itemId, @RequestParam("quantity") int quantity, Principal principal) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);
        Item item = itemService.findById(itemId);
        cartService.addItemToCart(user, item, quantity);  // 카트에 상품 추가
        return "redirect:/cart";  // 카트 페이지로 리다이렉트
    }

    // 카트에서 상품 삭제
    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam("itemId") Long itemId, Principal principal) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);
        cartService.removeItemFromCart(user, itemId);  // 카트에서 상품 삭제
        return "redirect:/cart";  // 카트 페이지로 리다이렉트
    }

    // 카트에서 상품 수량 변경
    @PostMapping("/update")
    public String updateCartItemQuantity(@RequestParam("itemId") Long itemId, @RequestParam("quantity") int quantity, Principal principal) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);
        cartService.updateCartItemQuantity(user, itemId, quantity);  // 카트 상품 수량 변경
        return "redirect:/cart";  // 카트 페이지로 리다이렉트
    }
}
