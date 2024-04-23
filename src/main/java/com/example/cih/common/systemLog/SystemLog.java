package com.example.cih.common.systemLog;


import com.example.cih.domain.common.OnlyRegDateBaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="SystemLog")
public class SystemLog extends OnlyRegDateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="systemLogId")
    private Long systemLogId;

    @Transient  // 자바 직렬화와 영속화 에서 필드를 제외
   // @Column(name="text1", length = 50)
    private String text1;

    @Transient
    //@Column(name="text2",length = 50)
    private String text2;

    @Setter
    private String fullMsg;

    @Access(AccessType.PROPERTY)    // 기본 동작 방식을 재정의 (게터를 통한 접근)
    public String getFullMsg(){
        return text1  + ", " + text2;
    }
}
