package com.mysite.sbb.cart;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    // 사용자의 장바구니 항목 가져오기
    public List<CartItem> getUserCart(SiteUser user) {
        return cartRepository.findByUser(user); // user에 해당하는 CartItem들을 반환하는 메서드
    }

    // 카트에 상품 추가
    public void addItemToCart(SiteUser user, Item item, int quantity) {
        // 카트에 이미 있는 상품인지 확인하고, 없다면 새로운 CartItem을 추가하는 로직
        CartItem cartItem = new CartItem(user, item, quantity);
        cartRepository.save(cartItem);
    }

    // 카트에서 상품 삭제
    public void removeItemFromCart(SiteUser user, Long itemId) {
        CartItem cartItem = cartRepository.findByUserAndItemId(user, itemId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }

    // 카트 상품 수량 변경
    public void updateCartItemQuantity(SiteUser user, Long itemId, int quantity) {
        CartItem cartItem = cartRepository.findByUserAndItemId(user, itemId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
        }
    }
}
