package com.mysite.sbb.user;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String address;

    private String detailAddress;

    private String extraAddress;

    private String postcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
