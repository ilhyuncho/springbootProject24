package com.example.cih.domain.delivery;


import com.example.cih.domain.delivery.DeliveryStatus;
import com.example.cih.domain.user.Address;
import com.example.cih.domain.shop.Order;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Deliverys")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DELIVERY_ID")
    private Long deliveryId;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Address address){
        this.address = address;
    }
}
