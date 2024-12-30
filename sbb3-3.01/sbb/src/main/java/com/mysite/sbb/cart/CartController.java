package com.mysite.sbb.cart;

import com.mysite.sbb.cart.Cart;
import com.mysite.sbb.cart.CartService;
import com.mysite.sbb.item.Item;
import com.mysite.sbb.item.ItemService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ItemService itemService;

    public CartController(CartService cartService, UserService userService, ItemService itemService) {
        this.cartService = cartService;
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        // 현재 로그인된 사용자를 가져옵니다.
        String username = principal.getName();
        SiteUser user = userService.getUser(username);

        // 사용자와 연관된 장바구니 아이템 가져오기
        List<Cart> cartItems = cartService.getCartItems(user);
        if (cartItems == null) {
            cartItems = new ArrayList<>();  // null일 경우 빈 리스트로 초기화
        }
        model.addAttribute("cartItems", cartItems);
        return "cart"; // cart.html 렌더링
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long itemId, @RequestParam int quantity, Principal principal) {
        // 현재 로그인된 사용자를 가져옵니다.
        String username = principal.getName();
        SiteUser user = userService.getUser(username);

        // 장바구니에 추가
        cartService.addToCart(user, itemId, quantity);  // itemId만 전달
        return "redirect:/cart";
    }

    @PostMapping("/remove/{cartId}")
    public String removeFromCart(@PathVariable Long cartId) {
        // 장바구니에서 항목 제거
        cartService.removeFromCart(cartId);
        return "redirect:/cart";
    }
}
