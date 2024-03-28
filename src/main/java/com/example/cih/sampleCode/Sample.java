package com.example.cih.sampleCode;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sampleId")
    private Long sampleId;

    @Column(name="SampleName", length = 10, nullable = false)
    private String sampleName;

    @Column(name="ex1",length = 30, nullable = false)
    private String ex1;
}
