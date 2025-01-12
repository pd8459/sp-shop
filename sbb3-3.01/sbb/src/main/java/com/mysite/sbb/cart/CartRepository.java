package com.mysite.sbb.cart;

import com.mysite.sbb.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    // user에 해당하는 CartItem들을 반환하는 메서드
    List<CartItem> findByUser(SiteUser user);

    // user와 itemId에 해당하는 CartItem을 찾는 메서드
    CartItem findByUserAndItemId(SiteUser user, Long itemId);
}
