package com.mysite.sbb.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    // 추가적인 쿼리 메소드 작성 가능
    // 예를 들어, 카테고리별로 상품을 조회하는 메소드
    Iterable<Item> findByCategory(String category);
}
