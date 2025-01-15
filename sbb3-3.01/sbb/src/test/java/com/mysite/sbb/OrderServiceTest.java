package com.mysite.sbb;

import com.mysite.sbb.item.Item;
import com.mysite.sbb.item.ItemRepository;
import com.mysite.sbb.order.Order;
import com.mysite.sbb.order.OrderItemRepository;
import com.mysite.sbb.order.OrderRepository;
import com.mysite.sbb.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private Item item;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }

    @Test
    public void testSaveOrder() {
        // Given: 결제 데이터 준비
        Map<String, Object> paymentData = Map.of(
                "merchant_uid", "order_12345",
                "imp_uid", "imp_12345",
                "amount", "1000",
                "buyer_name", "홍길동",
                "buyer_email", "test@example.com",
                "items", List.of(Map.of("item_id", "1", "quantity", "2"))
        );

        // Given: ItemRepository에서 상품을 반환하도록 설정
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Given: OrderRepository에서 저장된 주문을 반환하도록 설정
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        // When: 주문 저장 메서드 호출
        Order savedOrder = orderService.saveOrder(paymentData);

        // Then: 주문이 저장되고, 예외 없이 반환되어야 함
        assertNotNull(savedOrder);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }
}
