package com.mysite.sbb.cart;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // userId로 사용자의 카트 목록을 반환하는 메서드
    List<Cart> findByUserId(Long userId);

    // SiteUser와 Item으로 카트를 조회하는 메서드
    Cart findByUserAndItem(SiteUser user, Item item);
}
