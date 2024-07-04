package com.example.cih.domain.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass   // JPA의 엔티티 클래스가 상속받을 경우 자식 클래스에서 매핑 정보를 전달
public abstract class ItemOptionEntity extends BaseEntity{
    public Long itemOptionId1;
    public Long itemOptionId2;

    @Transient  // 영속 대상에서 제외시킴 ( get 이 붙어서 영속대상으로 포함되는듯 )
                // : 'Basic' attribute type should not be a container 에러 발생 때문에
    public List<Long> getListOptionId(){
        return Arrays.asList(itemOptionId1, itemOptionId2);
    }

}
