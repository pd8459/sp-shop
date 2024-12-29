package com.mysite.sbb.item;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 카테고리에 따른 아이템 조회
    List<Item> findByCategory(String category);
}
