package com.mysite.sbb.cart;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.user.SiteUser; // 사용자의 실제 엔티티
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart") // 테이블 이름 명시
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 사용자와의 연관 관계
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 명시
    private SiteUser user;

    @ManyToOne(fetch = FetchType.LAZY) // 상품과의 연관 관계
    @JoinColumn(name = "item_id", nullable = false) // 외래 키 명시
    private Item item;

    private int quantity; // 상품 수량
}
