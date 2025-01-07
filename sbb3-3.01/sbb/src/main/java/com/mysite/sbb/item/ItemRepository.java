package com.mysite.sbb.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 카테고리에 따른 아이템을 페이징 처리하여 반환
    Page<Item> findByCategory(String category, Pageable pageable);
}