package com.mysite.sbb.cart;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.item.ItemRepository;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public CartService(CartRepository cartRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Cart addToCart(SiteUser user, Long itemId, int quantity) {
        // 아이템 검색
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        Item item = itemOptional.get();

        // 동일한 상품이 이미 장바구니에 있는지 확인
        Cart existingCart = cartRepository.findByUserAndItem(user, item);
        if (existingCart != null) {
            // 수량 증가
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            return cartRepository.save(existingCart);
        }

        // 새로 장바구니에 추가
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItem(item);
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public List<Cart> getCartItems(SiteUser user) {
        // 특정 사용자의 장바구니 아이템 조회
        return cartRepository.findByUser(user);
    }

    public void removeFromCart(Long cartId) {
        // 장바구니 항목 삭제
        cartRepository.deleteById(cartId);
    }
}
