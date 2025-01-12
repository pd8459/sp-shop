package com.mysite.sbb.order;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantUid;
    private String impUid;
    private double amount;
    private String buyerName;
    private String buyerEmail;
    private LocalDateTime orderDate;
}
