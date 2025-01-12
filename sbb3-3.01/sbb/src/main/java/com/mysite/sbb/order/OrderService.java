package com.mysite.sbb.order;

import com.mysite.sbb.item.ItemRepository;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository; // Order 데이터를 저장할 레포지토리

    @Autowired
    private OrderItemRepository orderItemRepository; // OrderItem 데이터를 저장할 레포지토리

    @Autowired
    private ItemRepository itemRepository;

    // 주문 저장 메서드
    public Order saveOrder(Map<String, Object> paymentData) {
        // 결제 데이터를 기반으로 Order 생성
        String merchantUid = (String) paymentData.get("merchant_uid");
        String impUid = (String) paymentData.get("imp_uid");
        double amount = Double.parseDouble((String) paymentData.get("amount"));
        String buyerName = (String) paymentData.get("buyer_name");
        String buyerEmail = (String) paymentData.get("buyer_email");

        // Order 생성
        Order order = new Order();
        order.setMerchantUid(merchantUid);
        order.setImpUid(impUid);
        order.setAmount(amount);
        order.setBuyerName(buyerName);
        order.setBuyerEmail(buyerEmail);
        order.setOrderDate(LocalDateTime.now());

        // Order 저장
        Order savedOrder = orderRepository.save(order);

        // Order에 관련된 OrderItem 생성 및 저장
        List<OrderItem> orderItems = createOrderItems(paymentData, savedOrder);
        orderItemRepository.saveAll(orderItems); // 여러 OrderItem을 저장

        return savedOrder; // 저장된 주문 반환
    }

    // OrderItem을 생성하는 메서드
    private List<OrderItem> createOrderItems(Map<String, Object> paymentData, Order order) {
        // 결제 데이터에서 상품 정보를 추출하여 OrderItem 목록을 만듦
        List<Map<String, Object>> items = (List<Map<String, Object>>) paymentData.get("items");
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map<String, Object> itemData : items) {
            Long itemId = Long.valueOf((String) itemData.get("item_id"));
            int quantity = Integer.parseInt((String) itemData.get("quantity"));

            // Item 찾기
            Item item = findItemById(itemId);

            // OrderItem 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(order); // 주문 정보 연결

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    // 상품 ID로 Item 찾는 메서드
    private Item findItemById(Long itemId) {
        // ItemRepository에서 상품을 찾아 반환하는 로직
        // 예시: itemRepository.findById(itemId).orElseThrow(...);
        return itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
