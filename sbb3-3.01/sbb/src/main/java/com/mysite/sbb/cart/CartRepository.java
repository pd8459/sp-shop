package com.mysite.sbb.cart;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(SiteUser user);
    Cart findByUserAndItem(SiteUser user, Item item);
}
