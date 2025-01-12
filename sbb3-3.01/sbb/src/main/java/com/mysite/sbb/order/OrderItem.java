package com.mysite.sbb.order;

import com.mysite.sbb.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false) // Order와 연관관계
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false) // Item과 연관관계
    private Item item;

    private int quantity; // 구매 수량

    private double price; // 해당 항목의 총 가격 (단위 가격 * 수량)
}
