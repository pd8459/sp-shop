package com.mysite.sbb.cart;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // 사용자의 카트 목록 반환
    public List<Cart> getUserCart(SiteUser user) {
        // userId로 카트 항목을 찾는 쿼리 사용
        return cartRepository.findByUserId(user.getId());
    }

    // 카트에서 특정 상품 제거
    public void removeItemFromCart(SiteUser user, Long itemId) {
        // SiteUser와 Item을 기준으로 카트 항목 찾기
        Item item = new Item();  // Item 객체 생성 후 id 설정
        item.setId(itemId);

        Cart cartItem = cartRepository.findByUserAndItem(user, item);  // findByUserAndItem 호출
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }

    // 카트에 상품 추가 (기존 메서드)
    public void addItemToCart(SiteUser user, Item item, int quantity) {
        // 기존에 해당 상품이 이미 카트에 존재하는지 확인
        Cart existingCart = cartRepository.findByUserAndItem(user, item);
        if (existingCart != null) {
            // 이미 상품이 존재하면 수량만 증가
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartRepository.save(existingCart);
        } else {
            // 새로 카트 항목 추가
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setItem(item);
            newCart.setQuantity(quantity);
            cartRepository.save(newCart);
        }
    }

    public void updateCartItemQuantity(SiteUser user, Long itemId, int quantity) {
        // itemId를 이용해 Item 객체를 찾기
        Item item = new Item();
        item.setId(itemId);

        // 해당 사용자와 상품에 대한 카트 항목을 찾기
        Cart cartItem = cartRepository.findByUserAndItem(user, item);

        if (cartItem != null) {
            // 카트 항목이 존재하면 수량을 업데이트
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem); // 변경된 카트 항목 저장
        }
    }
}
