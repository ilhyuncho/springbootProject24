package com.example.cih.domain.seller;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sellerId")
    private Long sellerId;

    @Column(name="userId", nullable = false)
    private Long userId;

}
