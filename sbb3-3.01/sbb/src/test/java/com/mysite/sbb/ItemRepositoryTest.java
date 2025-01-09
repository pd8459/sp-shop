package com.mysite.sbb;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void addTestItems() {
        for (int i = 20; i <= 100; i++) {
            Item item = new Item();
            item.setName("Test Item " + i);
            item.setDescription("Description for Test Item " + i);
            item.setPrice(1000 + (i * 100)); // 가격
            item.setCategory(i % 2 == 0 ? "Men" : "Women");
            item.setImageUrl("https://image.thenorthfacekorea.co.kr/cmsstatic/product/NT7TQ54A_NT7TQ54A_primary.jpg?gallery");

            itemRepository.save(item);
        }
    }
}
