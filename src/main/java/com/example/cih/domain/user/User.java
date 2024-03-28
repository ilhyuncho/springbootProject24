package com.example.cih.domain.user;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long userId;

    @Column(name="userName", length = 10, nullable = false)
    private String userName;

    @Column(name="address",length = 100, nullable = false)
    private String address;
}
