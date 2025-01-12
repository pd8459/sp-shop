package com.mysite.sbb.cart;

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
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ItemService itemService;

    // 장바구니 페이지 보기
    @GetMapping
    public String getCartPage(Model model, Principal principal) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);

        // 유저의 장바구니 항목 가져오기
        List<CartItem> cartItems = cartService.getUserCart(user);

        // 총 금액 계산
        long totalAmount = (long) cartItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();

        // 모델에 장바구니 항목 및 총 금액 추가
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);

        return "cart"; // cart.html 템플릿
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

    // 결제하기 처리
    @PostMapping("/checkout")
    public String checkout(@RequestParam("totalAmount") long totalAmount, Principal principal, Model model) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);

        // 유저의 장바구니 항목 가져오기
        List<CartItem> cartItems = cartService.getUserCart(user);

        // 총 결제 금액을 포함한 모델에 데이터 추가
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);

        // 결제 화면으로 이동 (포트원 결제 API 연동 페이지로 이동)
        return "checkout"; // checkout.html 페이지로 이동
    }
}
