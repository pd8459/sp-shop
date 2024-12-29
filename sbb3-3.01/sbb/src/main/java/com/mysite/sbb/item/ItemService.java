package com.mysite.sbb.item;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 모든 아이템 가져오기
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    // 카테고리에 따른 아이템 가져오기
    public List<Item> findByCategory(String category) {
        return itemRepository.findByCategory(category); // Repository 메서드 호출
    }

    // 아이템 저장
    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item findById(Long id) {
        Optional<Item> item = itemRepository.findById(id); // 아이템 ID로 찾기
        return item.orElse(null); // 없으면 null 반환
    }
}
