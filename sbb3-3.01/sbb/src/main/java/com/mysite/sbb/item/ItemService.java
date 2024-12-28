package com.mysite.sbb.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 상품 목록을 카테고리별로 조회
    public Iterable<Item> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    // 상품 추가
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    // 상품 삭제
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
