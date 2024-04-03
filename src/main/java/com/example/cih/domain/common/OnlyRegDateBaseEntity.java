package com.example.cih.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass   // JPA의 엔티티 클래스가 상속받을 경우 자식 클래스에서 매핑 정보를 전달
@EntityListeners(value = { AuditingEntityListener.class})   // 해당 클래스에 Auditing 기능을 포함
// 엔티티를 db에 적용하기 전후로 콜백을 요청할 수 있게 하는 어노테이션
// 엔티티가 db에 추가되거나 변경될 때 자동으로 시간 값을 지정할 수 있다

// AuditingEntityListener 를 활성화 시키기 위해서는 프로젝트 설정에 @EnableJpaAuditing을 추가해야 함
// 이 프로젝트는 JpaAuditingConfiguration class를 추가함
@Getter
public abstract class OnlyRegDateBaseEntity {
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;
}
