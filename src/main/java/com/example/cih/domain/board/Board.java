package com.example.cih.domain.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;
}
